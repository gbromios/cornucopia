package com.gb.cornucopia.cookery.presser;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.fruit.Fruits;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPresser extends Slot {
	private final boolean is_input;

	public SlotPresser(final IInventory inv, final int slotIndex, final int xPosition, final int yPosition, boolean is_input)
	{
		super(inv, slotIndex, xPosition, yPosition);
		this.is_input = is_input;
	}

	public boolean isItemValid(final ItemStack stack)
	{
		return this.is_input && TileEntityPresser.canPress(stack.getItem());
	}
}
