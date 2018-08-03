package com.gb.cornucopia.cookery.stove;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotStoveInput extends SlotItemHandler {
	/** The player that is using the GUI where this slot resides. */
	//private final EntityPlayer player;
	/*private final TileEntityStove stove;*/
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
		if (this.getStack() != null) {
		//System.out.format("SLOT CHANGE!! %d : %s\n", this.slotNumber, this.getStack());
		}
		super.onSlotChanged();
		/*this.stove.markInputChanged();*/
	}
}
