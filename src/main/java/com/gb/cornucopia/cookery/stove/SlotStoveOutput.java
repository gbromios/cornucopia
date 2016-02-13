

package com.gb.cornucopia.cookery.stove;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotStoveOutput extends Slot {
	
	public SlotStoveOutput(final IInventory stove, final int slotIndex, final int xPosition, final int yPosition)
	{
		super(stove, slotIndex, xPosition, yPosition);
	}

	public boolean isItemValid(final ItemStack stack)
	{
		return false;
	}
}
