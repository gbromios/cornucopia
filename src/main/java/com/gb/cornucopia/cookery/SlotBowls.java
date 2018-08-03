package com.gb.cornucopia.cookery;

import com.gb.cornucopia.cookery.stove.TileEntityStove;

import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotBowls extends SlotItemHandler {

	private final IItemHandler itemHandler;
	private final int index;

	public boolean isItemValid(final ItemStack stack) { return stack != null && stack.getItem() == Items.BOWL; }
	private Container c;

	public SlotBowls(final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.itemHandler = itemHandler;
		this.index = index;
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
