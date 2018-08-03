package com.gb.cornucopia.cuisine.dish;
import java.util.ArrayList;
import java.util.Iterator;

import com.gb.cornucopia.cookery.stove.TileEntityStove;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

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
	public Dish findMatchingDish(final IItemHandler cooking_input, final boolean has_bowl, final boolean has_water){
		return this.findMatchingDish(cooking_input, 0, cooking_input.getSlots(), has_bowl, has_water);
	}
	// both indices inclusive		
	public Dish findMatchingDish(final IItemHandler cooking_input, final int min, final int max, final boolean has_bowl, final boolean has_water){
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