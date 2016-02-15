package com.gb.cornucopia.fruit;

import java.util.HashMap;

import com.gb.cornucopia.CornuCopia;

import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Items;

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
	public final ItemFruitRaw raw;
	public final BlockFruitSapling sapling;
	public final BlockFruitLeaf leaf;
	public final BlockFruitCrop crop;

	public Fruit(final String name, final ItemFruitRaw raw, final BlockFruitSapling sapling, final BlockFruitLeaf leaf, final BlockFruitCrop crop, final BlockPlanks.EnumType wood){
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
}
