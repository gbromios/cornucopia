package com.gb.cornucopia.bees.apiary;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerApiary extends Container {
	public ContainerApiary(InventoryPlayer playerInventory, final TileEntityApiary apiary) {
		IItemHandler apiaryInventory = apiary.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

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

		// the player inventory
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
	public ItemStack transferStackInSlot(final EntityPlayer player, final int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

			if (index < containerSlots) {
				if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(final EntityPlayer player)
	{
		return true;
	}

}
