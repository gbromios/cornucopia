package com.gb.cornucopia.cookery;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.cheese.Cheese;
import com.gb.cornucopia.cookery.brewing.BlockBarrel;
import com.gb.cornucopia.cookery.brewing.BlockBarrelEmpty;
import com.gb.cornucopia.cookery.cuttingboard.BlockCuttingBoard;
import com.gb.cornucopia.cookery.mill.BlockMill;
import com.gb.cornucopia.cookery.mill.BlockMillTop;
import com.gb.cornucopia.cookery.presser.BlockPresser;
import com.gb.cornucopia.cookery.presser.BlockPresserTop;
import com.gb.cornucopia.cookery.stove.BlockStove;
import com.gb.cornucopia.cookery.stove.BlockStoveTop;
import com.gb.cornucopia.cuisine.Cuisine;
import com.gb.cornucopia.veggie.Veggie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Cookery {
	//region yawn static fields
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

	public static BlockBarrelEmpty empty_barrel;
	public static Item barrel_hoop;
	public static Item barrel_stave;
	public static BlockBarrel wine_barrel;
	public static BlockBarrel cider_barrel;
	public static BlockBarrel cordial_barrel;
	public static BlockBarrel mead_barrel;
	public static BlockBarrel cheese_barrel;
	public static BlockBarrel vinegar_barrel;
	public static BlockBarrel pickle_barrel;
	public static BlockBarrel anchovy_barrel;
	public static BlockBarrel beer_barrel;

	//endregion


	public static void preInit() {
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
		barrel_hoop = new Item().setUnlocalizedName("brew_barrel_hoop").setRegistryName("brew_barrel_hoop");
		barrel_hoop.setCreativeTab(CornuCopia.tabCookery);
		InvModel.add(barrel_hoop);

		barrel_stave = new Item().setUnlocalizedName("brew_barrel_stave").setRegistryName("brew_barrel_stave");
		barrel_stave.setCreativeTab(CornuCopia.tabCookery);
		InvModel.add(barrel_stave);

		empty_barrel = new BlockBarrelEmpty("empty");
	}

	public static void init() {
//		initBarrels(); TODO hella broken right now
	}

	// finished barrel recipes depend on items that will have been initialized in preInit, so wait until init to create barrels 
	private static void initBarrels() {
		wine_barrel = new BlockBarrel("wine", 1, new ItemStack[]{new ItemStack(Cuisine.wine, 2)}, new Item[]{Cuisine.grape_juice, Cuisine.grape_juice, Cuisine.grape_juice}, false);
		cider_barrel = new BlockBarrel("cider", 1, new ItemStack[]{new ItemStack(Cuisine.cider, 2)}, new Item[]{Cuisine.apple_juice, Cuisine.apple_juice, Cuisine.apple_juice}, true);
		cordial_barrel = new BlockBarrel("cordial", 1, new ItemStack[]{new ItemStack(Cuisine.cordial, 2)}, new Item[]{Cuisine.blueberry_juice, Cuisine.blueberry_juice, Cuisine.blueberry_juice}, true);
		beer_barrel = new BlockBarrel("beer", 1, new ItemStack[]{new ItemStack(Cuisine.beer, 4)}, new Item[]{Cuisine.mash, Cuisine.mash, Cuisine.mash}, false);
		pickle_barrel = new BlockBarrel("pickle", 1, new ItemStack[]{new ItemStack(Cuisine.pickle, 3)}, new Item[]{Cuisine.vinegar, Veggie.cucumber.raw, Cuisine.vinegar}, false);
		anchovy_barrel = new BlockBarrel("anchovy", 1, new ItemStack[]{new ItemStack(Cuisine.anchovy, 3)}, new Item[]{Cuisine.salt, Items.FISH, Cuisine.salt}, false);
		mead_barrel = new BlockBarrel("mead", 1, new ItemStack[]{new ItemStack(Cuisine.mead, 2)}, new Item[]{Bees.honey_raw, Bees.honey_raw, Bees.honey_raw}, false);
		cheese_barrel = new BlockBarrel("cheese", 1, new ItemStack[]{new ItemStack(Item.getItemFromBlock(Cheese.cheese_wheel_young))}, new Item[]{Items.MILK_BUCKET, Items.MILK_BUCKET, Items.MILK_BUCKET}, (int) 1.8e+6, false); // 30 minutes
		vinegar_barrel = new BlockBarrel("vinegar", 1, new ItemStack[]{new ItemStack(Cuisine.vinegar, 4)}, new Item[]{Cuisine.wine}, (int) 1.2e+6, false); // 20 minutes
	}

	public static void postInit() {
	}


}
