package com.gb.cornucopia.cookery.crafting;

import com.gb.cornucopia.bees.crafting.SlotApiary;
import com.gb.cornucopia.cookery.block.TileEntityStove;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerStove extends Container {
	public final IInventory stoveInventory;
	private int cook_time;
	private int burn_time;
	private int butn_time_max;
	private int initial_cook_time;
	
    public ContainerStove(InventoryPlayer playerInventory, IInventory stove) {
    	this.stoveInventory = stove;
        // fuel
        this.addSlotToContainer(new Slot(stoveInventory, 0, 70, 46));
        // crafting grid
        
        for (int i = 0; i<3; i++) {
        	for(int j = 0; j<2; j++) {
        		this.addSlotToContainer(new Slot(stoveInventory, 1 + (i + j * 3),  52 + i * 18, 10 + j * 18));
        	} 	
        }

        // output
        this.addSlotToContainer(new SlotStoveOutput(playerInventory.player, stoveInventory, 7, 130, 19));

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

    /**
     * Add the given Listener to the list of Listeners. Method name is for legacy.
     */
    public void addCraftingToCrafters(ICrafting listener)
    {
        super.addCraftingToCrafters(listener);
        listener.func_175173_a(this, this.stoveInventory);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
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

        this.cook_time = this.stoveInventory.getField(2);
        this.burn_time = this.stoveInventory.getField(0);
        this.butn_time_max = this.stoveInventory.getField(1);
        this.initial_cook_time = this.stoveInventory.getField(3);
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.stoveInventory.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.stoveInventory.isUseableByPlayer(playerIn);
    }
    
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 8 )
			{
				if (!this.mergeItemStack(itemstack1, 8, 43, true)){ 
					
					return null;
				}
					slot.onSlotChange(itemstack1, itemstack);
			}
			else {
				if (!this.mergeItemStack(itemstack1, 0, 7, false)){ 
					
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
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
    

}