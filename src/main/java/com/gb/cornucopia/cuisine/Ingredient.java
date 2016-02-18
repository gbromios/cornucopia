package com.gb.cornucopia.cuisine;

import java.util.ArrayList;

import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggie;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Ingredient {

	public static final Ingredient mirepoix_part = new Ingredient("mirepoix_part");
	public static final Ingredient sweet_berry = new Ingredient("sweet_berry");
	public static final Ingredient citrus = new Ingredient("citrus");

	// set up ingredients so we can use them in dishes
	// this can be done late as ingredient members will not
	// be referenced until crafters are looking stuff up
	public static void init() {
		mirepoix_part
		.add(Veggie.onion.raw)
		.add(Veggie.celery.raw)
		.add(Veggie.bell_pepper.raw)
		.add(Items.carrot)
		;		
		
		sweet_berry
		.add(Veggie.blackberry.raw)
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

	public boolean matches(final Item i){
		return this.items.contains(i);
	}

}
