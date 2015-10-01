package com.gb.harvestcraft.cookery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

import com.gb.harvestcraft.cookery.item.ItemCookWare;
import com.gb.harvestcraft.cookery.Ingredient;

/*
 * copied a lot from ShapelessOreRecipe here. it does what I want but not close enough to extend.
 * I don't really want to abuse the ore dictionary in this way although i totally could
 * 
 * */

public class RecipeDish implements IRecipe {
	
	private final ItemStack result;
	private final ItemCookWare ItemCookWare;
	private final ArrayList<ItemStack> items;
	private final ArrayList<Ingredient> ingredients;

    public RecipeDish(Block result, Object... recipe){ this(new ItemStack(result), recipe); }
    public RecipeDish(Item  result, Object... recipe){ this(new ItemStack(result), recipe); }

    public RecipeDish(ItemStack result, Object... recipe)
    {
        this.result = result.copy();
        this.items = new ArrayList<ItemStack>();
        this.ingredients = new ArrayList<Ingredient>();
        
        int i;
        // optional first argument: ItemCookWare
        if (recipe[0] instanceof ItemCookWare){
        	this.ItemCookWare = (ItemCookWare)recipe[0];
        	i = 1;
        } else {
        	this.ItemCookWare = null;
        	i = 0;
        }
        
        while (i < recipe.length) {
        	// itemstack/item/block: don't imagine these will be used much, but here they are if they do. maybe even get crazy and support String => OreDict??
        	if (recipe[i] instanceof ItemStack) {
        		items.add((ItemStack)recipe[i++]);
        	}
        	else if (recipe[i] instanceof Item) {
        		items.add(new ItemStack((Item)recipe[i++]));
        	}
        	else if (recipe[i] instanceof Block) {
        		items.add(new ItemStack((Block)recipe[i++]));
        	}
        	// the ingredients: Ingredient, [count=1, [mustBeUnique=true]]
        	else if (recipe[i] instanceof Ingredient){
        		ingredients.add((Ingredient)recipe[i++]);
        	}
            else
            {
                String message = "Invalid shapeless ore recipe: ";
                for (Object a :  recipe)
                {
                    message += a + ", ";
                }
                message += this.result;
                throw new RuntimeException(message);
            }
        }
    	// TODO: possibly ensure that no ambiguous recipes are being created?
}

    @Override
    public int getRecipeSize(){ return 9; } // wonder what this gets used for. Possibly determines if a table is required?

    @Override
    public ItemStack getRecipeOutput(){ return result; }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting i){ return result.copy(); }

    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(InventoryCrafting crafting, World world)
    {
        ArrayList<ItemStack> items_required = new ArrayList<ItemStack>(this.items);
        ArrayList<Ingredient> ingredients_required = new ArrayList<Ingredient>(this.ingredients);
       
        for (int x = 0; x < crafting.getSizeInventory(); x++)
        {
            ItemStack slot = crafting.getStackInSlot(x);

            if (slot != null)
            {
                boolean satisfied_requirement = false;
                for (ItemStack req : items_required)
                {
                    if (req.isItemEqual(slot))
                    {
                    	satisfied_requirement = true;
                        items_required.remove(req);
                        break;
                    }
                }
                // one input item can satisfy multiple ingredients!!! be careful hahahaha
                // but can only satisfy the requirement for an specific ingredient once!
                ArrayList<Ingredient> counted_as = new ArrayList<Ingredient>();
                for (Ingredient req : ingredients_required)
                {
                    if (!counted_as.contains(req) && req.matches((slot)))
                    {
                    	satisfied_requirement = true;
                    	counted_as.add(req);
                        ingredients_required.remove(req);
                    }
                }
                
                if (!satisfied_requirement) { return false; }
            }
        }

        return items_required.isEmpty() && ingredients_required.isEmpty();
    }

    /**
     * WTF
     */
    public ArrayList<Object> getInput()
    {
    	throw new RuntimeException("just curious wtf asshole method could be calling this shit. WTF are interfaces even for??????");
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv)
    {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }


}
