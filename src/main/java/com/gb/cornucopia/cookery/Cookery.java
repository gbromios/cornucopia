package com.gb.cornucopia.cookery;

import com.gb.cornucopia.cookery.block.BlockCuttingBoard;
import com.gb.cornucopia.cookery.block.BlockMill;
import com.gb.cornucopia.cookery.block.BlockMillTop;
import com.gb.cornucopia.cookery.block.BlockPresser;
import com.gb.cornucopia.cookery.block.BlockPresserTop;
import com.gb.cornucopia.cookery.block.BlockStove;
import com.gb.cornucopia.cookery.block.BlockStoveTop;
import com.gb.cornucopia.cookery.block.BlockWaterBasin;
import com.gb.cornucopia.cookery.block.Vessel;
import com.gb.cornucopia.cookery.item.ItemCookWare;
import com.gb.cornucopia.cuisine.Ingredient;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

	public static Ingredient mirepoix_part;
	public static Ingredient sweet_berry;
	public static Ingredient citrus;

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


	};
	public static void initCrafting()
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
				" II", " II", "S  ", 'I',
				Items.iron_ingot,
				'S', Items.stick
				);
		GameRegistry.addShapedRecipe(new ItemStack(juicer),
				" I ", "SSS",
				'I', Items.iron_ingot,
				'S', Blocks.stone_slab
				);
	}


}
