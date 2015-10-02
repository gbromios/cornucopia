package com.gb.harvestcraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import com.gb.harvestcraft.garden.Gardens;
import com.gb.harvestcraft.garden.block.BlockGarden;
import com.gb.harvestcraft.veggie.Veggies;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraft.util.WeightedRandom;

public class DummyGen implements IWorldGenerator {
	// plains, swamp, desert, river and mushroom island are handled individually no need to put em in the set
	private final HashSet<BiomeGenBase> never_biomes;
	
	private final HashSet<BiomeGenBase> taiga_biomes;
	private final ArrayList<DummyGen.GardenWTFWR> taiga_gardens;
	private final HashSet<BiomeGenBase> forest_biomes;
	private final ArrayList<DummyGen.GardenWTFWR> forest_gardens;
	private final HashSet<BiomeGenBase> mountain_biomes;
	private final ArrayList<DummyGen.GardenWTFWR> mountain_gardens;
	private final HashSet<BiomeGenBase> arid_biomes;
	private final ArrayList<DummyGen.GardenWTFWR> arid_gardens;
	private final HashSet<BiomeGenBase> lush_biomes;
	private final ArrayList<DummyGen.GardenWTFWR> lush_gardens;
	
	private final ArrayList<DummyGen.GardenWTFWR> plains_gardens;
	private final ArrayList<DummyGen.GardenWTFWR> swamp_gardens;
	
	static final class GardenWTFWR extends WeightedRandom.Item {
		public final BlockGarden garden;
		public GardenWTFWR(BlockGarden garden, int weight) {
			super(weight);
			this.garden = garden;
		}
	}
	
	static final class TUMMYGRABBER extends WeightedRandom {
		public BlockGarden getRandomGarden(Random r, Collection c) {
			return ((GardenWTFWR)super.getRandomItem(r, c)).garden;
		}
	}


	public DummyGen(){
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
		
		//
        taiga_gardens = new ArrayList<DummyGen.GardenWTFWR>(Arrays.asList(new DummyGen.GardenWTFWR[]{
        		new DummyGen.GardenWTFWR(Gardens.allium, 30),
        		new DummyGen.GardenWTFWR(Gardens.ground, 10),
        		new DummyGen.GardenWTFWR(Gardens.gourd, 10)
        }));
        forest_gardens = new ArrayList<DummyGen.GardenWTFWR>(Arrays.asList(new DummyGen.GardenWTFWR[]{
        		new DummyGen.GardenWTFWR(Gardens.berry, 20),
        		new DummyGen.GardenWTFWR(Gardens.herb, 10),
        		new DummyGen.GardenWTFWR(Gardens.stalk, 10),
        		new DummyGen.GardenWTFWR(Gardens.allium, 10)
        		
        }));
        mountain_gardens = new ArrayList<DummyGen.GardenWTFWR>(Arrays.asList(new DummyGen.GardenWTFWR[]{
        		new DummyGen.GardenWTFWR(Gardens.allium, 20),
        		new DummyGen.GardenWTFWR(Gardens.berry, 20),
        		new DummyGen.GardenWTFWR(Gardens.gourd, 5),
        		new DummyGen.GardenWTFWR(Gardens.grass, 5)
        }));
        arid_gardens = new ArrayList<DummyGen.GardenWTFWR>(Arrays.asList(new DummyGen.GardenWTFWR[]{
        		new DummyGen.GardenWTFWR(Gardens.grass, 40),
        		new DummyGen.GardenWTFWR(Gardens.ground, 40),
        		new DummyGen.GardenWTFWR(Gardens.stalk, 20)

        }));
        lush_gardens = new ArrayList<DummyGen.GardenWTFWR>(Arrays.asList(new DummyGen.GardenWTFWR[]{
        		new DummyGen.GardenWTFWR(Gardens.berry, 20),
        		new DummyGen.GardenWTFWR(Gardens.tropical, 40),
        		new DummyGen.GardenWTFWR(Gardens.leafy, 20),
        		new DummyGen.GardenWTFWR(Gardens.stalk, 20),
        		new DummyGen.GardenWTFWR(Gardens.herb, 20)
        		
        }));
        plains_gardens = new ArrayList<DummyGen.GardenWTFWR>(Arrays.asList(new DummyGen.GardenWTFWR[]{
        		new DummyGen.GardenWTFWR(Gardens.grass, 40),
        		new DummyGen.GardenWTFWR(Gardens.leafy, 15),
        		new DummyGen.GardenWTFWR(Gardens.textile, 10),
        		new DummyGen.GardenWTFWR(Gardens.gourd, 10)
        }));
        swamp_gardens = new ArrayList<DummyGen.GardenWTFWR>(Arrays.asList(new DummyGen.GardenWTFWR[]{
        		new DummyGen.GardenWTFWR(Gardens.leafy, 30),
        		new DummyGen.GardenWTFWR(Gardens.herb, 30),
        		new DummyGen.GardenWTFWR(Gardens.stalk, 30),
        		new DummyGen.GardenWTFWR(Gardens.tropical, 10)
        }));
        
        


	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		// this isn't gonna be pretty. I am desperate to get my shit into worldgen lol.
		// definitely brittle, but not brittle enough to be a pain in the ass for just vanilla.
		// like, if i was guaranteed never to want to change world gen, this implementation will probably be ffine
		// I'll implement it nicely when BoP rises from the ashes~!

		// 1/16 chance to gen a garden
		if (random.nextInt(15) > 0) {
			this.genGarden(
					random,
					world,
					(chunkX * 16) + random.nextInt(15),
					(chunkZ * 16) + random.nextInt(15), 
					world.getBiomeGenForCoords(new BlockPos((chunkX * 16) + 7, 0, (chunkZ * 16) + 7)));
		}

		// same for fruit but since leaves are rarer, so shall be the fruits
		if (random.nextInt(15) > 0) {
			this.genFruit(
					random,
					world,
					(chunkX * 16) + random.nextInt(15),
					(chunkZ * 16) + random.nextInt(15), 
					world.getBiomeGenForCoords(new BlockPos((chunkX * 16) + 7, 0, (chunkZ * 16) + 7)));
		}				
	}
	
