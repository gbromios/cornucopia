package com.gb.cornucopia.cookery.crafting;

import com.gb.cornucopia.cookery.Cookery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class ContainerCookingTable extends Container {
	private final World world; // these are the same as the super class but they're private so i can't override anything :I
	private final BlockPos pos;
	private final DishRegistry dishRegistry;
	public final InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public final IInventory craftResult = new InventoryCraftResult();

	public ContainerCookingTable(InventoryPlayer playerventory, World world, BlockPos pos, DishRegistry dishRegistry) {
		this.world = world;
		this.pos = pos;
		this.dishRegistry = dishRegistry;

		this.addSlotToContainer(new SlotCuttingBoardOutput(playerventory.player, this.craftMatrix, this.craftResult, 0, 124, 35, dishRegistry));
		
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
				this.addSlotToContainer(new Slot(playerventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(playerventory, i, 8 + i * 18, 142));
		}
		// endregion
		
		this.onCraftMatrixChanged(this.craftMatrix);

	}

	public boolean canInteractWith(EntityPlayer player)
	{
		// vanilla method has block hard coded.
		return 
				!this.world.isAirBlock(this.pos) // more ideally, we'd just check that it's the actual block; this should be almost as good as far as "block is still there" is concerned
				&& player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D
				;

	}

	//region can't inherit from ContainerWorkbench directly, but these are nice to have 
	
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		// TODO this is where the functionality will be changed from vanilla crafting table
		//this.craftResult.setInventorySlotContents(0, this.dishRegistry.findMatchingDish(this.craftMatrix, this.world));
	}

	
	public void onContainerClosed(EntityPlayer player)
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

	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 0)
			{
				if (!this.mergeItemStack(itemstack1, 10, 46, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index >= 10 && index < 37)
			{
				if (!this.mergeItemStack(itemstack1, 37, 46, false))
				{
					return null;
				}
			}
			else if (index >= 37 && index < 46)
			{
				if (!this.mergeItemStack(itemstack1, 10, 37, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 10, 46, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	public boolean canMergeSlot(ItemStack p_94530_1_, Slot p_94530_2_)
	{
		return p_94530_2_.inventory != this.craftResult && super.canMergeSlot(p_94530_1_, p_94530_2_);
	}
	// endregion

}
