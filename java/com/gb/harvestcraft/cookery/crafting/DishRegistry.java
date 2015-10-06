package com.gb.harvestcraft.cookery.crafting;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class DishRegistry {
	private static final ArrayList<Dish> dishes = new ArrayList<>();
	
	public static void add(Dish dish){
		dishes.add(dish);
	}
	
	public static ItemStack findMatchingDish(InventoryCrafting cooking_input, World worldIn)
	    {
	        Iterator iterator = dishes.iterator();
	        Dish d;

	        do
	        {
	            if (!iterator.hasNext())
	            {
	                return null;
	            }

	            d = (Dish) iterator.next();
	        }
	        while (!d.matches(cooking_input, worldIn));

	        return d.getCraftingResult(cooking_input);
	}
	
    public static ItemStack[] getChangedInput(InventoryCrafting cooking_input, World worldIn)
    {
        Iterator iterator = dishes.iterator();

        while (iterator.hasNext())
        {
            Dish d = (Dish) iterator.next();

            if (d.matches(cooking_input, worldIn))
            {
                return d.getRemainingItems(cooking_input);
            }
        }

        // return whatever items were there? I'm 
        ItemStack[] aitemstack = new ItemStack[cooking_input.getSizeInventory()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            aitemstack[i] = cooking_input.getStackInSlot(i);
        }

        return aitemstack;
    }
	
}
