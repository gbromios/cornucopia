package com.gb.cornucopia.cookery.mill;

import com.gb.cornucopia.veggie.Veggie;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotMill extends SlotItemHandler {
	private final boolean is_input;
	private final IItemHandler itemHandler;
	private final int index;

	public SlotMill(final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition, boolean is_input)
	{
		super(itemHandler, index, xPosition, yPosition);
		this.itemHandler = itemHandler;
		this.index = index;
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
				|| i == Veggie.corn.raw
				|| i == Veggie.spice.raw
				|| i == Veggie.herb.raw
				;

	}
}
