package com.gb.cornucopia.cookery.cuttingboard;

import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cookery.SlotBowls;
import com.gb.cornucopia.cuisine.dish.Dish;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ContainerCuttingBoard extends Container {
	private final World world; // these are the same as the super class but they're private so i can't override anything :I
	private final BlockPos pos;
	public final InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public final IInventory craftResult = new InventoryCraftResult();
	private IInventory bowl = new InventoryBasic("bowls", false, 1);
	private Dish currentRecipe = null;

	public ContainerCuttingBoard(final InventoryPlayer player, final World world, final BlockPos pos) {
		this.world = world;
		this.pos = pos;
		// 0 = output
		this.addSlotToContainer(new SlotCuttingBoardOutput(this.craftMatrix, this.craftResult, this.bowl, this, 0, 123, 35));
		// 1 = bowls
		this.addSlotToContainer(new SlotBowls(this.bowl, this, 0, 123, 55));
		
		// 2 - 10 crafting matrix
		int i;
		int j;

		for (i = 0; i < 3; ++i)
		{
			for (j = 0; j < 3; ++j)
			{
				this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
			}
		}
		
		// 11-37 player inventory backpack
		for (i = 0; i < 3; ++i)
		{
			for (j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		// 38-46 player inventory hotbar
		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
		
		this.onCraftMatrixChanged(this.craftMatrix);

	}
	
	public boolean hasBowl(){
		// skip the count check on this side, i dont think it matters?
		return this.bowl.getStackInSlot(0) != null && this.bowl.getStackInSlot(0).getItem() == Items.BOWL;
	}
	public boolean hasWater(){
		//Block a = this.world.getBlockState(this.pos.add(0, -1, 1)).getBlock();
		//Block b = this.world.getBlockState(this.pos.add(0, -1, -1)).getBlock();
		//Block c = this.world.getBlockState(this.pos.add(1, -1, 0)).getBlock();
		//Block d = this.world.getBlockState(this.pos.add(-1, -1, 0)).getBlock();
		//System.out.println(this.pos);
		
		return // im lazy
				this.world.getBlockState(this.pos.add(1, -1, 0)).getBlock() == Cookery.water_basin
				|| this.world.getBlockState(this.pos.add(-1, -1, 0)).getBlock() == Cookery.water_basin
				|| this.world.getBlockState(this.pos.add(0, -1, 1)).getBlock() == Cookery.water_basin
				|| this.world.getBlockState(this.pos.add(0, -1, -1)).getBlock() == Cookery.water_basin
				;
	}

	public boolean canInteractWith(final EntityPlayer player)
	{
		// vanilla method has block hard coded.
		return 
				!this.world.isAirBlock(this.pos) // more ideally, we'd just check that it's the actual block; this should be almost as good as far as "block is still there" is concerned
				&& player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D
				;

	}

	//matrix will always just be this.craftMatrix afaict
	public void onCraftMatrixChanged(IInventory matrix)
	{
		//if (this.world.isRemote) {return;}
		//this.craftResult.setInventorySlotContents(0, this.dishRegistry.findMatchingDish(this.craftMatrix).getItem());
		final Dish d = Dish.cutting_board.findMatchingDish(this.craftMatrix, this.hasBowl(), this.hasWater());
		this.craftResult.setInventorySlotContents(0, d == null ? null : d.getItem());
		this.currentRecipe = d;
	}
	
	public boolean requiresBowl() {
		return this.currentRecipe != null && this.currentRecipe.requiresBowl();
		
	}

	
	public void onContainerClosed(final EntityPlayer player)
	{
		super.onContainerClosed(player);

		if (!this.world.isRemote)
		{
			for (int i = 0; i < 9; i++)
			{
				ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
				if (itemstack != null)
				{
					player.dropItem(itemstack, false);
				}
			}
			ItemStack itemstack = this.bowl.getStackInSlot(0);
			if (itemstack != null)
			{
				player.dropItem(itemstack, false);
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

			// output
			if (index < 11)
			{
				if (!this.mergeItemStack(stack, 11, 46, true))
				{
					return null;
				}
				// this might be superfluous?
				slot.onSlotChange(stack, ret_stack);
			}
			else if (index >= 11 && index <= 37)
			{
				if (!this.mergeItemStack(stack, 37, 46, false))
				{
					return null;
				}
			}
			else if (index > 37 && index <= 46)
			{
				if (!this.mergeItemStack(stack, 10, 37, false))
				{
					return null;
				}
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
