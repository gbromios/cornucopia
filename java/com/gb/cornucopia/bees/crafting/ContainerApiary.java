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
		public ContainerApiary(InventoryPlayer playerInventory, IInventory apiaryInventory) {
		// not sure if i am supposed to use the player or something?
			
			
		this.addSlotToContainer(new Slot(apiaryInventory, 0, 34, 21)); // queen
		this.addSlotToContainer(new Slot(apiaryInventory, 1, 34, 47)); // workers
		
		// seven honeycomb slots
		this.addSlotToContainer(new Slot(apiaryInventory, 2, 78,  21)); 
		this.addSlotToContainer(new Slot(apiaryInventory, 3, 101, 10));
		this.addSlotToContainer(new Slot(apiaryInventory, 4, 124, 21));
		this.addSlotToContainer(new Slot(apiaryInventory, 5, 78,  43));
		this.addSlotToContainer(new Slot(apiaryInventory, 6, 101, 32));
		this.addSlotToContainer(new Slot(apiaryInventory, 7, 124, 43));
		this.addSlotToContainer(new Slot(apiaryInventory, 8, 101, 54));
		
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
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
