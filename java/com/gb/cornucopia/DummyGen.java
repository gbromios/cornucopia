package com.gb.cornucopia;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import com.gb.cornucopia.fruit.Fruits;
import com.gb.cornucopia.fruit.block.BlockLeafFruit;
import com.gb.cornucopia.veggie.block.BlockWildVeggie;
import com.gb.util.WeightedArray;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class DummyGen implements IWorldGenerator {
	// plains, swamp, desert, river and mushroom island are handled 
	// individually no need to put em in a set
	private final HashSet<BiomeGenBase> never_biomes;
	private final HashSet<BiomeGenBase> taiga_biomes;
	private final HashSet<BiomeGenBase> forest_biomes;
	private final HashSet<BiomeGenBase> mountain_biomes;
	private final HashSet<BiomeGenBase> arid_biomes;
	private final HashSet<BiomeGenBase> lush_biomes;

	private final WeightedArray<BlockWildVeggie> taiga_gardens;
	private final WeightedArray<BlockWildVeggie> forest_gardens;
	private final WeightedArray<BlockWildVeggie> mountain_gardens;
	private final WeightedArray<BlockWildVeggie> arid_gardens;
	private final WeightedArray<BlockWildVeggie> lush_gardens;
	private final WeightedArray<BlockWildVeggie> plains_gardens;
	private final WeightedArray<BlockWildVeggie> swamp_gardens;

	private final WeightedArray<BlockLeafFruit> forest_fruits;
	private final WeightedArray<BlockLeafFruit> mountain_fruits;
	private final WeightedArray<BlockLeafFruit> arid_fruits;
	private final WeightedArray<BlockLeafFruit> lush_fruits;
	private final WeightedArray<BlockLeafFruit> plains_fruits;
	private final WeightedArray<BlockLeafFruit> swamp_fruits;

	 
	public DummyGen(){
		//region // map biomes to categories
		// no fruits or veggies should spawn here
		never_biomes = new HashSet<BiomeGenBase>(Arrays.asList(new BiomeGenBase[]{
				BiomeGenBase.beach,
				BiomeGenBase.ocean,
				BiomeGenBase.deepOcean,
				BiomeGenBase.hell,
				BiomeGenBase.sky,
				BiomeGenBase.frozenOcean,
				BiomeGenBase.frozenRiver,
				BiomeGenBase.icePlains,
				BiomeGenBase.iceMountains,
				BiomeGenBase.desertHills,
				BiomeGenBase.mushroomIslandShore,
				BiomeGenBase.stoneBeach,
				BiomeGenBase.coldBeach
		}));

		taiga_biomes = new HashSet<BiomeGenBase>(Arrays.asList(new BiomeGenBase[]{
				BiomeGenBase.taiga,
				BiomeGenBase.taigaHills,
				BiomeGenBase.megaTaiga,
				BiomeGenBase.megaTaigaHills,
				BiomeGenBase.coldTaiga,
				BiomeGenBase.coldTaigaHills	
		}));

		forest_biomes = new HashSet<BiomeGenBase>(Arrays.asList(new BiomeGenBase[]{
				BiomeGenBase.birchForest,
				BiomeGenBase.birchForestHills,
				BiomeGenBase.roofedForest,
				BiomeGenBase.forest,
				BiomeGenBase.forestHills	
		}));
		mountain_biomes = new HashSet<BiomeGenBase>(Arrays.asList(new BiomeGenBase[]{
				BiomeGenBase.extremeHills,
				BiomeGenBase.extremeHillsEdge,
				BiomeGenBase.extremeHillsPlus
		}));
		arid_biomes = new HashSet<BiomeGenBase>(Arrays.asList(new BiomeGenBase[]{
				BiomeGenBase.savanna,
				BiomeGenBase.savannaPlateau,
				BiomeGenBase.mesa,
				BiomeGenBase.mesaPlateau_F,
				BiomeGenBase.mesaPlateau
		}));
		lush_biomes = new HashSet<BiomeGenBase>(Arrays.asList(new BiomeGenBase[]{
				BiomeGenBase.jungle,
				BiomeGenBase.jungleHills,
				BiomeGenBase.jungleEdge
		}));

		//endregion

		// region // map gardens to biomes
		taiga_gardens = new WeightedArray<BlockWildVeggie>()
				;
		forest_gardens = new WeightedArray<BlockWildVeggie>()
				;
		mountain_gardens = new WeightedArray<BlockWildVeggie>()
				;
		arid_gardens = new WeightedArray<BlockWildVeggie>()
				;
		lush_gardens = new WeightedArray<BlockWildVeggie>()
				;
		plains_gardens = new WeightedArray<BlockWildVeggie>()
				;
		swamp_gardens = new WeightedArray<BlockWildVeggie>()
				;


		//endregion

		//region // map fruits to biomes

		//.add(Fruits.nutmeg.leaf, 10)
		//.add(Fruits.cinnamon.leaf, 10)
		forest_fruits = new WeightedArray<BlockLeafFruit>()
				;

		mountain_fruits = new WeightedArray<BlockLeafFruit>()
				;

		arid_fruits = new WeightedArray<BlockLeafFruit>()
				;

		lush_fruits = new WeightedArray<BlockLeafFruit>()
				;

		plains_fruits = new WeightedArray<BlockLeafFruit>()
				;

		swamp_fruits = new WeightedArray<BlockLeafFruit>()
				;

		//endregion

	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		// this isn't gonna be pretty. I am desperate to get my shit into worldgen lol.
		// definitely brittle, but not brittle enough to be a pain in the ass for just vanilla.
		// like, if i was guaranteed never to want to change world gen, this implementation will probably be ffine
		// I'll implement it nicely when BoP rises from the ashes~!

		// going to break this feature for a while, no worries
		return;
		/*
		// 1/16 chance to gen a garden

		// its uncoditional right now to test generation logic. should try once per chunk
		if (random.nextInt(16) > 0) {
			this.genGarden(
					random,
					world,
					(chunkX * 16) + random.nextInt(15),
					(chunkZ * 16) + random.nextInt(15), 
					world.getBiomeGenForCoords(new BlockPos((chunkX * 16) + 7, 0, (chunkZ * 16) + 7))
					);
		}

		// same for fruit but since leaves are rarer, so shall be the fruits
		if (random.nextInt(16) > 0) {
			this.genFruit(
					random,
					world,
					(chunkX * 16) + random.nextInt(15),
					(chunkZ * 16) + random.nextInt(15), 
					world.getBiomeGenForCoords(new BlockPos((chunkX * 16) + 7, 0, (chunkZ * 16) + 7))
					);
		}*/
	}

	private BlockPos findLowestBranch(World world, int x, int z){
		BlockPos pos;
		int y = 128;
		while (y-- > 42){
			pos = new BlockPos(x, y, z);
			if (world.isAirBlock(pos.down()) && world.getBlockState(pos).getBlock().isLeaves(world, pos)) {

				return pos;
			}
		}
		return null;		
	}

	private BlockPos findLowestAir(World world, int x, int z){
		BlockPos pos;
		int y = 128;
		while (y-- > 42){
			pos = new BlockPos(x, y, z);
			if (!world.isAirBlock(pos.down())) {
				// mojang pls
				if (world.getBlockState(pos.down()).getBlock().isLeaves(world, pos.down())) {

					continue;
				}
				return pos;
			}
		}
		return null;
	}

	private void genGarden(Random random, World world, int x, int z, BiomeGenBase biome){
		// some biomes just don't get veggies >:C
		if (never_biomes.contains(biome)) { return; }

		BlockPos pos = findLowestAir(world, x, z);
		if (pos == null){ return; }


		Block soil = world.getBlockState(pos.down()).getBlock();

		// deserts have an even lower chance to grow 
		if (biome.equals(BiomeGenBase.desert)) {
			if (random.nextInt(3) == 0 && soil == Blocks.sand){

				//world.setBlockState(pos, desert.getDefaultState());
			}
			return; 
		}

		// river crops are even more rarer
		if (biome.equals(BiomeGenBase.river)) {
			if (random.nextInt(7) == 0){

				//world.setBlockState(pos, water.getDefaultState());
			}
			return;
		}

		if (mountain_biomes.contains(biome)) {
			if (random.nextInt(7) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){

				world.setBlockState(pos, mountain_gardens.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (taiga_biomes.contains(biome)) {
			if (random.nextInt(7) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){

				world.setBlockState(pos, taiga_gardens.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (arid_biomes.contains(biome)) {
			if (random.nextInt(5) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){

				world.setBlockState(pos, arid_gardens.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (forest_biomes.contains(biome)) {
			if (random.nextInt(2) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){

				world.setBlockState(pos, forest_gardens.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (lush_biomes.contains(biome)) {
			if (random.nextInt(1) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){

				world.setBlockState(pos, lush_gardens.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (BiomeGenBase.plains == biome){
			if (random.nextInt(2) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){

				world.setBlockState(pos, plains_gardens.getRandom(random).getDefaultState());
			}
			return;			
		}
		if (BiomeGenBase.swampland == biome){
			if (random.nextInt(3) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){

				world.setBlockState(pos, plains_gardens.getRandom(random).getDefaultState());
			}
			return;			
		}
	}

	private void genFruit(Random random, World world, int x, int z, BiomeGenBase biome) {
		// if it dont get veggies, it dont get fruit neiether do desests or rivers
		if (never_biomes.contains(biome) || taiga_biomes.contains(biome) || biome.equals(BiomeGenBase.desert) || biome.equals(BiomeGenBase.river)) {
			//
			return; 
		}

		BlockPos pos = findLowestBranch(world, x, z);
		if (pos == null){ return; }

		if (mountain_biomes.contains(biome)) {
			if (random.nextInt(3) == 0){

				world.setBlockState(pos, mountain_fruits.getRandom(random).getDefaultState().withProperty(BlockLeafFruit.SAPLING_GENERATOR, true));
			}
			return; 
		}

		if (arid_biomes.contains(biome)) {
			if (random.nextInt(2) == 0){
				world.setBlockState(pos, arid_fruits.getRandom(random).getDefaultState().withProperty(BlockLeafFruit.SAPLING_GENERATOR, true));
			}
			return; 
		}

		if (forest_biomes.contains(biome)) {
			if (random.nextInt(5) == 0){
				world.setBlockState(pos, forest_fruits.getRandom(random).getDefaultState().withProperty(BlockLeafFruit.SAPLING_GENERATOR, true));
			}
			return; 
		}

		if (lush_biomes.contains(biome)) {
			if (random.nextInt(5) == 0){
				world.setBlockState(pos, lush_fruits.getRandom(random).getDefaultState().withProperty(BlockLeafFruit.SAPLING_GENERATOR, true));
			}
			return; 
		}

		if (BiomeGenBase.plains == biome){
			if (random.nextInt(4) == 0){
				world.setBlockState(pos, plains_fruits.getRandom(random).getDefaultState().withProperty(BlockLeafFruit.SAPLING_GENERATOR, true));
			}
			return;			
		}
		
		if (BiomeGenBase.swampland == biome){
			if (random.nextInt(5) == 0){
				world.setBlockState(pos, plains_fruits.getRandom(random).getDefaultState().withProperty(BlockLeafFruit.SAPLING_GENERATOR, true));
			}
			return;			
		}
		
	}



}
