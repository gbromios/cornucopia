package com.gb.cornucopia.cookery.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMill extends Container{
	public ContainerMill(final InventoryPlayer playerInventory, final IInventory millInventory) {
		this.addSlotToContainer(new SlotMill(millInventory, 0, 62, 11, true));
		this.addSlotToContainer(new SlotMill(millInventory, 1, 80, 11, true));
		this.addSlotToContainer(new SlotMill(millInventory, 2, 98, 11, true));
		this.addSlotToContainer(new SlotMill(millInventory, 3, 62, 37, false)); 
		this.addSlotToContainer(new SlotMill(millInventory, 4, 80, 37, false));
		this.addSlotToContainer(new SlotMill(millInventory, 5, 98, 37, false));
		this.addSlotToContainer(new SlotMill(millInventory, 6, 62, 56, false)); 
		this.addSlotToContainer(new SlotMill(millInventory, 7, 80, 56, false));
		this.addSlotToContainer(new SlotMill(millInventory, 8, 98, 56, false));

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
			// input/output slots xfer to player inv
			if (index >= 0 && index < 9)
			{
				if (!this.mergeItemStack(stack, 9, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			// player can merge to the input
			// make sure it's allowed as input because container donesnt check
			else if (!this.getSlot(0).isItemValid(stack) || !this.mergeItemStack(stack, 0, 3, false)) {
				return null;
			}

			if (stack.stackSize == 0)
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
