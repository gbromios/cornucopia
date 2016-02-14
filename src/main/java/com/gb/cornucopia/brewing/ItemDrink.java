package com.gb.cornucopia.brewing;

import net.minecraft.item.Item;

// item for now... potion later??
public class ItemDrink extends Item{
	public final String name;
	public ItemDrink(String name) {
		this.name = "brew_" + name + "_drink";
	}

}
