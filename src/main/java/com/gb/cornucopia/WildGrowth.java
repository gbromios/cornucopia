package com.gb.cornucopia;

import java.util.HashMap;
import java.util.Random;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.fruit.BlockFruitCrop;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggie;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WildGrowth {
	private static final Random RANDOM = new Random();
	private static final int MAXCHUNKS = (512 * 512 * 2);	
	private static final int CACHECHECK = 1024;
	private int last_cleaned = 0;
	private Table<Integer, Integer, Integer> grew_last = HashBasedTable.create();
	private void clear(int time){
		// tbh prolly just better to clear it totally but whatever
		for (Integer x : grew_last.rowKeySet()) {
			for (Integer z : grew_last.columnKeySet()) {
				if (grew_last.contains(x, z) && time - grew_last.get(x, z) > Settings.wild_growth_cooldown ){
					grew_last.remove(x, z);
				}
			}
		}
		System.out.format("		(cleared to )\n ", this.grew_last.size());
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
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load e) {
		if (e.world.isRemote && CornuCopia.config.wild_growth_mega_log) { 
			this.growAVeggie(e.getChunk(), e.world); // << debug only mode
			this.growAFruit(e.getChunk(), e.world); // << debug only mode
			//return; 
		}
		//if (true) {return;}
		if (!Settings.wild_fruit_spawn && !Settings.wild_veggie_spawn && !Settings.wild_bee_spawn) { return; }
		if ( this.offCooldown(e.getChunk(), (int) e.world.getWorldTime()) ) {
			// chunk has a chance to spawn a random veggie
			if (Settings.wild_fruit_spawn && RANDOM.nextInt(Settings.wild_fruit_spawn_chance) == 0) { this.growAFruit(e.getChunk(), e.world); }
			if (Settings.wild_veggie_spawn && RANDOM.nextInt(Settings.wild_veggie_spawn_chance) == 0) { this.growAVeggie(e.getChunk(), e.world); }
			//if (Settings.wild_bee_spawn && RANDOM.nextInt(Settings.wild_bee_spawn_chance) == 0) { this.growABees(e.getChunk(), e.world); }
		}
		
		// every thousand or so load events, make sure we're not leaking memory
		// this is roughly how many chunks i expect there to be total on the map
		if ( last_cleaned++ > CACHECHECK)  {
			last_cleaned = 0;
			System.out.format("		(wild growth cache has %d)\n ", this.grew_last.size());
			if ( grew_last.size() > MAXCHUNKS ) {
				this.clear((int) e.world.getWorldTime());
			}
		}
	}
	
	private void growAFruit(Chunk c, World w) {
		final int x = 16 * c.xPosition + RANDOM.nextInt(16);
		final int z = 16 * c.zPosition + RANDOM.nextInt(16);;
		final int yMin = c.getHeight(x & 15, z & 15) - 12;
		int y = c.getHeight(x & 15, z & 15);
		
		// don't bother searching too low
		while (--y > yMin) {
			final BlockPos pos = new BlockPos(x, y, z);
			final IBlockState leaf = w.getBlockState(pos.up());
			//System.out.format("		......%s ? %s ____ %s\n ", pos, w.getBlockState(pos), leaf);
			
			if ((leaf.getBlock() == Blocks.leaves || leaf.getBlock() == Blocks.leaves2) && w.isAirBlock(pos)) {
				final BiomeGenBase b = w.getBiomeGenForCoords(pos);
				final Fruit f = Fruit.getForBiome(RANDOM, b);
				/*
				if (f == null) {return;}
				w.setBlockState(pos, Fruit.getAny(RANDOM).crop.getDefaultState()
						.withProperty(BlockFruitCrop.DROP_SAPLING, true)
						.withProperty(BlockFruitCrop.AGE, RANDOM.nextInt(4))
						);
				*/
				System.out.format(" FRUIT @ %s \n\n", f == null ? "<>" : f.name);
				return;
			}
		} 

	}

	private void growABees(Chunk c, World w) {
		final int x = 16 * c.xPosition + RANDOM.nextInt(16);
		final int z = 16 * c.zPosition + RANDOM.nextInt(16);;
		final int yMin = c.getHeight(x & 15, z & 15) - 12;
		int y = c.getHeight(x & 15, z & 15);
		
		// don't bother searching too low
		while (--y > yMin) {
			final BlockPos pos = new BlockPos(x, y, z);
			final IBlockState leaf = w.getBlockState(pos.up());
			//System.out.format("		......%s ? %s ____ %s\n ", pos, w.getBlockState(pos), leaf);
			
			if ((leaf.getBlock() == Blocks.leaves || leaf.getBlock() == Blocks.leaves2 ) && w.isAirBlock(pos)) {
				w.setBlockState(pos, Bees.hive.getDefaultState());
				System.out.format(" BEES @ %s = %s \n ", pos, w.getBlockState(pos), leaf);
				return;
			}
		}
		//System.out.format("		bees hit %s\n ", w.getBlockState(new BlockPos(x, y, z)));
	
	}

	private void growAVeggie(Chunk c, World w) {
		// account for: temperate forest, cold forest, jungle, plains, arid
		final int x = 16 * c.xPosition + RANDOM.nextInt(16);
		final int z = 16 * c.zPosition + RANDOM.nextInt(16);;
		final int y = c.getHeight(x & 15, z & 15);
		final BlockPos pos = new BlockPos(x, y, z);
		final BiomeGenBase b = w.getBiomeGenForCoords(pos);
		final Veggie v = Veggie.getForBiome(RANDOM, b);
		/*
		if ( v == null ) {return;}
		if (w.getBlockState(pos.down()).getBlock() == Blocks.grass){
			w.setBlockState(pos, v.wild.getDefaultState());
		}*/
		System.out.format(" VEG   @ %s \n\n", v == null ? "<>" : v.name);
	}
	
	
	
	
}































