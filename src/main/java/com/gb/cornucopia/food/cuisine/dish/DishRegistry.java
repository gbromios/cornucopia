package com.gb.cornucopia.food.cuisine.dish;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.inventory.IInventory;

// maps a list of Dishes (i.e. recipes) to a specific cookign vessel/crafting station
public class DishRegistry {
	private final ArrayList<Dish> dishes;

	public DishRegistry(){
		this.dishes = new ArrayList<>();
	}

	public DishRegistry add(final Dish dish){
		dishes.add(dish);
		return this;
	}

	// currently assumes bowl inventory is size=0 but could easily be changed to e.g. look in player's inventory
	public Dish findMatchingDish(final IInventory cooking_input, final boolean has_bowl, final boolean has_water){
		return this.findMatchingDish(cooking_input, 0, cooking_input.getSizeInventory(), has_bowl, has_water);
	}
	// both indices inclusive		
	public Dish findMatchingDish(final IInventory cooking_input, final int min, final int max, final boolean has_bowl, final boolean has_water){ 
		final Iterator<Dish> iterator = dishes.iterator();
		while (iterator.hasNext()) {
			final Dish d = (Dish) iterator.next();
			if (d.matches(cooking_input, min, max, has_bowl, has_water)) {
				return d;
			}
			
		}
		return null;
	}
}