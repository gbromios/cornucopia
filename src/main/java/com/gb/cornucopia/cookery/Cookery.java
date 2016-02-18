package com.gb.cornucopia.cookery;

import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.brewing.BlockBarrel;
import com.gb.cornucopia.cookery.cuttingboard.BlockCuttingBoard;
import com.gb.cornucopia.cookery.mill.BlockMill;
import com.gb.cornucopia.cookery.mill.BlockMillTop;
import com.gb.cornucopia.cookery.presser.BlockPresser;
import com.gb.cornucopia.cookery.presser.BlockPresserTop;
import com.gb.cornucopia.cookery.stove.BlockStove;
import com.gb.cornucopia.cookery.stove.BlockStoveTop;
import com.gb.cornucopia.cuisine.Cuisine;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Cookery {	
	//region yawn static fields
	public static ItemCookWare juicer;

	public static ItemCookWare pot;
	public static ItemCookWare pan;
	public static ItemCookWare skillet;
	public static ItemCookWare kettle;

	public static BlockCuttingBoard cutting_board;
	public static BlockWaterBasin water_basin;
	public static BlockStove stove;
	public static BlockStoveTop stovetop;
	public static BlockPresser presser;
	public static BlockPresserTop pressertop;
	public static BlockMill mill;
	public static BlockMillTop milltop;
	
	public static Item empty_barrel;
	public static Item barrel_hoop;
	public static Item barrel_stave;
	public static BlockBarrel wine_barrel;

	//endregion



	public static void preInit(){
		juicer = new ItemCookWare("juicer");

		pot = new ItemCookWare("pot", Vessel.POT);
		pan = new ItemCookWare("pan", Vessel.PAN);
		//skillet = new ItemCookWare("skillet", Vessel.SKILLET);
		//kettle = new ItemCookWare("kettle", Vessel.KETTLE);

		cutting_board = new BlockCuttingBoard();
		water_basin = new BlockWaterBasin();
		stove = new BlockStove();
		stovetop = new BlockStoveTop();

		presser = new BlockPresser();
		pressertop = new BlockPresserTop();

		mill = new BlockMill();
		milltop = new BlockMillTop();

		// things to make barrels empty barrels
		barrel_hoop = new Item().setUnlocalizedName("brew_barrel_hoop");
		GameRegistry.registerItem(barrel_hoop, "brew_barrel_hoop");
		InvModel.add(barrel_hoop, "brew_barrel_hoop");
		
		barrel_stave = new Item().setUnlocalizedName("brew_barrel_stave");
		GameRegistry.registerItem(barrel_stave, "brew_barrel_stave");
		InvModel.add(barrel_stave, "brew_barrel_stave");
		
		empty_barrel = new Item().setUnlocalizedName("brew_empty_barrel");
		GameRegistry.registerItem(empty_barrel, "brew_empty_barrel");
		InvModel.add(empty_barrel, "brew_empty_barrel");
	};
	
	
	public static void init() {
		initCrafting();
		initBarrels();
	}

	// finished barrel recipes depend on items that will have been initialized in preInit, so wait until init to create barrels 
	private static void initBarrels(){
		wine_barrel = new BlockBarrel("wine", 1, new Item[]{Cuisine.wine}, new Item[]{Cuisine.grape_juice, Cuisine.grape_juice, Cuisine.grape_juice});
	};

	private static void initCrafting()
	{	
		// crafting recipes
		GameRegistry.addShapedRecipe(new ItemStack(water_basin),
				"S S", "SBS", "SSS",
				'B', Items.water_bucket,
				'S', Blocks.cobblestone
				);

		GameRegistry.addShapedRecipe(new ItemStack(cutting_board),
				"I  ", "SSS",
				'S', Blocks.wooden_slab,
				'I', Items.iron_ingot
				);
		GameRegistry.addShapedRecipe(new ItemStack(presser),
				"III", "WPW", "WWW",
				'I', Items.iron_ingot,
				'W', Blocks.planks,
				'P', Blocks.piston
				);
		GameRegistry.addShapedRecipe(new ItemStack(mill),
				"SSS", "WIW", "WWW",
				'I', Items.iron_ingot,
				'W', Blocks.planks, 
				'S', Blocks.cobblestone
				);
		GameRegistry.addShapedRecipe(new ItemStack(stove), 
				"III", "S S", "SSS",
				'I', Items.iron_ingot,
				'S', Blocks.cobblestone
				);
		
		GameRegistry.addShapedRecipe(new ItemStack(pot),
				"S S", "I I", "III",
				'I', Items.iron_ingot,
				'S', Items.stick
				);
		GameRegistry.addShapedRecipe(new ItemStack(pan),
				" II", " II", "S  ",
				'I', Items.iron_ingot,
				'S', Items.stick
				);
		GameRegistry.addShapedRecipe(new ItemStack(juicer),
				" I ", "SSS",
				'I', Items.iron_ingot,
				'S', Blocks.stone_slab
				);
		GameRegistry.addShapedRecipe(new ItemStack(barrel_hoop),
				" I ", "I I", " I ",
				'I', Items.iron_ingot
				);
		GameRegistry.addShapedRecipe(new ItemStack(barrel_stave),
				" W ", " W ", " W ",
				'W', Blocks.planks
				);
		GameRegistry.addShapedRecipe(new ItemStack(empty_barrel),
				"SHS", "S S", "SHS",
				'S', barrel_stave,
				'H', barrel_hoop
				);
	}

	public static void postInit() {}
	

}
