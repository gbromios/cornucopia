package com.gb.cornucopia.cookery.stove;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotStoveFuel extends SlotItemHandler {

	private final IItemHandler itemHandler;
	private final int index;

	public SlotStoveFuel(final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
		this.itemHandler = itemHandler;
		this.index = index;
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack)
	{
		if (stack.isEmpty())
			return false;
/*		System.out.println("burn time of item is: " + TileEntityFurnace.getItemBurnTime(stack) );*/

		if (TileEntityFurnace.getItemBurnTime(stack) != 0) {
			return true;}

			return false;

/*		IItemHandler handler = this.getItemHandler();
		ItemStack remainder;
		if (handler instanceof IItemHandlerModifiable)
		{
			IItemHandlerModifiable handlerModifiable = (IItemHandlerModifiable) handler;
			ItemStack currentStack = handlerModifiable.getStackInSlot(index);

			handlerModifiable.setStackInSlot(index, ItemStack.EMPTY);

			remainder = handlerModifiable.insertItem(index, stack, true);

			handlerModifiable.setStackInSlot(index, currentStack);
		}
		else
		{
			remainder = handler.insertItem(index, stack, true);
		}
		return remainder.isEmpty() || remainder.getCount() < stack.getCount();*/

	}
}
