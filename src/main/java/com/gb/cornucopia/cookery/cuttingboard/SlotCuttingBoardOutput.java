package com.gb.cornucopia.cookery.cuttingboard;

import com.gb.cornucopia.cuisine.dish.Dish;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCuttingBoardOutput extends Slot {
    private final InventoryCrafting craftMatrix;
    private final IInventory bowl;
	private final Container c;
	public SlotCuttingBoardOutput(final InventoryCrafting crafting, final IInventory inv, IInventory bowl, Container c, final int slotIndex, final int x, final int y) {
		super(inv, slotIndex, x, y);
		this.craftMatrix = crafting;
		this.bowl = bowl;
		this.c = c;
	}

	// can't put anything in the output slot
	public boolean isItemValid(final ItemStack stack) { return false; }
	
    public void onPickupFromSlot(final EntityPlayer player, final ItemStack stack)
    {
    	//not sure what the implications of this are? dont care much about attaching to other crafting systems tbh
        //net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, craftMatrix);
        //net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
        //final ItemStack[] input = Dish.cutting_board.getChangedInput(this.craftMatrix, player.worldObj);
        //net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);
    	
    	// o forgive me
    	if (this.c instanceof ContainerCuttingBoard && ((ContainerCuttingBoard)this.c).requiresBowl()) {
    		this.bowl.decrStackSize(0, 1);
    	}

    	// assume that everything in this inventory A) has no containerItem (lol) and B) is used in the recipe. will fix A later. 
        for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i)
        {
            if (this.craftMatrix.getStackInSlot(i) != null)
            {
                this.craftMatrix.decrStackSize(i, 1);
				//decrStackSize should DTRT and null check and all that
            }

        }
    }
}
