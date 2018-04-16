package com.gb.cornucopia.cookery.presser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPresser extends Container{
	public ContainerPresser(final InventoryPlayer playerInventory, final IInventory presserInventory) {
		this.addSlotToContainer(new SlotPresser(presserInventory, 0, 80, 20, true));
		this.addSlotToContainer(new SlotPresser(presserInventory, 1, 80, 45, false)); 

		// the player
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}		
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int index)
	{
		Slot slot = (Slot)this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			final ItemStack stack = slot.getStack();
			final ItemStack ret_stack = stack.copy();

			if (index < 2 && index >= 0)
			{
				if (!this.mergeItemStack(stack, 2, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(stack, 0, 2, false))
			{
				return null;
			}

			if (stack.getCount() == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
			return ret_stack;
		}
		return null;
	}

	@Override
	public boolean canInteractWith(final EntityPlayer playerIn) {
		return true;
	}

}
