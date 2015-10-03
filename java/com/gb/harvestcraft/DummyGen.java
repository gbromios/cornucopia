package com.gb.harvestcraft;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import com.gb.harvestcraft.fruit.Fruits;
import com.gb.harvestcraft.fruit.block.BlockLeafFruit;
import com.gb.harvestcraft.garden.Gardens;
import com.gb.harvestcraft.garden.block.BlockGarden;
import com.gb.util.WeightedArray;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class DummyGen implements IWorldGenerator {
	// plains, swamp, desert, river and mushroom island are handled individually no need to put em in the set
	private final HashSet<BiomeGenBase> never_biomes;

	private final HashSet<BiomeGenBase> taiga_biomes;
	private final HashSet<BiomeGenBase> forest_biomes;
	private final HashSet<BiomeGenBase> mountain_biomes;
	private final HashSet<BiomeGenBase> arid_biomes;
	private final HashSet<BiomeGenBase> lush_biomes;
	// swamp and plains only have 1 biome each, so they don't need a mapping

	private final WeightedArray<BlockGarden> taiga_gardens;
	private final WeightedArray<BlockGarden> forest_gardens;
	private final WeightedArray<BlockGarden> mountain_gardens;
	private final WeightedArray<BlockGarden> arid_gardens;
	private final WeightedArray<BlockGarden> lush_gardens;
	private final WeightedArray<BlockGarden> plains_gardens;
	private final WeightedArray<BlockGarden> swamp_gardens;

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
		taiga_gardens = new WeightedArray<BlockGarden>()
				.add(Gardens.allium, 30)
				.add(Gardens.ground, 20)
				.add(Gardens.herb, 10)
				;

		forest_gardens = new WeightedArray<BlockGarden>()
				.add(Gardens.berry, 25)
				.add(Gardens.stalk, 10)
				.add(Gardens.allium, 10)
				.add(Gardens.ground, 10)

				;
		mountain_gardens = new WeightedArray<BlockGarden>()
				.add(Gardens.allium, 20)
				.add(Gardens.berry, 20)
				.add(Gardens.gourd, 5)
				.add(Gardens.grass, 5)
				;
		arid_gardens = new WeightedArray<BlockGarden>()
				.add(Gardens.grass, 40)
				.add(Gardens.ground, 40)
				.add(Gardens.stalk, 20)

				;
		lush_gardens = new WeightedArray<BlockGarden>()
				.add(Gardens.berry, 20)
				.add(Gardens.tropical, 40)
				.add(Gardens.leafy, 20)
				.add(Gardens.stalk, 20)
				.add(Gardens.herb, 20)

				;
		plains_gardens = new WeightedArray<BlockGarden>()
				.add(Gardens.grass, 40)
				.add(Gardens.leafy, 15)
				.add(Gardens.textile, 10)
				.add(Gardens.gourd, 10)
				;
		swamp_gardens = new WeightedArray<BlockGarden>()
				.add(Gardens.leafy, 30)
				.add(Gardens.herb, 30)
				.add(Gardens.stalk, 30)
				.add(Gardens.tropical, 10)
				;


		//.add(Fruits.nutmeg.leaf, 10)
		//.add(Fruits.cinnamon.leaf, 10)
		forest_fruits = new WeightedArray<BlockLeafFruit>()
				.add(Fruits.almond.leaf, 10)
				.add(Fruits.cherry.leaf, 10)
				.add(Fruits.peach.leaf, 10)
				.add(Fruits.pear.leaf, 10)
				.add(Fruits.persimmon.leaf, 10)
				.add(Fruits.walnut.leaf, 10)
				.add(Fruits.pecan.leaf, 10)
				;

		mountain_fruits = new WeightedArray<BlockLeafFruit>()
				.add(Fruits.candlenut.leaf, 10)
				.add(Fruits.cherry.leaf, 10)
				.add(Fruits.chestnut.leaf, 10)
				.add(Fruits.persimmon.leaf, 10)
				;

		arid_fruits = new WeightedArray<BlockLeafFruit>()
				.add(Fruits.apricot.leaf, 10)
				.add(Fruits.avocado.leaf, 10)
				.add(Fruits.date.leaf, 10)
				.add(Fruits.fig.leaf, 10)
				.add(Fruits.persimmon.leaf, 10)
				.add(Fruits.peppercorn.leaf, 10)
				;

		lush_fruits = new WeightedArray<BlockLeafFruit>()
				.add(Fruits.fig.leaf, 10)
				.add(Fruits.cashew.leaf, 10)
				.add(Fruits.coconut.leaf, 10)
				.add(Fruits.dragonfruit.leaf, 10)
				.add(Fruits.durian.leaf, 10)
				.add(Fruits.banana.leaf, 10)
				.add(Fruits.grapefruit.leaf, 10)
				.add(Fruits.lemon.leaf, 10)
				.add(Fruits.lime.leaf, 10)
				.add(Fruits.mango.leaf, 10)
				.add(Fruits.orange.leaf, 10)
				.add(Fruits.papaya.leaf, 10)
				.add(Fruits.peppercorn.leaf, 10)
				.add(Fruits.starfruit.leaf, 10)
				.add(Fruits.vanillabean.leaf, 10)
				;

		plains_fruits = new WeightedArray<BlockLeafFruit>()
				.add(Fruits.fig.leaf, 10)
				.add(Fruits.olive.leaf, 10)
				.add(Fruits.pear.leaf, 10)
				.add(Fruits.plum.leaf, 10)

				;

		swamp_fruits = new WeightedArray<BlockLeafFruit>()
				.add(Fruits.peach.leaf, 10)
				.add(Fruits.plum.leaf, 10)
				.add(Fruits.pomegranate.leaf, 10)
				.add(Fruits.pistachio.leaf, 10)
				.add(Fruits.walnut.leaf, 10)
				.add(Fruits.pecan.leaf, 10)
				;


		//endregion


		//region // map fruits to biomes



		//endregion

	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		// this isn't gonna be pretty. I am desperate to get my shit into worldgen lol.
		// definitely brittle, but not brittle enough to be a pain in the ass for just vanilla.
		// like, if i was guaranteed never to want to change world gen, this implementation will probably be ffine
		// I'll implement it nicely when BoP rises from the ashes~!

		// update: actually this works okay i might just map it to bop biomes huehue

		// 1/16 chance to gen a garden

		// its uncoditional right now to test generation logic. should try once per chunk
		//if (random.nextInt(15) > 0) {}
		this.genGarden(
				random,
				world,
				(chunkX * 16) + random.nextInt(15),
				(chunkZ * 16) + random.nextInt(15), 
				world.getBiomeGenForCoords(new BlockPos((chunkX * 16) + 7, 0, (chunkZ * 16) + 7))
				);


		// same for fruit but since leaves are rarer, so shall be the fruits
		// currently always for testing purposes
		//if (random.nextInt(15) > 0) {
		this.genFruit(
				random,
				world,
				(chunkX * 16) + random.nextInt(15),
				(chunkZ * 16) + random.nextInt(15), 
				world.getBiomeGenForCoords(new BlockPos((chunkX * 16) + 7, 0, (chunkZ * 16) + 7))
				);

	}

	private BlockPos findLowestBranch(World world, int x, int z){
		BlockPos pos;
		int y = 128;
		while (y-- > 42){
			pos = new BlockPos(x, y, z);
			if (world.isAirBlock(pos.down()) && world.getBlockState(pos).getBlock().isLeaves(world, pos)) {
				System.out.format(" <><> fruit can grow! @@@ %s\n", pos);
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
					System.out.format(" <><> FOLIAGE @@@ %s\n", pos.down());
					continue;
				}
				return pos;
			}
		}
		return null;
	}

	private void genGarden(Random random, World world, int x, int z, BiomeGenBase biome){
		// some biomes just don't get veggies >:C
		if (never_biomes.contains(biome)) {
			//System.out.format("   NEVER IN %s!\n", biome.biomeName);
			return; 
		}

		BlockPos pos = findLowestAir(world, x, z);
		if (pos == null){
			System.out.format(" ? no air at %d, %d???\n", x, z);
			return;
		}


		if (pos.getY() < 40){
			System.out.format("   cannot :( - '%d' < 40~ 2 deep 4 me\n", pos.getY());
			return; 
		}

		Block soil = world.getBlockState(pos.down()).getBlock();
		//System.out.format(" ~ trying genGarden in %s @ %s:\n", biome.biomeName, pos);

		// deserts have an even lower chance to grow 
		if (biome.equals(BiomeGenBase.desert)) {
			if (random.nextInt(3) == 0 && soil == Blocks.sand){
				System.out.format(" ! GO DESERT  @ %s %s!\n", biome.biomeName, pos);
				world.setBlockState(pos, Gardens.desert.getDefaultState());
			}
			return; 
		}

		// river crops are even more rarer
		if (biome.equals(BiomeGenBase.river)) {
			if (random.nextInt(7) == 0){
				System.out.format(" ! GO RIVER  @ %s %s!\n", biome.biomeName, pos);
				world.setBlockState(pos, Gardens.water.getDefaultState());
			}
			return;
		}

		if (mountain_biomes.contains(biome)) {
			if (random.nextInt(7) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO MOUNTAINE @ %s %s !\n", biome.biomeName, pos);
				world.setBlockState(pos, mountain_gardens.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (taiga_biomes.contains(biome)) {
			if (random.nextInt(7) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO TIGERS! @ %s %s USA #1 !\n", biome.biomeName, pos);
				world.setBlockState(pos, taiga_gardens.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (arid_biomes.contains(biome)) {
			if (random.nextInt(5) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO ARID  @ %s %s!\n", biome.biomeName, pos);
				world.setBlockState(pos, arid_gardens.getRandom(random).getDefaultState());
			}
			return; 
		}

		// at this point, the unchecked biomes are lush enough for a small chance to
		// grow river gardens? :O

		if (forest_biomes.contains(biome)) {
			if (random.nextInt(2) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO FORAST @ %s %s !\n", biome.biomeName, pos);
				world.setBlockState(pos, forest_gardens.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (lush_biomes.contains(biome)) {
			if (random.nextInt(1) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO JUNGO @ %s %s !\n", biome.biomeName, pos);
				world.setBlockState(pos, lush_gardens.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (BiomeGenBase.plains == biome){
			if (random.nextInt(2) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO PLAINS @ %s %s !\n", biome.biomeName, pos);
				world.setBlockState(pos, plains_gardens.getRandom(random).getDefaultState());
			}
			return;			
		}
		if (BiomeGenBase.swampland == biome){
			if (random.nextInt(3) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO SKWUMP @ %s %s !\n", biome.biomeName, pos);
				world.setBlockState(pos, plains_gardens.getRandom(random).getDefaultState());
			}
			return;			
		}
	}

	private void genFruit(Random random, World world, int x, int z, BiomeGenBase biome) {
		// if it dont get veggies, it dont get fruit neiether do desests or rivers
		if (never_biomes.contains(biome) || taiga_biomes.contains(biome) || biome.equals(BiomeGenBase.desert) || biome.equals(BiomeGenBase.river)) {
			//System.out.format("   NEVER IN %s!\n", biome.biomeName);
			return; 
		}

		BlockPos pos = findLowestBranch(world, x, z);
		if (pos == null){
			return;
		}


		if (pos.getY() < 40){
			System.out.format("   cannot :( - '%d' < 40~ 2 deep 4 me\n", pos.getY());
			return; 
		}

		//System.out.format(" ~ trying genFruit in %s @ %s:\n", biome.biomeName, pos);
		if (mountain_biomes.contains(biome)) {
			if (random.nextInt(3) == 0){
				System.out.format(" ! FRU MOUNTAINE @ %s %s !\n", biome.biomeName, pos);
				world.setBlockState(pos, mountain_fruits.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (arid_biomes.contains(biome)) {
			if (random.nextInt(2) == 0){
				System.out.format(" ! FRU ARID  @ %s %s!\n", biome.biomeName, pos);
				world.setBlockState(pos, arid_fruits.getRandom(random).getDefaultState());
			}
			return; 
		}

		// at this point, the unchecked biomes are lush enough for a small chance to
		// grow river fruits? :O

		if (forest_biomes.contains(biome)) {
			if (random.nextInt(5) == 0){
				System.out.format(" ! FRU FORAST @ %s %s !\n", biome.biomeName, pos);
				world.setBlockState(pos, forest_fruits.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (lush_biomes.contains(biome)) {
			if (random.nextInt(5) == 0){
				System.out.format(" ! FRU JUNFRU @ %s %s !\n", biome.biomeName, pos);
				world.setBlockState(pos, lush_fruits.getRandom(random).getDefaultState());
			}
			return; 
		}

		if (BiomeGenBase.plains == biome){
			if (random.nextInt(4) == 0){
				System.out.format(" ! FRU PLAINS @ %s %s !\n", biome.biomeName, pos);
				world.setBlockState(pos, plains_fruits.getRandom(random).getDefaultState());
			}
			return;			
		}
		if (BiomeGenBase.swampland == biome){
			if (random.nextInt(5) == 0){
				System.out.format(" ! FRU SKWUMP @ %s %s !\n", biome.biomeName, pos);
				world.setBlockState(pos, plains_fruits.getRandom(random).getDefaultState());
			}
			return;			
		}

	}



}
