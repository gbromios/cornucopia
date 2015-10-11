package com.gb.cornucopia.cookery;

import com.gb.cornucopia.GuiHandler;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.cookery.block.BlockCuttingBoard;
import com.gb.cornucopia.cookery.block.BlockWaterBasin;
import com.gb.cornucopia.cookery.crafting.Dish;
import com.gb.cornucopia.cookery.crafting.DishRegistry;
import com.gb.cornucopia.cookery.crafting.Ingredient;
import com.gb.cornucopia.cookery.item.ItemCookWare;
import com.gb.cornucopia.fruit.Fruits;
import com.gb.cornucopia.veggie.Veggies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class Cookery {	
	//region yawn static fields;
	public static ItemCookWare juicer;

	public static BlockCuttingBoard cutting_board;
	public static BlockWaterBasin water_barrel;

	public static Ingredient mirepoix_part;
	public static Ingredient sweet_berry;
	public static Ingredient citrus;
	//endregion


	public static void preInit(){
		juicer = new ItemCookWare("juicer");
		cutting_board = new BlockCuttingBoard();
		water_barrel = new BlockWaterBasin();
		
		InvModel.add(juicer, juicer.name);
		InvModel.add(cutting_board, cutting_board.name);
		InvModel.add(water_barrel, water_barrel.name);
	};

	public static void init(){
		new DishRegistry(cutting_board);
	}

	private static void registerIngredients(){
	}

	private static void registerRecipes(){	
	}

}
