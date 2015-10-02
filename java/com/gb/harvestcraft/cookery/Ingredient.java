package com.gb.harvestcraft.cookery;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Ingredient {
	// TODO: look into compatibility with NEI?
	public final String name;
	private ArrayList<ItemStack> items;
	
	public Ingredient(String name){
		this.name = name;
		this.items = new ArrayList<ItemStack>(); 
	}
	
	public Ingredient add(ItemStack i){
		this.items.add(i);
		return this;
	}
	public Ingredient add(Item i){
		return this.add(new ItemStack(i));
	}
	
	public boolean matches(ItemStack i){
		return this.items.contains(i);
	}
}