	private BlockPos findLowestAir(World world, int x, int z){
		BlockPos bp;
		int y = 128;
		while (y-- > 42){
			bp = new BlockPos(x, y, z);
			if (!world.isAirBlock(bp.down())) {
				return bp;
			}
		}
		return null;
	}

	private void genGarden(Random random, World world, int x, int z, BiomeGenBase biome){
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
		
		// ugh where do i put this crap
		TUMMYGRABBER wr = new TUMMYGRABBER();


		// make sure there's room to grow
		//if (!world.isAirBlock(pos.up())){
		//	System.out.format("   cannot :( - '%s' in the way\n", world.getBlockState(pos));	
		//	return;
		//}

		// some biomes just don't get veggies >:C
		if (never_biomes.contains(biome)) {
			//System.out.format("   NEVER IN %s!\n", biome.biomeName);
			return; 
			}

		// deserts have an even lower chance to grow 
		if (biome.equals(BiomeGenBase.desert)) {
			if (random.nextInt(3) == 0 && soil == Blocks.sand){
				System.out.format(" ! GO DESERT !\n", biome.biomeName, pos);
				world.setBlockState(pos, Gardens.desert.getDefaultState());
			}
			return; 
		}

		// river crops are even more rarer
		if (biome.equals(BiomeGenBase.river)) {
			if (random.nextInt(7) == 0){
				System.out.format(" ! GO RIVER !\n", biome.biomeName, pos);
				world.setBlockState(pos, Gardens.water.getDefaultState());
			}
			return;
		}
		
		if (mountain_biomes.contains(biome)) {
			if (random.nextInt(7) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO MOUNTAINE !\n", biome.biomeName, pos);
				world.setBlockState(pos, wr.getRandomGarden(random, mountain_gardens).getDefaultState());
			}
			return; 
		}
		
		if (taiga_biomes.contains(biome)) {
			if (random.nextInt(7) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO TIGERS! USA #1 !\n", biome.biomeName, pos);
				world.setBlockState(pos, wr.getRandomGarden(random, taiga_gardens).getDefaultState());
			}
			return; 
		}
		
		if (arid_biomes.contains(biome)) {
			if (random.nextInt(5) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO ARID !\n", biome.biomeName, pos);
				world.setBlockState(pos, wr.getRandomGarden(random, arid_gardens).getDefaultState());
			}
			return; 
		}
		
		// at this point, the unchecked biomes are lush enough for a small chance to
		// grow river gardens? :O
		
		if (forest_biomes.contains(biome)) {
			if (random.nextInt(2) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO FORAST !\n", biome.biomeName, pos);
				world.setBlockState(pos, wr.getRandomGarden(random, forest_gardens).getDefaultState());
			}
			return; 
		}
		
		if (lush_biomes.contains(biome)) {
			if (random.nextInt(1) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO JUNGO !\n", biome.biomeName, pos);
				world.setBlockState(pos, wr.getRandomGarden(random, lush_gardens).getDefaultState());
			}
			return; 
		}
		
		if (BiomeGenBase.plains == biome){
			if (random.nextInt(2) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO PLAINS !\n", biome.biomeName, pos);
				world.setBlockState(pos, wr.getRandomGarden(random, plains_gardens).getDefaultState());
			}
			return;			
		}
		if (BiomeGenBase.swampland == biome){
			if (random.nextInt(3) == 0 && (soil == Blocks.dirt || soil == Blocks.grass)){
				System.out.format(" ! GO SKWUMP !\n", biome.biomeName, pos);
				world.setBlockState(pos, wr.getRandomGarden(random, plains_gardens).getDefaultState());
			}
			return;			
		}


		//world.getBlockState(pos).getBlock() == Blocks.water

	}

	private void genFruit(Random random, World world, int x, int z, BiomeGenBase biome) {
		BlockPos pos = null;
		//System.out.format(" ~ trying genFruit in %s @ %s:\n", biome.biomeName, pos);
		//if (pos.getY() < 40){ return; }

	}
	
	

}
