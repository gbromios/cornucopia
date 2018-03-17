package com.gb.cornucopia.cookery.mill;

import com.gb.cornucopia.farming.veggie.Veggie;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotMill extends Slot {
	private final boolean is_input;

	public SlotMill(final IInventory inv, final int slotIndex, final int xPosition, final int yPosition, boolean is_input)
	{
		super(inv, slotIndex, xPosition, yPosition);
		this.is_input = is_input;
	}

	public boolean isItemValid(final ItemStack stack)
	{
		if (!this.is_input || stack == null) {
			return false;
		}

		final Item i = stack.getItem();
		return i == Items.WHEAT
				|| i == Veggie.barley.raw
				|| i == Veggie.peanut.raw
				|| i == Veggie.spice.raw
				|| i == Veggie.herb.raw
				;

	}
}
