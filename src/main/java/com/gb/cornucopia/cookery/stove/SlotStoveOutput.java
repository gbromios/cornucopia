

package com.gb.cornucopia.cookery.stove;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotStoveOutput extends SlotItemHandler {

	private final IItemHandler itemHandler;
	private final int index;
	
	public SlotStoveOutput(final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
		this.itemHandler = itemHandler;
		this.index = index;
	}

	public boolean isItemValid(final ItemStack stack)
	{
		return false;
	}
}
