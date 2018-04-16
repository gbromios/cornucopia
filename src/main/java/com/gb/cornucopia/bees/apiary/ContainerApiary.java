package com.gb.cornucopia.bees.apiary;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerApiary extends Container {
	public ContainerApiary(final InventoryPlayer playerInventory, final IInventory apiaryInventory, final World world, final BlockPos pos) {		
		this.addSlotToContainer(new SlotApiary(apiaryInventory, 0, 33, 20, 0)); // queen
		this.addSlotToContainer(new SlotApiary(apiaryInventory, 1, 33, 46, 1)); // workers

		// seven honeycomb slots
		this.addSlotToContainer(new SlotApiary(apiaryInventory, 2,  78, 21, 2)); 
		this.addSlotToContainer(new SlotApiary(apiaryInventory, 3, 101, 10, 2));
		this.addSlotToContainer(new SlotApiary(apiaryInventory, 4, 124, 21, 2));
		this.addSlotToContainer(new SlotApiary(apiaryInventory, 5,  78, 43, 2));
		this.addSlotToContainer(new SlotApiary(apiaryInventory, 6, 101, 32, 2));
		this.addSlotToContainer(new SlotApiary(apiaryInventory, 7, 124, 43, 2));
		this.addSlotToContainer(new SlotApiary(apiaryInventory, 8, 101, 54, 2));

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
		ItemStack itemstack = null;
		final Slot slot = (Slot)this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 9 && index >= 0)
			{
				if (!this.mergeItemStack(itemstack1, 9, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, 9, false))
			{
				return null;
			}

			if (itemstack1.getCount() == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(final EntityPlayer player)
	{
		return true;
	}

}
