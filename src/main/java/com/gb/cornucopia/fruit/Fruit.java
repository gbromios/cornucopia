package com.gb.cornucopia.fruit;

import java.util.HashMap;
import java.util.Random;

import com.gb.cornucopia.CornuCopia;
import com.gb.util.WeightedArray;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Items;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import scala.actors.threadpool.Arrays;

public class Fruit {
	// static fields
	private static final HashMap<String, Fruit> fruitMap = new HashMap<>();
	public static Fruit almond;
	public static Fruit avocado;
	public static Fruit banana;
	public static Fruit cherry;
	public static Fruit lemon;
	public static Fruit lime;
	public static Fruit orange;
	public static Fruit plum;
	public static Fruit olive;
	public static Fruit pear;
	public static Fruit date;
	public static Fruit coconut;
	public static Fruit coffee;
	public static Fruit fig;
	public static Fruit pomegranate;
	public static Fruit grapefruit;
	public static Fruit kiwi;
	public static Fruit peach;

	public static void preInit(){
		Items.apple.setCreativeTab(CornuCopia.tabFruit);
		almond = new Fruit("almond", BlockPlanks.EnumType.SPRUCE);
		avocado = new Fruit("avocado", BlockPlanks.EnumType.SPRUCE);
		banana = new Fruit("banana", BlockPlanks.EnumType.JUNGLE);
		cherry = new Fruit("cherry");
		coconut = new Fruit("coconut", BlockPlanks.EnumType.JUNGLE);
		coffee = new Fruit("coffee", BlockPlanks.EnumType.DARK_OAK);
		date = new Fruit("date", BlockPlanks.EnumType.ACACIA);
		fig = new Fruit("fig", BlockPlanks.EnumType.ACACIA);
		grapefruit = new Fruit("grapefruit", BlockPlanks.EnumType.SPRUCE);
		kiwi = new Fruit("kiwi", BlockPlanks.EnumType.DARK_OAK);
		lemon = new Fruit("lemon", BlockPlanks.EnumType.BIRCH);
		lime = new Fruit("lime", BlockPlanks.EnumType.BIRCH);
		olive = new Fruit("olive", BlockPlanks.EnumType.ACACIA);
		orange = new Fruit("orange", BlockPlanks.EnumType.JUNGLE);
		peach = new Fruit("peach");
		pear = new Fruit("pear");
		plum = new Fruit("plum",BlockPlanks.EnumType.BIRCH);
		pomegranate = new Fruit("pomegranate", BlockPlanks.EnumType.DARK_OAK);
	}

	public static void init(){
		for (Fruit f : fruitMap.values()){
			f.leaf.setGraphicsLevel(true); 
		}
	}


	
	// instance fields
	public final String name;
	public final ItemFruitRaw raw;
	public final BlockFruitSapling sapling;
	public final BlockFruitLeaf leaf;
	public final BlockFruitCrop crop;

	public Fruit(final String name, final ItemFruitRaw raw, final BlockFruitSapling sapling, final BlockFruitLeaf leaf, final BlockFruitCrop crop, final BlockPlanks.EnumType wood){
		this.name = name;
		this.raw = raw;
		this.sapling = sapling;
		this.leaf = leaf;
		this.crop = crop;

		// hook up needfuls 
		this.sapling.setTreeStates(wood, leaf.getDefaultState());
		this.leaf.setGrows(crop);
		this.crop.setLeaf(leaf);
		this.crop.setDrops(raw, sapling);

		fruitMap.put(name, this);
	}

	public Fruit(final String name, final BlockPlanks.EnumType wood){
		this(name,
				new ItemFruitRaw(name),
				new BlockFruitSapling(name),
				new BlockFruitLeaf(name),
				new BlockFruitCrop(name),
				wood
				);
	}

