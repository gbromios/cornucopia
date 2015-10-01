package com.gb.harvestcraft.fruit;

import java.util.HashMap;

import com.gb.harvestcraft.HarvestCraft;
import com.gb.harvestcraft.fruit.block.BlockCropFruit;
import com.gb.harvestcraft.fruit.block.BlockLeafFruit;
import com.gb.harvestcraft.fruit.block.BlockSaplingFruit;
import com.gb.harvestcraft.fruit.item.ItemRawFruit;

import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Fruits {
	protected static final HashMap<String, Fruit> fruitMap = new HashMap<String, Fruit>();

	//region all the fruits
	public static Fruit almond;
	public static Fruit apricot;
	public static Fruit avocado;
	public static Fruit banana;
	public static Fruit candlenut;
	public static Fruit cashew;
	public static Fruit cherry;
	public static Fruit chestnut;
	public static Fruit cinnamon;
	public static Fruit coconut;
	public static Fruit date;
	public static Fruit dragonfruit;
	public static Fruit durian;
	public static Fruit fig;
	public static Fruit grapefruit;
	public static Fruit lemon;
	public static Fruit lime;
	public static Fruit mango;
	public static Fruit nutmeg;
	public static Fruit olive;
	public static Fruit orange;
	public static Fruit papaya;
	public static Fruit peach;
	public static Fruit pear;
	public static Fruit pecan;
	public static Fruit peppercorn;
	public static Fruit persimmon;
	public static Fruit pistachio;
	public static Fruit plum;
	public static Fruit pomegranate;
	public static Fruit starfruit;
	public static Fruit vanillabean;
	public static Fruit walnut;
	//endregion
	
	public static Fruit createFruit(String name, ItemRawFruit raw, BlockSaplingFruit sapling, BlockLeafFruit leaf, BlockCropFruit crop, IBlockState wood){
		// take fresh instances and save them in static fields
		Fruit fs = new Fruit(raw, sapling, leaf, crop, wood);
		fruitMap.put(name, fs);
		return fs;

	}
	
	public static Fruit createFruit(String name){
		return createFruit(name,
				new ItemRawFruit(name),
				new BlockSaplingFruit(name),
				new BlockLeafFruit(name),
				new BlockCropFruit(name),
				Blocks.log.getDefaultState()
				);
	}

	public static void preInit(){
		//region instantiate Fruit objects
		almond = createFruit("almond");
		apricot = createFruit("apricot");
		avocado = createFruit("avocado");
		banana = createFruit("banana");
		candlenut = createFruit("candlenut");
		cashew = createFruit("cashew");
		cherry = createFruit("cherry");
		chestnut = createFruit("chestnut");
		cinnamon = createFruit("cinnamon");
		coconut = createFruit("coconut");
		date = createFruit("date");
		dragonfruit = createFruit("dragonfruit");
		durian = createFruit("durian");
		fig = createFruit("fig");
		grapefruit = createFruit("grapefruit");
		lemon = createFruit("lemon");
		lime = createFruit("lime");
		mango = createFruit("mango");
		nutmeg = createFruit("nutmeg");
		olive = createFruit("olive");
		orange = createFruit("orange");
		papaya = createFruit("papaya");
		peach = createFruit("peach");
		pear = createFruit("pear");
		pecan = createFruit("pecan");
		peppercorn = createFruit("peppercorn");
		persimmon = createFruit("persimmon");
		pistachio = createFruit("pistachio");
		plum = createFruit("plum");
		pomegranate = createFruit("pomegranate");
		starfruit = createFruit("starfruit");
		vanillabean = createFruit("vanillabean");
		walnut = createFruit("walnut");
		//endregion
	}
	
	public static void init(){
		createModels();
	}

	private static void createModels(){
		for (Fruit f : fruitMap.values()) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
					f.raw,
					0,
					new ModelResourceLocation(String.format("%s:%s", HarvestCraft.MODID, f.raw.name), "inventory") 
				);

			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
					Item.getItemFromBlock(f.sapling),
					0,
					new ModelResourceLocation(String.format("%s:%s", HarvestCraft.MODID, f.sapling.name), "inventory")
				);
			
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
					Item.getItemFromBlock(f.leaf),
					0,
					new ModelResourceLocation("minecraft:oak_leaves", "inventory")
				);
			
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
					Item.getItemFromBlock(f.crop),
					0,
					new ModelResourceLocation(String.format("%s:%s", HarvestCraft.MODID, f.crop.name), "inventory")
				);
			
		}
	}


}
