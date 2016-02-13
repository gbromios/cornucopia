package com.gb.cornucopia.cookery.crafting;

import com.gb.cornucopia.cuisine.DishRegistry;
import com.gb.cornucopia.cuisine.Dishes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCuttingBoardOutput extends Slot {
    /** The craft matrix inventory linked to this result slot. */
    private final InventoryCrafting craftMatrix;
    /** The player that is using the GUI where this slot resides. */
    private final EntityPlayer player;
    /** The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset. */
    private final DishRegistry dishRegistry;

	public SlotCuttingBoardOutput(final EntityPlayer player, final InventoryCrafting crafting, final IInventory inv, final int slotIndex, final int x, final int y) {
		super(inv, slotIndex, x, y);
		this.player = player;
		this.craftMatrix = crafting;
		this.dishRegistry = Dishes.cutting_board;
	}

	// can't put anything in the output slot
	public boolean isItemValid(final ItemStack stack) { return false; }
	
    public void onPickupFromSlot(final EntityPlayer player, final ItemStack stack)
    {
        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, craftMatrix);
        this.onCrafting(stack);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
        final ItemStack[] input = this.dishRegistry.getChangedInput(this.craftMatrix, player.worldObj);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);

        for (int i = 0; i < input.length; ++i)
        {
        	// no fucking clue what the distinction is here, but im not fucking with frail-ass crafting code 
            final ItemStack slot_stack = this.craftMatrix.getStackInSlot(i);
            ItemStack input_stack = input[i];

            if (slot_stack != null)
            {
                this.craftMatrix.decrStackSize(i, 1);
            }

            if (input_stack != null)
            {
                if (this.craftMatrix.getStackInSlot(i) == null)
                {
                    this.craftMatrix.setInventorySlotContents(i, input_stack);
                }
                else if (!this.player.inventory.addItemStackToInventory(input_stack))
                {
                    this.player.dropPlayerItemWithRandomChoice(input_stack, false);
                }
            }
        }
    }
}
