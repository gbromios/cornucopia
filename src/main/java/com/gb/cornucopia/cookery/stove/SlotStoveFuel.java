package com.gb.cornucopia.cookery.stove;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotStoveFuel extends Slot {
	public SlotStoveFuel(final TileEntityStove stove, final int slotIndex, final int xPosition, final int yPosition)
	{
		super((IInventory)stove, slotIndex, xPosition, yPosition);
	}

	public boolean isItemValid(final ItemStack stack)
	{
		return TileEntityStove.getFuelValue(stack) > 0;
	}
}
