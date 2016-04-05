package com.gb.cornucopia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.fruit.BlockFruitCrop;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggie;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.Console;

public class WildGrowth {
	private static final Random RANDOM = new Random();
	private static final int MAXCHUNKS = (512 * 512 * 2);	
	private static final int CACHECHECK = 1024;
	private static final String FILENAME = "cc_wild_growth.dat";
	private int last_cleaned = 0;
	private Table<Integer, Integer, Integer> grew_last = HashBasedTable.create();

	private void read(){
		try {
			File f = new File(DimensionManager.getCurrentSaveRootDirectory(), FILENAME);
			FileInputStream fis = new FileInputStream(f);
			DataInputStream instream = new DataInputStream(fis);
			
			while (true){
				try {
					// x, z, time
					grew_last.put(instream.readInt(), instream.readInt(), instream.readInt());
				} catch (Exception e){
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
	private void write(){
			System.out.format("CornuCopia: write %d chunks\n", grew_last.size());
		try {
			File f = new File(DimensionManager.getCurrentSaveRootDirectory(), FILENAME);
			FileOutputStream fos = new FileOutputStream(f, false);
			DataOutputStream outstream = new DataOutputStream(fos);
			
			for (Cell<Integer, Integer, Integer> chunk: grew_last.cellSet()) {
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


	private void clear(int time){
		// tbh prolly just better to clear it totally but whatever
		for (Integer x : grew_last.rowKeySet()) {
			for (Integer z : grew_last.columnKeySet()) {
				if (grew_last.contains(x, z) && time - grew_last.get(x, z) > Settings.wild_growth_cooldown ){
					grew_last.remove(x, z);
				}
			}
		}
		//System.out.format("		(cleared to )\n ", this.grew_last.size());
	}

	private boolean offCooldown(Chunk c, Integer time){
		final int x = c.xPosition;
		final int z = c.zPosition;
		if ( !grew_last.contains(x, z) || time - grew_last.get(x, z) > Settings.wild_growth_cooldown ) {
			grew_last.put(x, z, time);
			return true;
		} else {
			return false;
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e){
		this.read();
	}
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Save e){
		this.write();
	}
	@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load e) {
		if (e.world.isRemote) { return; }
		if (!Settings.wild_fruit_spawn && !Settings.wild_veggie_spawn && !Settings.wild_bee_spawn) { return; }
		if ( this.offCooldown(e.getChunk(), (int) e.world.getWorldTime()) ) {
			// chunk has a chance to spawn a random veggie
			if (Settings.wild_fruit_spawn && RANDOM.nextInt(Settings.wild_fruit_spawn_chance) == 0) { this.growAFruit(e.getChunk(), e.world); }
			if (Settings.wild_veggie_spawn && RANDOM.nextInt(Settings.wild_veggie_spawn_chance) == 0) { this.growAVeggie(e.getChunk(), e.world); }
			if (Settings.wild_bee_spawn && RANDOM.nextInt(Settings.wild_bee_spawn_chance) == 0) { this.growABees(e.getChunk(), e.world); }
		}

		// every thousand or so load events, make sure we're not leaking memory
		// this is roughly how many chunks i expect there to be total on the map
		if ( last_cleaned++ > CACHECHECK)  {
			last_cleaned = 0;
			//System.out.format("		(wild growth cache has %d)\n ", this.grew_last.size());
			if ( grew_last.size() > MAXCHUNKS ) {
				this.clear((int) e.world.getWorldTime());
			}
		}
	}

	private void growAFruit(Chunk c, World w) {
		final int x = 16 * c.xPosition + RANDOM.nextInt(16);
		final int z = 16 * c.zPosition + RANDOM.nextInt(16);;
		final int yMin = c.getHeightValue(x & 15, z & 15);
		int y = c.getHeightValue(x & 15, z & 15);

		// don't bother searching too low
		while (--y > yMin) {
			final BlockPos pos = new BlockPos(x, y, z);
			final IBlockState leaf = w.getBlockState(pos.up());
			//System.out.format("		......%s ? %s ____ %s\n ", pos, w.getBlockState(pos), leaf);

			if ((leaf.getBlock() == Blocks.leaves || leaf.getBlock() == Blocks.leaves2) && w.isAirBlock(pos)) {
				final BiomeGenBase b = w.getBiomeGenForCoords(pos);
				final Fruit f = Fruit.getForBiome(RANDOM, b);
				if (f == null) {return;}
				w.setBlockState(pos, Fruit.getAny(RANDOM).crop.getDefaultState()
						.withProperty(BlockFruitCrop.DROP_SAPLING, true)
						.withProperty(BlockFruitCrop.AGE, 3)
						);
				//System.out.format(" FRUIT @ %s \n\n", f == null ? "<>" : f.name);
				return;
			}
		} 

	}

	private void growABees(Chunk c, World w) {
		final int x = 16 * c.xPosition + RANDOM.nextInt(16);
		final int z = 16 * c.zPosition + RANDOM.nextInt(16);;
		final int yMin = c.getHeightValue(x & 15, z & 15) - 12;
		int y = c.getHeightValue(x & 15, z & 15);

		// don't bother searching too low
		while (--y > yMin) {
			final BlockPos pos = new BlockPos(x, y, z);
			final IBlockState leaf = w.getBlockState(pos.up());
			//System.out.format("		......%s ? %s ____ %s\n ", pos, w.getBlockState(pos), leaf);

			if ((leaf.getBlock() == Blocks.leaves || leaf.getBlock() == Blocks.leaves2 ) && w.isAirBlock(pos)) {
				w.setBlockState(pos, Bees.hive.getDefaultState());
				//System.out.format(" BEES @ %s = %s \n ", pos, w.getBlockState(pos), leaf);
				return;
			}
		}
		//System.out.format("		bees hit %s\n ", w.getBlockState(new BlockPos(x, y, z)));

	}

	private void growAVeggie(Chunk c, World w) {
		// account for: temperate forest, cold forest, jungle, plains, arid
		final int x = 16 * c.xPosition + RANDOM.nextInt(16);
		final int z = 16 * c.zPosition + RANDOM.nextInt(16);;
		final int y = c.getHeightValue(x & 15, z & 15);
		final BlockPos pos = new BlockPos(x, y, z);
		final BiomeGenBase b = w.getBiomeGenForCoords(pos);
		final Veggie v = Veggie.getForBiome(RANDOM, b);
		if ( v == null ) {return;}
		if (w.getBlockState(pos.down()).getBlock() == Blocks.grass){
			w.setBlockState(pos, v.wild.getDefaultState());
		}
		//System.out.format(" VEG   @ %s \n\n", v == null ? "<>" : v.name);
	}




}































