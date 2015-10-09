package com.gb.cornucopia.bees.crafting;

import com.gb.cornucopia.cookery.crafting.SlotCookingOutput;

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

public class ContainerApiary extends Container {
	private final World world;
	private final BlockPos pos;
	public final InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public final IInventory craftResult = new InventoryCraftResult();

	public ContainerApiary(InventoryPlayer playerventory, World world, BlockPos pos) {
		this.world = world;
		this.pos = pos;

		//this.addSlotToContainer(new SlotCookingOutput(playerventory.player, this.craftMatrix, this.craftResult, 0, 124, 35, dishRegistry));
		
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
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
