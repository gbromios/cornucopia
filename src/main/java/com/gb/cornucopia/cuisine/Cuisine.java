package com.gb.cornucopia.cuisine;

import java.util.ArrayList;
import java.util.List;

import com.gb.cornucopia.cuisine.dish.Dish;
import com.gb.util.WeightedArray;
import com.google.common.collect.Lists;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class Cuisine {
	// intermediate food items
	public static ItemCuisine flour;
	public static ItemCuisine soda; // as in sodium bicarbonate
	public static ItemCuisine salt;
	public static ItemCuisine vinegar;	
	public static ItemCuisine mash;
	public static ItemCuisine peanut_butter;
	public static ItemCuisine oil_olive;
	public static ItemCuisine grape_juice;

	// can't do anything special w/ these.... yet!
	public static ItemCuisine roast_coffee;
	public static ItemCuisine dried_tea;

	// will probably make distinct types for these guys later: 
	// some spices:
	public static ItemCuisine black_pepper;
	public static ItemCuisine cinnamon;
	public static ItemCuisine chili_powder;
	public static ItemCuisine curry_powder;
	public static ItemCuisine mustard;
	// some herbs:
	public static ItemCuisine mint;
	public static ItemCuisine cilantro;
	public static ItemCuisine basil;
	public static ItemCuisine rosemary;
	public static ItemCuisine oregano;

	public static final WeightedArray<Item> spice_drops = new WeightedArray<>();
	public static final WeightedArray<Item> herb_drops = new WeightedArray<>();

	// here are some actual food items cooked in dishes!
	public static ItemCuisine eggs_over_easy;
	public static ItemCuisine garden_salad;
	public static ItemCuisine roasted_veggies;
	public static ItemCuisine ketchup;
	public static ItemCuisine red_sauce;


	public static ItemLibation wine;
	// CHEEEEESE
	public static ItemCuisine fresh_cheese;
	public static ItemCuisine aged_cheese;
	


	// i.e. inedible stables OR placeholders
	private static ItemCuisine _generic(String name){ return new ItemCuisine(name, 0, 0F); }

	public static void preInit(){		
		flour = _generic("flour");
		soda = _generic("soda");
		salt = _generic("salt");
		vinegar = _generic("vinegar");
		mash = _generic("mash");
		peanut_butter = new ItemCuisine("peanut_butter", 2, 0.75F);
		oil_olive = new ItemCuisine("oil_olive", 1, 0.5F);
		grape_juice = new ItemCuisine("grape_juice", 1, 0.15F);

		roast_coffee = _generic("roast_coffee");
		dried_tea    = _generic("dried_tea");

		spice_drops
		.add(black_pepper = _generic("black_pepper"), 40)
		.add(cinnamon = _generic("cinnamon"), 10)
		.add(chili_powder = _generic("chili_powder"), 10)
		.add(curry_powder = _generic("curry_powder"), 10)
		.add(mustard = _generic ("mustard"), 20)
		;

		herb_drops
		.add(basil = _generic("basil"), 20)
		.add(cilantro = _generic("cilantro"), 20)
		.add(mint = _generic("mint"), 20)
		.add(oregano = _generic("oregano"), 20)
		.add(rosemary = _generic("rosemary"), 20)
		;

		eggs_over_easy = new ItemCuisine("eggs_over_easy", 4, 0.35F);
		roasted_veggies = new ItemCuisine("roasted_veggies", 4, 0.3F);
		garden_salad = new ItemCuisine("garden_salad", 3, 0.15F);
		ketchup = new ItemCuisine("ketchup", 1, 0.05F);
		red_sauce = new ItemCuisine("red_sauce", 2, 0.25F);
		
		
		fresh_cheese = new ItemCuisine("fresh_cheese", 4, 0.3F);
		aged_cheese = new ItemCuisine("aged_cheese", 3, 0.5F);

	}

	public static void init(){
		// breading aint easy
		// removeVanillaRecipe(new ItemStack(Items.bread));
	}

	public static void postInit(){
		// here's where all the recipes and so forth come together. this is the very last module init call.
		Ingredient.init();
		Dish.init();

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
