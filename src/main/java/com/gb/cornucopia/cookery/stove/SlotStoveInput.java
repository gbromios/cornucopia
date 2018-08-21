package com.gb.cornucopia.cookery.stove;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotStoveInput extends SlotItemHandler {

	private final IItemHandler itemHandler;
	private final int index;

	public SlotStoveInput(final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
		this.itemHandler = itemHandler;
		this.index = index;
		/*this.stove = stove;*/
	}
	
	@Override
	public void onSlotChanged(){//(final ItemStack a, final ItemStack b){
		super.onSlotChanged();
		/*this.stove.markInputChanged();*/
	}
}
