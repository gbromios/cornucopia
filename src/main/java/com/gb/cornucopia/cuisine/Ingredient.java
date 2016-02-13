package com.gb.cornucopia.cuisine;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Ingredient {
	// TODO: look into compatibility with NEI?
	public final String name;
	private final ArrayList<Item> items;

	public Ingredient(final String name){
		this.name = name;
		items = new ArrayList<>();
	}

	public Ingredient add(final Item i){
		this.items.add(i);
		return this;
	}
	public Ingredient add(final ItemStack i){
		return this.add(i.getItem());
	}

	public boolean matches(final Item i){
		return this.items.contains(i);
	}
}
