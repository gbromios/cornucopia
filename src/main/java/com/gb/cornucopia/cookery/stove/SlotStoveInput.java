package com.gb.cornucopia.cookery.stove;

import net.minecraft.inventory.Slot;

public class SlotStoveInput extends Slot {
	/** The player that is using the GUI where this slot resides. */
	//private final EntityPlayer player;
	private final TileEntityStove stove;
	public SlotStoveInput(final TileEntityStove stove, final int slotIndex, final int xPosition, final int yPosition)
	{
		super(stove, slotIndex, xPosition, yPosition);
		this.stove = stove;
	}
	
	@Override
	public void onSlotChanged(){//(final ItemStack a, final ItemStack b){
		if (this.getStack() != null) {
			System.out.format("SLOT CHANGE!! %d : %s\n", this.slotNumber, this.getStack());
		}
		super.onSlotChanged();
		this.stove.markInputChanged();
	}
}
