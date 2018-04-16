package com.gb.cornucopia;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.fruit.BlockFruitCrop;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.BlockVeggieWild;
import com.gb.cornucopia.veggie.Veggie;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;
import java.util.Random;

public class WildGrowth {
	private static final Random RANDOM = new Random();
	private static final int MAXCHUNKS = (512 * 512 * 4);
	private static final int CACHECHECK = 2048;
	private static final String FILENAME = "cc_wild_growth.dat";
	private int last_cleaned = 0;
	private Table<Integer, Integer, Integer> grew_last = HashBasedTable.create();

	private void read() {
		System.out.println("CornuCopia: reading in stored growth data from cc_wild_growth.dat");
		try {
			File f = new File(DimensionManager.getCurrentSaveRootDirectory(), FILENAME);
			FileInputStream fis = new FileInputStream(f);
			DataInputStream instream = new DataInputStream(fis);

			while (true) {
				try {
					// x, z, time
					grew_last.put(instream.readInt(), instream.readInt(), instream.readInt());
				} catch (Exception e) {
					System.out.format("CornuCopia: read %d chunks\n", grew_last.size());
					break;
				}
			}

			instream.close();
			fis.close();

		} catch (Exception e) {
			System.out.println("CornuCopia: wild growth file not found, ignoring.");
			// file not found, blank slate;
			// e.printStackTrace();
		}
	}

	private void write() {
		System.out.format("CornuCopia: write %d chunks\n", grew_last.size());
		try {
			File f = new File(DimensionManager.getCurrentSaveRootDirectory(), FILENAME);
			FileOutputStream fos = new FileOutputStream(f, false);
			DataOutputStream outstream = new DataOutputStream(fos);

			for (Cell<Integer, Integer, Integer> chunk : grew_last.cellSet()) {
				outstream.writeInt(chunk.getRowKey()); // x
				outstream.writeInt(chunk.getColumnKey()); // z
				outstream.writeInt(chunk.getValue()); // time
			}


			outstream.close();
			fos.close();
		} catch (Exception e) {
			System.err.println("CornuCopia: Error writing chunks:");
			e.printStackTrace();
		}


	}


	private void clear(int time) {
		// tbh prolly just better to clear it totally but whatever
		for (Integer x : grew_last.rowKeySet()) {
			for (Integer z : grew_last.columnKeySet()) {
				if (grew_last.contains(x, z) && time - grew_last.get(x, z) > Settings.wild_growth_cooldown) {
					grew_last.remove(x, z);
				}
			}
		}
		//System.out.format("		(cleared to )\n ", this.grew_last.size());
	}

	private boolean offCooldown(Chunk chunk, Integer time) {
		final int x = chunk.x;
		final int z = chunk.z;
		if (!grew_last.contains(x, z) || time - grew_last.get(x, z) > Settings.wild_growth_cooldown) {
			grew_last.put(x, z, time);
			return true;
		} else {
			return false;
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		this.read();
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Save event) {
		this.write();
	}

	@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load event) {
		if (event.getWorld().isRemote) {
			return;
		}
		if (!Settings.wild_fruit_spawn && !Settings.wild_veggie_spawn && !Settings.wild_bee_spawn) {
			return;
		}
		if (this.offCooldown(event.getChunk(), (int) event.getWorld().getWorldTime())) {
			// chunk has a chance to spawn a random veggie
			if (Settings.wild_fruit_spawn && RANDOM.nextInt(Settings.wild_fruit_spawn_chance) == 0) {
				this.growAFruit(event.getChunk(), event.getWorld());
			}
			if (Settings.wild_veggie_spawn && RANDOM.nextInt(Settings.wild_veggie_spawn_chance) == 0) {
				this.growAVeggie(event.getChunk(), event.getWorld());
			}
			if (Settings.wild_bee_spawn && RANDOM.nextInt(Settings.wild_bee_spawn_chance) == 0) {
				this.growABees(event.getChunk(), event.getWorld());
			}
		}

		// every thousand or so load events, make sure we're not leaking memory
		// this is roughly how many chunks i expect there to be total on the map
		if (last_cleaned++ > CACHECHECK) {
			last_cleaned = 0;
			//System.out.format("		(wild growth cache has %d)\n ", this.grew_last.size());
			if (grew_last.size() > MAXCHUNKS) {
				this.clear((int) event.getWorld().getWorldTime());
			}
		}
	}

	private void growAFruit(Chunk chunk, World world) {
		final int x = 16 * chunk.x + RANDOM.nextInt(16);
		final int z = 16 * chunk.z + RANDOM.nextInt(16);
		;

		final int yMin = chunk.getHeightValue(x & 15, z & 15) - 20;
		int y = chunk.getHeightValue(x & 15, z & 15);
		// don't bother searching too low
		while (--y > yMin) {
			final BlockPos pos = new BlockPos(x, y, z);
			final IBlockState leaf = world.getBlockState(pos.up());
			//System.out.format("		......%s ? %s ____ %s\n ", pos, world.getBlockState(pos), leaf);

			if ((leaf.getBlock() == Blocks.LEAVES || leaf.getBlock() == Blocks.LEAVES2) && world.isAirBlock(pos)) {
				final Biome b = world.getBiome(pos);
				final Fruit f = Fruit.getForBiome(RANDOM, b);
				if (f == null) {
					return;
				}
				world.setBlockState(pos, f.crop.getDefaultState()
						.withProperty(BlockFruitCrop.DROP_SAPLING, true)
						.withProperty(BlockFruitCrop.AGE, 3)
				);
				//System.out.format(" FRUIT @ %s \n\n", f == null ? "<>" : f.name);
				return;
			}
		}

	}

	private void growABees(Chunk chunk, World world) {
		final int x = 16 * chunk.x + RANDOM.nextInt(16);
		final int z = 16 * chunk.z + RANDOM.nextInt(16);
		;
		final int yMin = chunk.getHeightValue(x & 15, z & 15) - 12;
		int y = chunk.getHeightValue(x & 15, z & 15);

		// don't bother searching too low
		while (--y > yMin) {
			final BlockPos pos = new BlockPos(x, y, z);
			final IBlockState leaf = world.getBlockState(pos.up());
			//System.out.format("		......%s ? %s ____ %s\n ", pos, world.getBlockState(pos), leaf);

			if ((leaf.getBlock() == Blocks.LEAVES || leaf.getBlock() == Blocks.LEAVES2) && world.isAirBlock(pos)) {
				world.setBlockState(pos, Bees.hive.getDefaultState());
				//System.out.format(" BEES @ %s = %s \n ", pos, world.getBlockState(pos), leaf);
				return;
			}
		}
		//System.out.format("		bees hit %s\n ", world.getBlockState(new BlockPos(x, y, z)));

	}

	private void growAVeggie(Chunk chunk, World world) {
		// account for: temperate forest, cold forest, jungle, plains, arid
		final int x = 16 * chunk.x + RANDOM.nextInt(16);
		final int z = 16 * chunk.z + RANDOM.nextInt(16);
		;
		final int y = chunk.getHeightValue(x & 15, z & 15);
		final BlockPos pos = new BlockPos(x, y, z);
		final Biome b = world.getBiome(pos);
		final Veggie v = Veggie.getForBiome(RANDOM, b);
		if (v == null) {
			return;
		}
		if (world.getBlockState(pos.down()).getBlock() == Blocks.GRASS) {
			world.setBlockState(pos, v.wild.getDefaultState());
		}
		//System.out.format(" VEG   @ %s \n\n", v == null ? "<>" : v.name);
	}


}































