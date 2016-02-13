package com.gb.cornucopia.cuisine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DishRegistry {
	private final ArrayList<Dish> dishes;

	public DishRegistry(){
		this.dishes = new ArrayList<>();
		// BTW: zero will be cutting board, 1-7 are stovetop blocks. don't plan on implementing TOO many more...
		// but if I do, just implement IMakesDishes and account for the offset in guiID 
	}

	public DishRegistry add(final Dish dish){
		dishes.add(dish);
		return this;
	}

	public Dish findMatchingDish(final IInventory cooking_input){
		return this.findMatchingDish(cooking_input, 0, cooking_input.getSizeInventory());
	}
	
	public Dish findMatchingDish(final IInventory cooking_input, final int min, final int max){ // both inclusive		
		final Iterator<Dish> iterator = dishes.iterator();
		while (iterator.hasNext()) {
			final Dish d = (Dish) iterator.next();
			if (d.matches(cooking_input, min, max)) {
				return d;
			}
			
		}
		return null;
	}

	public ItemStack[] getChangedInput(final InventoryCrafting cooking_input, final World world){
		final Iterator<Dish> iterator = dishes.iterator();
		while (iterator.hasNext())
		{
			final Dish d = (Dish) iterator.next();
			if (d.matches(cooking_input))
			{
				return d.getRemainingItems(cooking_input);
			}
		}

		// return whatever items were there? I'm 
		final ItemStack[] stacks = new ItemStack[cooking_input.getSizeInventory()];
		for (int i = 0; i < stacks.length; ++i)
		{
			stacks[i] = cooking_input.getStackInSlot(i);
		}
		return stacks;
	}
	
}