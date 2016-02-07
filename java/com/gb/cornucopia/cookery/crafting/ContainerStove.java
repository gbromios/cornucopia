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
        this.addSlotToContainer(new Slot(stoveInventory, 0, 70, 46));
        // crafting grid
        
        for (int i = 0; i<3; i++) {
        	for(int j = 0; j<2; j++) {
        		this.addSlotToContainer(new Slot(stoveInventory, 1 + (i + j * 3),  52 + i * 18, 10 + j * 18));
        	} 	
        }
        
        /*this.addSlotToContainer(new Slot(stoveInventory, 1,  40, 21)); 
        this.addSlotToContainer(new Slot(stoveInventory, 2, 65, 21));
        this.addSlotToContainer(new Slot(stoveInventory, 3, 90, 21));
        this.addSlotToContainer(new Slot(stoveInventory, 4,  40, 43));
        this.addSlotToContainer(new Slot(stoveInventory, 5, 65, 43));
        this.addSlotToContainer(new Slot(stoveInventory, 6, 90, 43));
        this.addSlotToContainer(new Slot(stoveInventory, 7,  40, 65));
        this.addSlotToContainer(new Slot(stoveInventory, 8, 65, 65));
        this.addSlotToContainer(new Slot(stoveInventory, 9,  90, 65));*/ 
        
        // output
        this.addSlotToContainer(new SlotStoveOutput(playerInventory.player, stoveInventory, 10, 130, 19));

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

            if (index < 8)
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
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
    
    

}