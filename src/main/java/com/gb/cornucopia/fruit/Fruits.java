package com.gb.cornucopia.fruit;

import java.util.HashMap;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.fruit.block.BlockFruitCrop;
import com.gb.cornucopia.fruit.block.BlockFruitLeaf;
import com.gb.cornucopia.fruit.block.BlockFruitSapling;
import com.gb.cornucopia.fruit.item.ItemFruitRaw;

import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Items;

public class Fruits {
	protected static final HashMap<String, Fruit> fruitMap = new HashMap<>();
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
	public static Fruit pommegranite;
	public static Fruit grapefruit;
	public static Fruit kiwi;
	public static Fruit peach;

	public static Fruit createFruit(final String name, final ItemFruitRaw raw, final BlockFruitSapling sapling, final BlockFruitLeaf leaf, final BlockFruitCrop crop, final BlockPlanks.EnumType wood){
		// take fresh instances and save them in static fields
		final Fruit f = new Fruit(raw, sapling, leaf, crop, wood);
		fruitMap.put(name, f);
		return f;
	}
	
	public static Fruit createFruit(final String name, final BlockPlanks.EnumType wood){
		return createFruit(name,
				new ItemFruitRaw(name),
				new BlockFruitSapling(name),
				new BlockFruitLeaf(name),
				new BlockFruitCrop(name),
				wood
				);
	}

	public static Fruit createFruit(final String name){
		return createFruit(name, BlockPlanks.EnumType.OAK);
	}

	public static void preInit(){
		// apples belong here!
		
		Items.apple.setCreativeTab(CornuCopia.tabFruit);
		
		almond = createFruit("almond", BlockPlanks.EnumType.SPRUCE);
		avocado = createFruit("avocado", BlockPlanks.EnumType.SPRUCE);
		banana = createFruit("banana", BlockPlanks.EnumType.JUNGLE);
		cherry = createFruit("cherry");
		coconut = createFruit("coconut", BlockPlanks.EnumType.JUNGLE);
		coffee = createFruit("coffee", BlockPlanks.EnumType.DARK_OAK);
		date = createFruit("date", BlockPlanks.EnumType.ACACIA);
		fig = createFruit("fig", BlockPlanks.EnumType.ACACIA);
		grapefruit = createFruit("grapefruit", BlockPlanks.EnumType.SPRUCE);
		kiwi = createFruit("kiwi", BlockPlanks.EnumType.DARK_OAK);
		lemon = createFruit("lemon", BlockPlanks.EnumType.BIRCH);
		lime = createFruit("lime", BlockPlanks.EnumType.BIRCH);
		olive = createFruit("olive", BlockPlanks.EnumType.ACACIA);
		orange = createFruit("orange", BlockPlanks.EnumType.JUNGLE);
		peach = createFruit("peach");
		pear = createFruit("pear");
		plum = createFruit("plum",BlockPlanks.EnumType.BIRCH);
		pommegranite = createFruit("pommegranite", BlockPlanks.EnumType.DARK_OAK);
	}

	public static void init(){
		for (Fruit f : fruitMap.values()){
			f.leaf.setGraphicsLevel(true); 
		}
	}
}
