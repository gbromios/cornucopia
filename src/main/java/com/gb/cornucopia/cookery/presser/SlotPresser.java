package com.gb.cornucopia.cookery.presser;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotPresser extends SlotItemHandler {
	private final boolean is_input;
	private final IItemHandler itemHandler;
	private final int index;

	public SlotPresser(final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition, boolean is_input)
	{
		super(itemHandler, index, xPosition, yPosition);
		this.itemHandler = itemHandler;
		this.index = index;
		this.is_input = is_input;
	}

	public boolean isItemValid(final ItemStack stack)
	{
		return this.is_input && TileEntityPresser.pressable(stack.getItem());
	}
}
