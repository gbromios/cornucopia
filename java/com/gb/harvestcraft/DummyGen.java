package com.gb.harvestcraft;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class DummyGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		// this isn't gonna be pretty. I am desperate to get my shit into worldgen lol.
		// I'll implement it nicely when BoP rises from the ashes 
		
	}

}
