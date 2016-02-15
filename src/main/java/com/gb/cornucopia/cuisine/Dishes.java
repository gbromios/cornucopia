package com.gb.cornucopia.cuisine;

import java.util.ArrayList;
import java.util.List;

import com.gb.cornucopia.cookery.Vessel;
import com.gb.cornucopia.cookery.cuttingboard.ContainerCuttingBoard;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggies;
import com.google.common.collect.Lists;


import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class Dishes {
	public static DishRegistry grill;
	public static DishRegistry pan;
	public static DishRegistry pot;
	public static DishRegistry cutting_board;

	
	// recipes galore~! i probably will add some here for testing but it'll get more complex later :D
	public static void init(){
		// set up ingredients so we can use them in dishes
		
		grill = new DishRegistry()
		//.add(new Dish(Cookery.delicious_food, Veggies.corn.raw, Bees.bee, Cookery.juicer))
		.add(new Dish(Cuisine.mirepoix, Ingredients.mirepoix_part, Ingredients.mirepoix_part, Ingredients.mirepoix_part))
		.add(new Dish(Items.iron_ingot, Items.blaze_powder, Items.cookie));
		;
		
		pot = new DishRegistry()
		//.add(new Dish(Cookery.delicious_treat, Fruit.almond.raw, Items.sugar, Items.sugar, Bees.honey_raw));
		;
		
		
		pan = new DishRegistry()
		//.add(new Dish(Cookery.delicious_halloween_snack, Veggies.corn.seed, Items.melon, Items.apple))
		.add(new Dish(Cuisine.mirepoix, Ingredients.mirepoix_part, Ingredients.mirepoix_part, Ingredients.mirepoix_part))
		;
		
		cutting_board = new DishRegistry()
		.add(new Dish(Items.cooked_beef, Veggies.asparagus.raw, Items.gunpowder))
		.add(new Dish(Items.glowstone_dust, Fruit.lemon.raw, Veggies.broccoli.raw))
		;
		//new DishRegistry(Vessel.SKILLET.meta);
		//new DishRegistry(Vessel.KETTLE.meta);

		// breading aint easy
		// removeVanillaRecipe(new ItemStack(Items.bread));
	}

	@SuppressWarnings({ "unchecked" }) // TODO: gonna be using this REAL SOON
	private static void removeVanillaRecipe(final ItemStack remove){
		final List<IRecipe> recipes = (List<IRecipe>)CraftingManager.getInstance().getRecipeList();
		final ArrayList<IRecipe> recipes_to_remove = Lists.newArrayList();

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
