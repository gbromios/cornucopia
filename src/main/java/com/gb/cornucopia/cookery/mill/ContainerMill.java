package com.gb.cornucopia.cookery.mill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerMill extends Container{
	public ContainerMill(final InventoryPlayer playerInventory, TileEntityMill mill) {

		IItemHandler millInventory = mill.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

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
	public boolean canInteractWith(final EntityPlayer playerIn) {
		return true;
	}

}
