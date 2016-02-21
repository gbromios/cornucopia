package com.gb.cornucopia.cuisine;

import java.util.ArrayList;
import java.util.List;

import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cookery.Vessel;
import com.gb.cornucopia.cuisine.dish.Dish;
import com.gb.util.WeightedArray;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Cuisine {
	// intermediate food items
	public static ItemCuisine flour;
	public static ItemCuisine soda; // as in sodium bicarbonate
	public static ItemCuisine salt; // table salt
	public static ItemCuisine vinegar;	
	public static ItemCuisine mash;
	public static ItemCuisine anchovy;
	public static ItemCuisine pickle;
	public static ItemCuisine bread_dough;
	public static ItemCuisine batter;
	public static ItemCuisine pastry_dough;
	public static ItemCuisine pasta_dough;
	public static ItemCuisine fresh_pasta;
	public static ItemCuisine peanut_butter;
	public static ItemCuisine olive_oil;
	public static ItemCuisine grape_juice;
	public static ItemCuisine fruit_juice;
	public static ItemCuisine berry_juice;
	public static ItemCuisine orange_juice;
	public static ItemCuisine lime_juice;
	public static ItemCuisine lemon_juice;
	public static ItemCuisine butter;
	public static ItemCuisine canola_oil;
	public static ItemCuisine tofu;

	// can't do anything special w/ these.... yet!
	public static ItemCuisine roast_coffee;
	public static ItemCuisine dried_tea;

	// CHEEEEESE
	public static ItemCuisine mozzarella;
	public static ItemCuisine fresh_cheese;
	public static ItemCuisine aged_cheese;

	// here are some actual food items cooked in dishes!
	public static ItemCuisine eggs_over_easy;
	public static ItemCuisine garden_salad;
	public static ItemCuisine ketchup;
	public static ItemCuisine mayonnaise;
	public static ItemCuisine honey_mustard;
	public static ItemCuisine popcorn;

	public static ItemCuisine red_sauce;
	public static ItemCuisine toast;
	public static ItemCuisine cheese_sauce;
	public static ItemCuisine simple_spaghetti;
	public static ItemCuisine caesar_salad;
	public static ItemCuisine chicken_caesar_salad;
	public static ItemCuisine buttered_noodles;
	public static ItemCuisine cheesy_noodles;
	public static ItemCuisine spaghetti_bolognese;
	public static ItemCuisine fish_and_chips;
	public static ItemCuisine bruscetta;
	public static ItemCuisine kebab;


	// delicicious dranks
	public static ItemLibation wine;
	public static ItemLibation cider;
	public static ItemLibation cordial;
	public static ItemLibation beer;
	public static ItemLibation nog;
	public static ItemLibation mead;
	public static ItemLibation spirits;
	public static ItemLibation bloody_mary;


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

	// i.e. inedible stables OR placeholders
	private static ItemCuisine _generic(String name){ return new ItemCuisine(name, 0, 0F); }

	public static void preInit(){		
		flour = _generic("flour");
		soda = _generic("soda");
		salt = _generic("salt");
		vinegar = _generic("vinegar");
		mash = _generic("mash");
		pickle = _generic("pickle");
		anchovy = _generic("anchovy");
		bread_dough = _generic("bread_dough");
		batter = _generic("batter");
		pastry_dough = _generic("pastry_dough");
		pasta_dough = _generic("pasta_dough");
		fresh_pasta = _generic("fresh_pasta");
		peanut_butter = new ItemCuisine("peanut_butter", 2, 0.75F);
		olive_oil = new ItemCuisine("olive_oil", 1, 0.5F);
		grape_juice = new ItemCuisine("grape_juice", 1, 0.15F);
		berry_juice = new ItemCuisine("berry_juice", 1, 0.15F);
		fruit_juice = new ItemCuisine("fruit_juice", 1, 0.15F);
		orange_juice = new ItemCuisine("orange_juice", 1, 0.15F);
		lime_juice = _generic("lime_juice");
		lemon_juice = _generic("lemon_juice");

		butter = _generic("butter");
		canola_oil = _generic("canola_oil");
		tofu = new ItemCuisine("tofu", 3, 0.25F);

		roast_coffee = _generic("roast_coffee");
		dried_tea    = _generic("dried_tea");

		mozzarella = new ItemCuisine("mozzarella", 3, 0.3F);
		fresh_cheese = new ItemCuisine("fresh_cheese", 4, 0.3F);
		aged_cheese = new ItemCuisine("aged_cheese", 3, 0.5F);

		eggs_over_easy = new ItemCuisine("eggs_over_easy", 4, 0.35F);
		toast = new ItemCuisine("toast", 4, 0.35F);
		garden_salad = new ItemCuisine("garden_salad", 3, 0.15F);
		ketchup = new ItemCuisine("ketchup", 1, 0.05F);

		cheese_sauce = new ItemCuisine("cheese_sauce", 1, 0.12F)
				.setContainerItem(Items.bowl)
				.setMaxStackSize(4)
				;
		red_sauce = new ItemCuisine("red_sauce", 1, 0.12F)
				.setContainerItem(Items.bowl)
				.setMaxStackSize(4)
				;
		popcorn = new ItemCuisine("popcorn", 3, 0.09F)
				.setContainerItem(Items.bowl)
				.setMaxStackSize(16)
				;

		// larger meals!
		cheesy_noodles = new ItemCuisine("cheesy_noodles", 12, 1.2F)
				.setContainerItem(Items.bowl)
				.setMaxStackSize(4)
				;
		spaghetti_bolognese = new ItemCuisine("spaghetti_bolognese", 12, 1.3F)
				.setContainerItem(Items.bowl)
				.setMaxStackSize(4)
				;
		chicken_caesar_salad = new ItemCuisine("chicken_caesar_salad", 11, 1.2F)
				.setContainerItem(Items.bowl)
				.setMaxStackSize(4)
				;
		caesar_salad = new ItemCuisine("caesar_salad", 8, 0.8F)
				.setContainerItem(Items.bowl)
				.setMaxStackSize(4)
				;
		bruscetta = new ItemCuisine("bruscetta", 6, 0.4F)
				.setMaxStackSize(8)
				;
		kebab = new ItemCuisine("kebab", 11, 1.0F)
				.setContainerItem(Items.stick)
				.setMaxStackSize(4)
				;
		
		wine = new ItemLibation("wine");
		cordial = new ItemLibation("cordial");
		cider = new ItemLibation("cider");
		beer = new ItemLibation("beer");
		nog = new ItemLibation("nog");
		mead = new ItemLibation("mead");

		// no way to make these yet ;)
		spirits = new ItemLibation("spirits");
		bloody_mary = new ItemLibation("bloody_mary");

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

	}

	public static void init(){
		// breading aint easy
		// removeVanillaRecipe(new ItemStack(Items.bread));
	}

	public static void postInit(){
		// here's where all the recipes and so forth come together. this is the very last module init call.
		Ingredient.init();
		Dish.init();

		removeVanillaRecipe(new ItemStack(Items.bread)); // sorry guys!!!!
		// how u really make bread tho:
		GameRegistry.addSmelting(Cuisine.bread_dough, new ItemStack(Items.bread), 1.0F);
		
		// not quite removing a vanilla recipe:
		Items.milk_bucket.setMaxStackSize(8);

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
