package com.gb.cornucopia.food.cuisine;

// item for now... potion later??
public class ItemLibation extends ItemCuisine{
	//public final String name;
	public ItemLibation(String name) {
		super(name, 4, 0.4F);
		this.setAlwaysEdible();
	}

}
