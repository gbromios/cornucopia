package com.gb.cornucopia.cuisine;

import java.util.ArrayList;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggie;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Ingredient {

	public static final Ingredient mirepoix_part = new Ingredient("mirepoix_part");
	public static final Ingredient sweetener = new Ingredient("sweetener");
	public static final Ingredient mountain_berry = new Ingredient("mountain_berry");
	public static final Ingredient citrus = new Ingredient("citrus");
	public static final Ingredient fat = new Ingredient("fat");
	public static final Ingredient red_meat = new Ingredient("red_meat");
	public static final Ingredient cooked_red_meat = new Ingredient("cooked_red_meat");
	public static final Ingredient kebab_veggie = new Ingredient("kebab_veggie");
	public static final Ingredient casserole_veggie = new Ingredient("casserole_veggie");
	public static final Ingredient seasoning = new Ingredient("seasoning");
	public static final Ingredient savory_salad = new Ingredient("savory_salad");
	public static final Ingredient sweet_salad = new Ingredient("sweet_salad");
	public static final Ingredient dressing = new Ingredient("dressing");

	// set up ingredients so we can use them in dishes
	// this can be done late as ingredient members will not
	// be referenced until crafters are looking stuff up
	public static void init() {
		sweetener
		.add(Items.sugar)
		.add(Bees.honey_raw)
		;
		
		mirepoix_part
		.add(Veggie.onion.raw)
		.add(Veggie.celery.raw)
		.add(Veggie.garlic.raw)
		.add(Veggie.bell_pepper.raw)
		.add(Items.carrot)
		;		
		
		mountain_berry
		.add(Veggie.blackberry.raw)
		.add(Fruit.pear.raw)
		.add(Veggie.blueberry.raw)
		.add(Veggie.strawberry.raw)
		.add(Veggie.raspberry.raw)
		.add(Fruit.cherry.raw)
		;
		
		citrus
		.add(Fruit.orange.raw)
		.add(Fruit.lime.raw)
		.add(Fruit.lemon.raw)
		.add(Fruit.grapefruit.raw)
		;
		
		fat
		.add(Cuisine.olive_oil)
		.add(Cuisine.butter)
		.add(Cuisine.canola_oil)
		;
		
		kebab_veggie
		.add(Veggie.bell_pepper.raw)
		.add(Veggie.onion.raw)
		.add(Veggie.zucchini.raw)
		.add(Blocks.red_mushroom)
		.add(Blocks.brown_mushroom)
		;
		
		red_meat
		.add(Items.beef)
		.add(Items.porkchop)
		.add(Items.mutton)
		.add(Items.rabbit)
		;
		
		cooked_red_meat
		.add(Items.cooked_beef)
		.add(Items.cooked_porkchop)
		.add(Items.cooked_mutton)
		.add(Items.cooked_rabbit)
		;
		
		seasoning
		.add(Cuisine.black_pepper)
		.add(Cuisine.basil)
		.add(Cuisine.cilantro)
		.add(Cuisine.chili_powder)
		.add(Cuisine.cinnamon)
		.add(Cuisine.curry_powder)
		.add(Cuisine.mint)
		.add(Cuisine.oregano)
		.add(Cuisine.rosemary)
		.add(Cuisine.salt)
		;
		
		casserole_veggie
		.add(Veggie.artichoke.raw)
		.add(Veggie.asparagus.raw)
		.add(Veggie.broccoli.raw)
		.add(Veggie.celery.raw)
		.add(Veggie.garlic.raw)
		.add(Items.carrot)
		.add(Veggie.bean.raw)
		.add(Veggie.lentil.raw)
		.add(Veggie.pea.raw)
		.add(Veggie.stringbean.raw)
		.add(Veggie.eggplant.raw)
		.add(Veggie.zucchini.raw)
		;
		
		savory_salad
		.add(Veggie.artichoke.raw)
		.add(Veggie.beet.raw)
		.add(Veggie.bell_pepper.raw)
		.add(Veggie.broccoli.raw)
		.add(Items.carrot)
		.add(Veggie.cabbage.raw)
		.add(Veggie.onion.raw)
		.add(Veggie.tomato.raw)
		.add(Veggie.turnip.raw)
		.add(Veggie.pea.raw)
		.add(Veggie.stringbean.raw)
		.add(Veggie.cucumber.raw)
		.add(Veggie.eggplant.raw)
		.add(Fruit.avocado.raw)
		;
		
		dressing
		.add(Cuisine.vinegar)
		.add(Cuisine.olive_oil)
		.add(Cuisine.honey_mustard)
		.add(Cuisine.mayonnaise)
		.add(Cuisine.ketchup)
		;
		
		sweet_salad
		.add(Items.apple)
		.add(Veggie.blackberry.raw)
		.add(Veggie.blueberry.raw)
		.add(Veggie.pineapple.raw)
		.add(Veggie.raspberry.raw)
		.add(Veggie.strawberry.raw)
		.add(Veggie.grape.raw)
		.add(Fruit.banana.raw)
		.add(Fruit.cherry.raw)
		.add(Fruit.pear.raw)
		.add(Fruit.plum.raw)
		.add(Fruit.kiwi.raw)
		.add(Fruit.peach.raw)
		;
		
	}
	// TODO: look into compatibility with NEI?
	public final String name;
	private final ArrayList<Item> items;

	private Ingredient(final String name){
		this.name = name;
		items = new ArrayList<>();
	}

	private Ingredient add(final Item i){
		this.items.add(i);
		return this;
	}
	private Ingredient add(final ItemStack i){
		return this.add(i.getItem());
	}
	private Ingredient add(final Block b){
		return this.add(Item.getItemFromBlock(b));
	}
	public boolean matches(final Item i){
		return this.items.contains(i);
	}

}
