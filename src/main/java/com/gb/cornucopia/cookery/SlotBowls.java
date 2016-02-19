package com.gb.cornucopia.cookery;

import com.gb.cornucopia.cookery.stove.TileEntityStove;

import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBowls extends Slot {

	public boolean isItemValid(final ItemStack stack) { return stack != null && stack.getItem() == Items.bowl; }
	private Container c;
	public SlotBowls(IInventory inventoryIn, Container c, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.c = c;
	}

	@Override
	public void onSlotChanged() {
		if (this.inventory instanceof TileEntityStove) {
			((TileEntityStove)this.inventory).markInputChanged();
		} else {
			super.onSlotChanged();
			this.c.onCraftMatrixChanged(this.inventory);
		}
	}
}
