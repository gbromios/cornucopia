package com.gb.cornucopia.cookery.crafting;

import com.gb.cornucopia.bees.crafting.SlotApiary;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerStove extends Container{
	public ContainerStove(InventoryPlayer playerInventory, IInventory stoveInventory) {
		// fuel
		this.addSlotToContainer(new Slot(stoveInventory, 0, 33, 20));
		// crafting grid
		this.addSlotToContainer(new Slot(stoveInventory, 1,  78, 21)); 
		this.addSlotToContainer(new Slot(stoveInventory, 2, 101, 21));
		this.addSlotToContainer(new Slot(stoveInventory, 3, 124, 21));
		this.addSlotToContainer(new Slot(stoveInventory, 4,  78, 43));
		this.addSlotToContainer(new Slot(stoveInventory, 5, 101, 43));
		this.addSlotToContainer(new Slot(stoveInventory, 6, 124, 43));
		this.addSlotToContainer(new Slot(stoveInventory, 7,  78, 65));
		this.addSlotToContainer(new Slot(stoveInventory, 8, 101, 56));
		this.addSlotToContainer(new Slot(stoveInventory, 9,  33, 46)); 
		
		// output
		this.addSlotToContainer(new SlotStoveOutput(playerInventory.player, stoveInventory, 10, 101, 54));

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
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
		// for now you'll have to transfer items the hard way
        return null;
    }

	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	

}
