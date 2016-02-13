package com.gb.cornucopia.cuisine;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

/*
 * copied a lot from ShapelessOreRecipe here. it does what I want but not close enough to extend.
 * I don't really want to abuse the ore dictionary in this way although i totally could
 * 
 * */

public class Dish {

	private final ItemStack result;
	private final ArrayList<ItemStack> items;
	private final ArrayList<Ingredient> ingredients;
	public final int cook_time;

	public static final int DEFAULT_COOK_TIME = 160; // 4/5 vanilla time
	public Dish(final Block result, final Object... recipe){ this(new ItemStack(result), recipe); }
	public Dish(final Item  result, final Object... recipe){ this(new ItemStack(result), recipe); }

	// idk if i like this but I am a savage
	public Dish(final ItemStack result, final Object... recipe)
	{
		this.result = result.copy();
		this.items = new ArrayList<ItemStack>();
		this.ingredients = new ArrayList<Ingredient>();
		
		int cook_time_param = DEFAULT_COOK_TIME; 

		for (Object i : recipe) {
			if (i instanceof ItemStack) {
				items.add((ItemStack)i);
			}
			else if (i instanceof Item) {
				items.add(new ItemStack((Item)i));
			}
			else if (i instanceof Block) {
				items.add(new ItemStack((Block)i));
			}
			else if (i instanceof Ingredient){
				ingredients.add((Ingredient)i);
			}
			else if (i instanceof Integer) {
				cook_time_param = (int)i;
			}
			else
			{
				String message = "Invalid Dish Recipe: ";
				for (Object a :  recipe)
				{
					message += a + ", ";
				}
				message += this.result;
				throw new RuntimeException(message);
			}
		}
		// TODO: possibly ensure that no ambiguous recipes are being created?
		
		// only set the cook time to default after we determined whether one was supplied
		this.cook_time = cook_time_param;
	}

	public ItemStack getItem(){ return result.copy(); }
	
	public boolean matches(final IInventory crafting)
	{
		return this.matches(crafting, 0, crafting.getSizeInventory());
	}
	

	public boolean matches(final IInventory crafting, final int min, final int max){
		final ArrayList<ItemStack> items_required = new ArrayList<>(this.items);
		final ArrayList<Ingredient> ingredients_required = new ArrayList<>(this.ingredients);
		// bit chufty this one...
		final HashMap<Item, ArrayList<Ingredient>> counted_as = new HashMap<Item, ArrayList<Ingredient>>();
		
		for (int x = min; x <= max; x++)
		{
			final ItemStack stack = crafting.getStackInSlot(x);

			if (stack != null)
			{
				boolean satisfied_requirement = false;
				for (ItemStack req : items_required)
				{
					if (req.isItemEqual(stack))
					{
						satisfied_requirement = true;
						items_required.remove(req);
						break;
					}
				}
				// one input item can satisfy multiple ingredients!!! be careful hahahaha
				// but each can only satisfy the requirement for an specific ingredient once
				final ArrayList<Ingredient> ingredients_present = new ArrayList<>();
				final Item item = stack.getItem();
				for (Ingredient req : ingredients_required)
				{
					if (req.matches(item)) {
						if ( !counted_as.containsKey(item) )
						{
							satisfied_requirement = true;
							final ArrayList<Ingredient> al = new ArrayList<>();
							al.add(req);
							counted_as.put(item, al);
							ingredients_present.add(req);
						} else if ( !counted_as.get(item).contains(req) ) {
							satisfied_requirement = true;
							counted_as.get(item).add(req);
							ingredients_present.add(req);
						}
					}
				}
				if (!satisfied_requirement) { return false; }
				for (Ingredient present : ingredients_present) {
					ingredients_required.remove(present);
				}
			}
		}
		return items_required.isEmpty() && ingredients_required.isEmpty();
	}

	/**
	 * WTF
	 */
	public ArrayList<Object> getInput()
	{
		// if this does ever happen, i know how i can make it usable. i'm just gonna need 
		// a damn good reason to implement something better than "return new ArrayList<Object>();"
		throw new RuntimeException("just curious wtf asshole method could be calling this shit. WTF are interfaces even for??????");
	}

	public ItemStack[] getRemainingItems(final InventoryCrafting inv)
	{
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}


}
