package com.gb.cornucopia.cookery.crafting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class DishRegistry {
	private final static HashMap<Integer, DishRegistry> block_map = new HashMap<>();
	private final ArrayList<Dish> dishes;
	
	public static DishRegistry get(int i){
		return block_map.get(i);
	}
	
	public static DishRegistry get(Block b){
		return block_map.get(Block.getIdFromBlock(b));
	}
	
	public DishRegistry(Block b){
		dishes = new ArrayList<>();
		block_map.put(Block.getIdFromBlock(b), this);
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
