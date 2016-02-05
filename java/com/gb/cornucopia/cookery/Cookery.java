package com.gb.cornucopia.cookery;

import com.gb.cornucopia.GuiHandler;
import com.gb.cornucopia.InvModel;

import java.util.ArrayList;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.cookery.block.BlockCuttingBoard;
import com.gb.cornucopia.cookery.block.BlockStove;
import com.gb.cornucopia.cookery.block.BlockStoveTop;
import com.gb.cornucopia.cookery.block.BlockWaterBasin;
import com.gb.cornucopia.cookery.block.Vessel;
import com.gb.cornucopia.cookery.crafting.Dish;
import com.gb.cornucopia.cookery.crafting.DishRegistry;
import com.gb.cornucopia.cookery.crafting.Ingredient;
import com.gb.cornucopia.cookery.item.ItemCookWare;
import com.gb.cornucopia.fruit.Fruits;
import com.gb.cornucopia.veggie.Veggies;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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

	public static Ingredient mirepoix_part;
	public static Ingredient sweet_berry;
	public static Ingredient citrus;
	//endregion

	public static void preInit(){
		juicer = new ItemCookWare("juicer");
		
		pot = new ItemCookWare("pot", Vessel.POT);
		//pan = new ItemCookWare("pan", Vessel.PAN);
		//skillet = new ItemCookWare("skillet", Vessel.SKILLET);
		//kettle = new ItemCookWare("kettle", Vessel.KETTLE);
		
		cutting_board = new BlockCuttingBoard();
		water_basin = new BlockWaterBasin();
		stove = new BlockStove();
		stovetop = new BlockStoveTop();

	};

	// recipes galore~! i probably will add some here for testing but it'll get more complex later :D
	public static void init(){
		new DishRegistry(cutting_board.GUI_ID);
		//new DishRegistry(Vessel.POT.meta);
		//new DishRegistry(Vessel.PAN.meta);
		//new DishRegistry(Vessel.SKILLET.meta);
		//new DishRegistry(Vessel.KETTLE.meta);
		
		// breading aint easy
		// removeVanillaRecipe(new ItemStack(Items.bread));
	}
	
	private static void removeVanillaRecipe(ItemStack remove){
	    ItemStack recipeResult = null;
	    ArrayList<IRecipe> recipes = (ArrayList)CraftingManager.getInstance().getRecipeList();
	    ArrayList<IRecipe> recipes_to_remove = Lists.newArrayList();

	    for (IRecipe r : recipes)
	    {
	        if (ItemStack.areItemStacksEqual(remove, r.getRecipeOutput()))
	        {
	            recipes_to_remove.add(r);
	        }
	    }
	    
	    for (IRecipe rm : recipes_to_remove)
	    {
	    	recipes.remove(rm);
	    }
	    
	}
	
}
