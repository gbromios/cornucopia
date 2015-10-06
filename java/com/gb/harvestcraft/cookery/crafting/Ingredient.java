package com.gb.harvestcraft.cookery.crafting;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Ingredient {
	// TODO: look into compatibility with NEI?
	public final String name;
	private ArrayList<Item> items;
	
	public Ingredient(String name){
		this.name = name;
		this.items = new ArrayList<>(); 
	}
	
	public Ingredient add(Item i){
		this.items.add(i);
		return this;
	}
	public Ingredient add(ItemStack i){
		return this.add(i.getItem());
	}
	
	public boolean matches(Item i){
		return this.items.contains(i);
	}
}
