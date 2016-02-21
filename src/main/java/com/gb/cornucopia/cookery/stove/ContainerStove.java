package com.gb.cornucopia.cookery.stove;

import com.gb.cornucopia.cookery.SlotBowls;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerStove extends Container {
	public final TileEntityStove stoveInventory;
	private int cook_time;
	private int burn_time;
	private int butn_time_max;
	private int initial_cook_time;

	public ContainerStove(final InventoryPlayer playerInventory, final TileEntityStove stove) {
		this.stoveInventory = stove;
		// fuel = 0
		this.addSlotToContainer(new SlotStoveFuel(stoveInventory, 0, 80, 55));
		
		// crafting grod = 123456
		for (int i = 0; i<3; i++) {
			for(int j = 0; j<2; j++) {
				this.addSlotToContainer(new SlotStoveInput(stoveInventory, 1 + (i + j * 3),  62 + i * 18, 19 + j * 18));
			} 	
		}

		// output = 7
		this.addSlotToContainer(new SlotStoveOutput(stoveInventory, 7, 136, 24));
		// bowls = 8
		this.addSlotToContainer(new SlotBowls(stove, this, 8, 24, 37));

		// fake slot ~ 45
		this.addSlotToContainer(new SlotStoveVessel(stove, 44, 24, 19));
		

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

	public void addCraftingToCrafters(final ICrafting listener)
	{
		super.addCraftingToCrafters(listener);
		listener.func_175173_a(this, this.stoveInventory);
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting)this.crafters.get(i);

			if (this.cook_time != (int)this.stoveInventory.getField(2))
			{
				icrafting.sendProgressBarUpdate(this, 2, this.stoveInventory.getField(2));
				this.stoveInventory.markDirty();

			}

			if (this.burn_time != (int)this.stoveInventory.getField(0))
			{
				icrafting.sendProgressBarUpdate(this, 0, this.stoveInventory.getField(0));
				this.stoveInventory.markDirty();

			}

			if (this.butn_time_max != (int)this.stoveInventory.getField(1))
			{
				icrafting.sendProgressBarUpdate(this, 1, this.stoveInventory.getField(1));
				this.stoveInventory.markDirty();

			}

			if (this.initial_cook_time != (int)this.stoveInventory.getField(3))
			{
				icrafting.sendProgressBarUpdate(this, 3, this.stoveInventory.getField(3));
				this.stoveInventory.markDirty();

			}
		}
		this.burn_time = this.stoveInventory.getField(0);
		this.butn_time_max = this.stoveInventory.getField(1);
		this.cook_time = this.stoveInventory.getField(2);
		this.initial_cook_time = this.stoveInventory.getField(3);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(final int id, final int data)
	{
		this.stoveInventory.setField(id, data);
	}

	public boolean canInteractWith(final EntityPlayer playerIn)
	{
		return this.stoveInventory.isUseableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer player, final int index)
	{
		Slot slot = (Slot)this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack())
		{
			final ItemStack stack = slot.getStack();
			// try to transfer from table -> player
			if (index < 9 )
			{
				if (!this.mergeItemStack(stack, 9, 44, true)){ 
					return null;
				}
			}
			// player -> table
			else {
				if (!this.mergeItemStack(stack, 0, 8, false)){ 
					return null;
				}
			}

			// if there was a successful merge
			// all items were merged, clean up slot's stack
			if (stack.stackSize == 0)
			{
				slot.putStack(null);
				return null;
			}
			//slot.onPickupFromSlot(player, stack);
			return stack;
		}
		return null;
	}

	@Override
	protected boolean mergeItemStack(final ItemStack in_stack, int min_i, int max_i, final boolean notch_sucks)
	{

		// try to merge the stacked item with any of the slots i..max_i (inclusive MAYBE LOL)
		if ( notch_sucks ) {
			max_i++;
		}
		boolean did_merge = false;

		if (in_stack.isStackable())
		{

			for (int i = min_i; i < max_i; i++) {
				final Slot slot = this.getSlot(i);

				// don't bother if the input stack isn't valid for this slot
				if (! slot.isItemValid(in_stack)) {
					continue;
				}

				// emtpy slot, transfer the item and be done with it
				if (!slot.getHasStack()) {
					// no item, of course theres' room
					slot.putStack(in_stack.copy());
					in_stack.stackSize = 0;
					return true;
				}

				// if there is an item: we can merge the two?
				final ItemStack slot_stack = slot.getStack();

			
				// use the same test as vanilla here. i prefer 
				// ''' if (!slot_stack.isItemEqual(in_stack) || slot_stack.getMetadata() != in_stack.getMetadata()) '''
				// but i just want the behavior to be same
				
				// if items are not equal OR are of differing subtypes or have different tags
				if (!slot_stack.isItemEqual(in_stack)
						|| ( slot_stack.getHasSubtypes() && slot_stack.getMetadata() != in_stack.getMetadata() )
						|| !ItemStack.areItemStackTagsEqual(in_stack, slot_stack) 
						) { continue; }

				final int capacity = slot_stack.getMaxStackSize() - slot_stack.stackSize;
				// can't merge any items on to a full stack
				if ( capacity < 1) {
					continue;

				} else if (capacity < in_stack.stackSize){ // not enough room transfer what items we can and keep looking 
					in_stack.stackSize -= capacity;
					slot_stack.stackSize += capacity;
					this.stoveInventory.markDirty();
					did_merge = true;
					continue;

				} else { // there is enough room for the whole input stack
					slot_stack.stackSize += in_stack.stackSize;
					in_stack.stackSize = 0;
					this.stoveInventory.markDirty();
					return true;
				}
			}
			return did_merge;

		} else { // non stackable: can only transfer to a free space:

			for (int i = min_i; i <= max_i; i++) {
				final Slot slot = this.getSlot(i);
				// don't bother if the input stack isn't valid for this slot
				if (! slot.isItemValid(in_stack)) { continue; }
				// emtpy slot, transfer the item and be done with it
        		if (! slot.getHasStack()) {
        			slot.putStack(in_stack.copy());
        			in_stack.stackSize = 0;
        			return true;
        		}
			}
			return false;
		}
	}
}