package com.gb.cornucopia.cookery.stove;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerStove extends Container {
	private TileEntityStove stove;

	public ContainerStove(final InventoryPlayer playerInventory, final TileEntityStove stove) {

		IItemHandler stoveInventory = stove.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

		this.stove = stove;
		// fuel = 0
		this.addSlotToContainer(new SlotStoveFuel(stoveInventory, 0, 80, 55));

		// crafting grid = 123456
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				this.addSlotToContainer(new SlotStoveInput(stoveInventory, 1 + (i + j * 3), 62 + i * 18, 19 + j * 18));
			}
		}

		// output = 7
		this.addSlotToContainer(new SlotStoveOutput(stoveInventory, 7, 136, 24));

		/*// bowls = 8
		this.addSlotToContainer(new SlotBowls(stoveInventory, 8, 24, 37));*/

		/*// fake slot ~ 9
		this.addSlotToContainer(new SlotStoveVessel(stoveInventory, 9, 24, 19));*/


		// the player
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}

	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(final int id, final int data) {
		stove.setField(id, data);
	}

	public boolean canInteractWith(final EntityPlayer playerIn) {
		return stove.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer player, final int index) {

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
	protected boolean mergeItemStack(final ItemStack in_stack, int min_i, int max_i, final boolean notch_sucks) {

		// try to merge the stacked item with any of the slots i..max_i (inclusive MAYBE LOL)
		if (notch_sucks) {
			max_i++;
		}
		boolean did_merge = false;

		if (in_stack.isStackable()) {

			for (int i = min_i; i < max_i; i++) {
				final Slot slot = this.getSlot(i);

				// don't bother if the input stack isn't valid for this slot
				if (!slot.isItemValid(in_stack)) {
					continue;
				}

				// emtpy slot, transfer the item and be done with it
				if (!slot.getHasStack()) {
					// no item, of course theres' room
					slot.putStack(in_stack.copy());
					in_stack.setCount(0);
					return true;
				}

				// if there is an item: we can merge the two?
				final ItemStack slot_stack = slot.getStack();


				// use the same test as vanilla here. i prefer 
				// ''' if (!slot_stack.isItemEqual(in_stack) || slot_stack.getMetadata() != in_stack.getMetadata()) '''
				// but i just want the behavior to be same

				// if items are not equal OR are of differing subtypes or have different tags
				if (!slot_stack.isItemEqual(in_stack)
						|| (slot_stack.getHasSubtypes() && slot_stack.getMetadata() != in_stack.getMetadata())
						|| !ItemStack.areItemStackTagsEqual(in_stack, slot_stack)
						) {
					continue;
				}

				final int capacity = slot_stack.getMaxStackSize() - slot_stack.getCount();
				// can't merge any items on to a full stack
				if (capacity < 1) {
					continue;

				} else if (capacity < in_stack.getCount()) { // not enough room transfer what items we can and keep looking
					in_stack.shrink(capacity);
					slot_stack.grow(capacity);
					stove.markDirty();
					did_merge = true;
					continue;

				} else { // there is enough room for the whole input stack
					slot_stack.grow(in_stack.getCount());
					in_stack.setCount(0);
					stove.markDirty();
					return true;
				}
			}
			return did_merge;

		} else { // non stackable: can only transfer to a free space:

			for (int i = min_i; i <= max_i; i++) {
				final Slot slot = this.getSlot(i);
				// don't bother if the input stack isn't valid for this slot
				if (!slot.isItemValid(in_stack)) {
					continue;
				}
				// emtpy slot, transfer the item and be done with it
				if (!slot.getHasStack()) {
					slot.putStack(in_stack.copy());
					in_stack.setCount(0);
					return true;
				}
			}
			return false;
		}
	}
}