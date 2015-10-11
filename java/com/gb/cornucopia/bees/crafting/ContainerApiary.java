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
	private final World world; // mojang
	private final BlockPos pos;
	
	public ContainerApiary(InventoryPlayer playerInventory, IInventory apiaryInventory, World world, BlockPos pos) {
		this.world = world;
		this.pos = pos;
		
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
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 9 && index >= 0)
            {
                if (!this.mergeItemStack(itemstack1, 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 9, false))
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
        }

        return itemstack;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		// pls
		return 
				!this.world.isAirBlock(this.pos)
				&& player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D
				;

	}

}