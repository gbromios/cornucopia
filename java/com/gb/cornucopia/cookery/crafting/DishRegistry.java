package com.gb.cornucopia.cookery.crafting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.gb.cornucopia.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class DishRegistry {
	private final ArrayList<Dish> dishes;
	
	public DishRegistry(Block b){
		dishes = new ArrayList<>();
		GuiHandler.register(b, this);
		
	}
	
	public void add(Dish dish){
		dishes.add(dish);
	}
	
	public ItemStack findMatchingDish(InventoryCrafting cooking_input, World world)
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
	        while (!d.matches(cooking_input, world));

	        return d.getCraftingResult(cooking_input);
	}
	
    public ItemStack[] getChangedInput(InventoryCrafting cooking_input, World world)
    {
        Iterator iterator = dishes.iterator();

        while (iterator.hasNext())
        {
            Dish d = (Dish) iterator.next();

            if (d.matches(cooking_input, world))
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
