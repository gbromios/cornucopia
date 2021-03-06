package com.gb.cornucopia.cuisine;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cheese.Cheese;
import com.gb.cornucopia.cuisine.dish.Dish;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggie;
import com.gb.util.WeightedArray;
import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cuisine {
	// intermediate food items
	public static ItemCuisine flour;
	public static ItemCuisine corn_flour;
	public static ItemCuisine soda; // as in sodium bicarbonate
	public static ItemCuisine salt; // table salt
	public static ItemCuisine vinegar;
	public static ItemCuisine mash;
	public static ItemCuisine anchovy;
	public static ItemCuisine pickle;
	public static ItemCuisine bread_dough;
	public static ItemCuisine tortilla_dough;
	public static ItemCuisine batter;
	public static ItemCuisine pastry_dough;
	public static ItemCuisine pasta_dough;
	public static ItemCuisine fresh_pasta;
	public static ItemCuisine peanut_butter;
	public static ItemCuisine olive_oil;

	public static ItemCuisine carrot_juice;
	public static ItemCuisine melon_juice;
	public static ItemCuisine apple_juice;
	public static ItemCuisine cherry_juice;
	public static ItemCuisine date_juice;
	public static ItemCuisine fig_juice;
	public static ItemCuisine grapefruit_juice;
	public static ItemCuisine kiwi_juice;
	public static ItemCuisine lemon_juice;
	public static ItemCuisine lime_juice;
	public static ItemCuisine orange_juice;
	public static ItemCuisine peach_juice;
	public static ItemCuisine pear_juice;
	public static ItemCuisine plum_juice;
	public static ItemCuisine pomegranate_juice;
	public static ItemCuisine beet_juice;
	public static ItemCuisine blackberry_juice;
	public static ItemCuisine blueberry_juice;
	public static ItemCuisine pineapple_juice;
	public static ItemCuisine raspberry_juice;
	public static ItemCuisine strawberry_juice;
	public static ItemCuisine tomato_juice;
	public static ItemCuisine grape_juice;
	private static final HashMap<Item, Item> juice_map = new HashMap<>();

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

	public static ItemCuisine smoothie;

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

	public static Item rock_salt;

	// i.e. inedible stables OR placeholders
	private static ItemCuisine _generic(String name) {
		return new ItemCuisine(name, 0, 0F);
	}

	public static void preInit() {
		// wut
		rock_salt = new Item();
		rock_salt.setUnlocalizedName("cuisine_rock_salt");
		rock_salt.setRegistryName("cuisine_rock_salt");
		rock_salt.setCreativeTab(CornuCopia.tabCuisine);
		InvModel.add(rock_salt);


		flour = _generic("flour");
		corn_flour = _generic("corn_flour");
		soda = _generic("soda");
		salt = _generic("salt");
		vinegar = _generic("vinegar");
		mash = _generic("mash");
		pickle = _generic("pickle");
		anchovy = _generic("anchovy");
		bread_dough = _generic("bread_dough");
		tortilla_dough = _generic("tortilla_dough");
		batter = _generic("batter");
		pastry_dough = _generic("pastry_dough");
		pasta_dough = _generic("pasta_dough");
		fresh_pasta = _generic("fresh_pasta");
		peanut_butter = new ItemCuisine("peanut_butter", 2, 0.75F);
		olive_oil = new ItemCuisine("olive_oil", 1, 0.5F);

		carrot_juice = new ItemCuisine("carrot_juice", 2, 0.15F);
		apple_juice = new ItemCuisine("apple_juice", 2, 0.15F);
		melon_juice = new ItemCuisine("melon_juice", 2, 0.15F);
		cherry_juice = new ItemCuisine("cherry_juice", 2, 0.15F);
		//date_juice = new ItemCuisine("date_juice", 2, 0.15F);
		fig_juice = new ItemCuisine("fig_juice", 2, 0.15F);
		grapefruit_juice = new ItemCuisine("grapefruit_juice", 2, 0.15F);
		kiwi_juice = new ItemCuisine("kiwi_juice", 2, 0.15F);
		orange_juice = new ItemCuisine("orange_juice", 2, 0.15F);
		peach_juice = new ItemCuisine("peach_juice", 2, 0.15F);
		pear_juice = new ItemCuisine("pear_juice", 2, 0.15F);
		plum_juice = new ItemCuisine("plum_juice", 2, 0.15F);
		pomegranate_juice = new ItemCuisine("pomegranate_juice", 2, 0.15F);
		beet_juice = new ItemCuisine("beet_juice", 2, 0.15F);
		blackberry_juice = new ItemCuisine("blackberry_juice", 2, 0.15F);
		blueberry_juice = new ItemCuisine("blueberry_juice", 2, 0.15F);
		pineapple_juice = new ItemCuisine("pineapple_juice", 2, 0.15F);
		raspberry_juice = new ItemCuisine("raspberry_juice", 2, 0.15F);
		strawberry_juice = new ItemCuisine("strawberry_juice", 2, 0.15F);
		tomato_juice = new ItemCuisine("tomato_juice", 2, 0.15F);
		grape_juice = new ItemCuisine("grape_juice", 2, 0.15F);

		lime_juice = _generic("lime_juice");
		lemon_juice = _generic("lemon_juice");

		butter = _generic("butter");
		canola_oil = _generic("canola_oil");
		tofu = new ItemCuisine("tofu", 3, 0.25F);

		roast_coffee = _generic("roast_coffee");
		dried_tea = _generic("dried_tea");

		mozzarella = new ItemCuisine("mozzarella", 3, 0.3F);
		fresh_cheese = new ItemCuisine("fresh_cheese", 4, 0.3F);
		aged_cheese = new ItemCuisine("aged_cheese", 3, 0.5F);

		eggs_over_easy = new ItemCuisine("eggs_over_easy", 4, 0.35F);
		toast = new ItemCuisine("toast", 4, 0.35F);
		garden_salad = new ItemCuisine("garden_salad", 3, 0.15F);
		ketchup = new ItemCuisine("ketchup", 1, 0.05F);

		cheese_sauce = new ItemCuisine("cheese_sauce", 1, 0.12F)
				.setContainerItem(Items.BOWL)
				.setMaxStackSize(4)
		;
		red_sauce = new ItemCuisine("red_sauce", 1, 0.12F)
				.setContainerItem(Items.BOWL)
				.setMaxStackSize(4)
		;
		popcorn = new ItemCuisine("popcorn", 3, 0.09F)
				.setContainerItem(Items.BOWL)
				.setMaxStackSize(16)
		;

		// larger meals!
		cheesy_noodles = new ItemCuisine("cheesy_noodles", 12, 1.2F, 1)
				.setContainerItem(Items.BOWL)
				.setMaxStackSize(4)
		;
		spaghetti_bolognese = new ItemCuisine("spaghetti_bolognese", 12, 1.3F, 1)
				.setContainerItem(Items.BOWL)
				.setMaxStackSize(4)
		;
		chicken_caesar_salad = new ItemCuisine("chicken_caesar_salad", 11, 1.2F, 1)
				.setContainerItem(Items.BOWL)
				.setMaxStackSize(4)
		;
		fish_and_chips = new ItemCuisine("fish_and_chips", 15, 1.6F, 1)
				.setContainerItem(Items.BOWL)
				.setMaxStackSize(4)
		;
		caesar_salad = new ItemCuisine("caesar_salad", 8, 0.8F, 1)
				.setContainerItem(Items.BOWL)
				.setMaxStackSize(4)
		;
		bruscetta = new ItemCuisine("bruscetta", 6, 0.4F, 1)
				.setMaxStackSize(8)
		;
		kebab = new ItemCuisine("kebab", 11, 1.0F, 1)
				.setContainerItem(Items.STICK)
				.setMaxStackSize(4)
		;
		smoothie = new ItemCuisine("smoothie", 6, 0.6F, 1)
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
				.add(mustard = _generic("mustard"), 20)
		;

		herb_drops
				.add(basil = _generic("basil"), 20)
				.add(cilantro = _generic("cilantro"), 20)
				.add(mint = _generic("mint"), 20)
				.add(oregano = _generic("oregano"), 20)
				.add(rosemary = _generic("rosemary"), 20)
		;

		juice_map.put(Items.CARROT, Cuisine.carrot_juice);
		juice_map.put(Items.APPLE, Cuisine.apple_juice);
		juice_map.put(Items.MELON, Cuisine.melon_juice);
		juice_map.put(Fruit.cherry.raw, Cuisine.cherry_juice);
		juice_map.put(Fruit.date.raw, Cuisine.date_juice);
		juice_map.put(Fruit.fig.raw, Cuisine.fig_juice);
		juice_map.put(Fruit.grapefruit.raw, Cuisine.grapefruit_juice);
		juice_map.put(Fruit.kiwi.raw, Cuisine.kiwi_juice);
		juice_map.put(Fruit.lemon.raw, Cuisine.lemon_juice);
		juice_map.put(Fruit.lime.raw, Cuisine.lime_juice);
		juice_map.put(Fruit.orange.raw, Cuisine.orange_juice);
		juice_map.put(Fruit.peach.raw, Cuisine.peach_juice);
		juice_map.put(Fruit.pear.raw, Cuisine.pear_juice);
		juice_map.put(Fruit.plum.raw, Cuisine.plum_juice);
		juice_map.put(Fruit.pomegranate.raw, Cuisine.pomegranate_juice);
		juice_map.put(Veggie.beet.raw, Cuisine.beet_juice);
		juice_map.put(Veggie.blackberry.raw, Cuisine.blackberry_juice);
		juice_map.put(Veggie.blueberry.raw, Cuisine.blueberry_juice);
		juice_map.put(Veggie.pineapple.raw, Cuisine.pineapple_juice);
		juice_map.put(Veggie.raspberry.raw, Cuisine.raspberry_juice);
		juice_map.put(Veggie.strawberry.raw, Cuisine.strawberry_juice);
		juice_map.put(Veggie.tomato.raw, Cuisine.tomato_juice);
		juice_map.put(Veggie.grape.raw, Cuisine.grape_juice);
		;

	}

	public static void init() {
//		GameRegistry.addShapelessRecipe(new ItemStack(Cuisine.fresh_cheese, 8), Cheese.cheese_wheel_young);
	}

	public static void postInit() {
		// here's where all the recipes and so forth come together. this is the very last module init call.
		Ingredient.init();
		Dish.init();

		removeVanillaRecipe(new ItemStack(Items.BREAD)); // sorry guys!!!!
		// how u really make bread tho:
		GameRegistry.addSmelting(Cuisine.bread_dough, new ItemStack(Items.BREAD), 0.25F);

		removeVanillaRecipe(new ItemStack(Items.CAKE)); // srsly
		GameRegistry.addSmelting(Cuisine.batter, new ItemStack(Items.CAKE), 0.25F);

		// removeVanillaRecipe(new ItemStack(Items.cookie)); // YOU'RE NEXT


		// not quite removing a vanilla recipe:
		Items.MILK_BUCKET.setMaxStackSize(8);

		// rock salt is dropped by mining deep in the earth, turns into 8x table salt
		// might make this a mill recipe, but for now just leave it vanilla crafting
//		GameRegistry.addShapelessRecipe(new ItemStack(Cuisine.salt, 16), Cuisine.rock_salt);

	}

	private static void removeVanillaRecipe(final ItemStack remove) {
		final List<IRecipe> recipes = Lists.newArrayList(ForgeRegistries.RECIPES);
		final ArrayList<IRecipe> recipes_to_remove = Lists.newArrayList();

		for (IRecipe r : recipes) {
			if (ItemStack.areItemStacksEqual(remove, r.getRecipeOutput())) {
				recipes_to_remove.add(r);
			}
		}
		for (IRecipe rm : recipes_to_remove) {
			recipes.remove(rm);
		}
	}

	public static Item getJuice(Item i) {
		return juice_map.get(i);
	}

	public static boolean hathJuice(Item i) {
		return juice_map.containsKey(i);
	}

}
