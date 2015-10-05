package com.gb.harvestcraft.cookery.crafting;

import com.gb.harvestcraft.cookery.Cookery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class ContainerCookingTable extends ContainerWorkbench {
	private final World world; // these are the same as the super class but they're private so i can't override anything :I
	private final BlockPos pos;

	public ContainerCookingTable(InventoryPlayer playerInventory, World world, BlockPos pos) {
		super(playerInventory, world, pos);
	    this.world = world;
	    this.pos = pos;
	}
	
    public boolean canInteractWith(EntityPlayer playerIn)
    {
    	// vanilla method has block hard coded.
    	return 
    		this.world.getBlockState(this.pos).getBlock() == Cookery.table
    		&& playerIn.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D
    	;
        
    }
    
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
    	// TODO this is where the functionality will be changed from vanilla crafting table
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.world));
    }

	
}