	public Fruit(final String name){
		this(name, BlockPlanks.EnumType.OAK);
	}
	
	
	private static final WeightedArray<Fruit> jungleFruits = new WeightedArray<>();
	private static final WeightedArray<Fruit> coldFruits = new WeightedArray<>();
	private static final WeightedArray<Fruit> forestFruits = new WeightedArray<>();
	private static final WeightedArray<Fruit> mountainFruits = new WeightedArray<>();
	private static final WeightedArray<Fruit> plainsFruits = new WeightedArray<>();
	private static final WeightedArray<Fruit> dryFruits = new WeightedArray<>();
	public static void postInit() { 
		jungleFruits
		.add(avocado , 10)
		.add(banana , 10)
		.add(coconut , 10)
		.add(coffee , 10)
		.add(date , 10)
		.add(fig , 10)
		.add(kiwi , 10)
		.add(lemon , 10)
		.add(lime , 10)
		.add(orange , 10)
		.add(pomegranate , 10)
		;
		coldFruits
		.add(almond , 10)
		.add(grapefruit , 10)
		;
		forestFruits
		.add(almond , 5)
		.add(cherry , 10)
		.add(peach , 20)
		.add(pear , 20)
		.add(plum , 20)
		;
		mountainFruits
		.add(almond , 10)
		.add(cherry , 10)
		;
		plainsFruits
		.add(lemon , 2)
		.add(lime , 2)
		.add(orange , 2)
		.add(peach , 15)
		.add(pear , 10)
		.add(plum , 10)
		;
		dryFruits
		.add(coffee , 10)
		.add(date , 10)
		.add(fig , 10)
		.add(grapefruit , 10)
		.add(lemon , 15)
		.add(lime , 15)
		.add(olive , 15)
		.add(orange , 10)
		.add(pomegranate , 10)
		;
	}
	
	public static Fruit getAny(Random r){
		//return ((Fruit[])fruitMap.values().toArray())[r.nextInt(fruitMap.size())];
	    int i = (int) (r.nextInt(fruitMap.size()));
	    for(Fruit f: fruitMap.values()) { if (--i < 0) return f; }
	    
	    throw new RuntimeException();
	    
	}
	
	public static Fruit getForBiome(Random r, BiomeGenBase b){
		final String ts = Collections2.transform(Arrays.asList(BiomeDictionary.getTypesForBiome(b)), new Function<BiomeDictionary.Type, String>(){

	        @Override
	        public String apply(final BiomeDictionary.Type type){
	            return  type.name();
	        }

	    }).toString();
		
		System.out.format(" > fb = %s @ %s => ", b.biomeName, ts);
		
		// cold, but only if forest; 1/8 chance
		if (BiomeDictionary.isBiomeOfType(b, Type.COLD)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(8) == 0 ) {
				return coldFruits.getRandom(r);
			}
			return null;
			
		}
		
		// next mountain forests, 1/4 chance
		if (BiomeDictionary.isBiomeOfType(b, Type.MOUNTAIN)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(4) == 0 ) {
				return mountainFruits.getRandom(r);
			}
			return null;
		}

		// jungle + swamp; could remove swamp out for something else if desired
		if (BiomeDictionary.isBiomeOfType(b, Type.WET)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.SWAMP) && r.nextInt(8) > 0 ) {
				// swamp only gets 1/8 chance
				return null;
			}
			return jungleFruits.getRandom(r);
		}
		// regular forest
		if (BiomeDictionary.isBiomeOfType(b, Type.FOREST)) {
			return forestFruits.getRandom(r);
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.HOT) || BiomeDictionary.isBiomeOfType(b, Type.MESA)) {
			if (r.nextInt(2) == 0 ) {
				return dryFruits.getRandom(r);
			}
			return null;
		}
		
		if (BiomeDictionary.isBiomeOfType(b, Type.PLAINS)) {
			if (r.nextInt(2) == 0 ) {
				return plainsFruits.getRandom(r);
			}
		}

		return null; // no fruits here :(
	}
	
	
	

}
