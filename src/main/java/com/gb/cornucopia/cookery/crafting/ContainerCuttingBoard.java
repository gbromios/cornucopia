package com.gb.cornucopia.cookery.crafting;

import com.gb.cornucopia.cuisine.Dish;
import com.gb.cornucopia.cuisine.DishRegistry;
import com.gb.cornucopia.cuisine.Dishes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class ContainerCuttingBoard extends Container {
	private final World world; // these are the same as the super class but they're private so i can't override anything :I
	private final BlockPos pos;
	public final InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public final IInventory craftResult = new InventoryCraftResult();
	private final DishRegistry dishRegistry;

	public ContainerCuttingBoard(final InventoryPlayer player, final World world, final BlockPos pos) {
		this.world = world;
		this.pos = pos;
		this.dishRegistry = Dishes.cutting_board;
		this.addSlotToContainer(new SlotCuttingBoardOutput(player.player, this.craftMatrix, this.craftResult, 0, 124, 35));
		
		//region// placing all the slots. 
		int i;
		int j;

		for (i = 0; i < 3; ++i)
		{
			for (j = 0; j < 3; ++j)
			{
				this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
			}
		}

		for (i = 0; i < 3; ++i)
		{
			for (j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
		// endregion
		
		this.onCraftMatrixChanged(this.craftMatrix);

	}

	public boolean canInteractWith(final EntityPlayer player)
	{
		// vanilla method has block hard coded.
		return 
				!this.world.isAirBlock(this.pos) // more ideally, we'd just check that it's the actual block; this should be almost as good as far as "block is still there" is concerned
				&& player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D
				;

	}

	//region can't inherit from ContainerWorkbench directly, but these are nice to have 
	
	public void onCraftMatrixChanged(final IInventory inventoryIn)
	{
		//this.craftResult.setInventorySlotContents(0, this.dishRegistry.findMatchingDish(this.craftMatrix).getItem());
		final Dish d = Dishes.cutting_board.findMatchingDish(this.craftMatrix);
		this.craftResult.setInventorySlotContents(0, d == null ? null : d.getItem());
	}

	
	public void onContainerClosed(final EntityPlayer player)
	{
		super.onContainerClosed(player);

		if (!this.world.isRemote)
		{
			for (int i = 0; i < 9; ++i)
			{
				ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

				if (itemstack != null)
				{
					player.dropPlayerItemWithRandomChoice(itemstack, false);
				}
			}
		}
	}

	public ItemStack transferStackInSlot(final EntityPlayer player, final int index)
	{
		Slot slot = (Slot)this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			final ItemStack stack = slot.getStack();
			final ItemStack ret_stack = stack.copy();

			if (index == 0)
			{
				if (!this.mergeItemStack(stack, 10, 46, true))
				{
					return null;
				}

				slot.onSlotChange(stack, ret_stack);
			}
			else if (index >= 10 && index < 37)
			{
				if (!this.mergeItemStack(stack, 37, 46, false))
				{
					return null;
				}
			}
			else if (index >= 37 && index < 46)
			{
				if (!this.mergeItemStack(stack, 10, 37, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(stack, 10, 46, false))
			{
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

			if (stack.stackSize == ret_stack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(player, stack);
			return ret_stack;
		}

		return null;
	}

	public boolean canMergeSlot(ItemStack stack, Slot slot)
	{
		return slot.inventory != this.craftResult && super.canMergeSlot(stack, slot);
	}
	// endregion

}
