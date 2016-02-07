package com.gb.cornucopia.cookery.crafting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.gb.cornucopia.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class DishRegistry {
	private static final HashMap<Integer, DishRegistry> dishRegistryRegistry = new HashMap<>(); 
	private final ArrayList<Dish> dishes;

	// this could never go wrong lol
	public static DishRegistry byID(Integer id){
		return dishRegistryRegistry.get(id);
	}

	public DishRegistry(Integer id){
		dishes = new ArrayList<>();
		assert (!dishRegistryRegistry.containsKey(id)); // one-to-one mapping of ID to DishRegistry. this could get delicate >__>, hence the assert 
		dishRegistryRegistry.put(id, this);
		// BTW: zero will be cutting board, 1-7 are stovetop blocks. don't plan on implementing TOO many more...
		// but if I do, just implement IMakesDishes and account for the offset in guiID 
	}

	public DishRegistry add(Dish dish){
		dishes.add(dish);
		return this;
	}

	public ItemStack findMatchingDish(IInventory cooking_input, World world){
		return this.findMatchingDish(cooking_input, world, 0, cooking_input.getSizeInventory());
	}
	public ItemStack findMatchingDish(IInventory cooking_input, World world, int min, int max){ // both inclusive
		
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
		while (!d.matches(cooking_input, world, min, max));

		return d.getCraftingResult(null); // why pass in param? i forgot
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
