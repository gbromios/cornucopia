package com.gb.cornucopia.cookery.cuttingboard;

import com.gb.cornucopia.cuisine.dish.Dish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotCuttingBoardOutput extends SlotItemHandler {
    private final boolean is_input;
    private final IItemHandler itemHandler;
    private final int index;
	public SlotCuttingBoardOutput(final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition, boolean is_input) {
        super(itemHandler, index, xPosition, yPosition);
        this.itemHandler = itemHandler;
        this.index = index;
        this.is_input = is_input;
	}

	// can't put anything in the output slot
    public boolean isItemValid(final ItemStack stack)
    {
        if (!this.is_input || stack == null) {
            return false;
        }
        else return true;
    }
	
    public void onPickupFromSlot(final EntityPlayer player, final ItemStack stack)
    {
    	//not sure what the implications of this are? dont care much about attaching to other crafting systems tbh
/*        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, craftMatrix);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
        final ItemStack[] input = Dish.cutting_board.findMatchingDish(this.craftMatrix, 1, 6, false, false);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);*/
    	
    	// TODO fix once bowls fixed
/*    	if (this.c instanceof ContainerCuttingBoard && ((ContainerCuttingBoard)this.c).requiresBowl()) {
    		this.bowl.decrStackSize(0, 1);
    	}*/

    	// assume that everything in this inventory A) has no containerItem (lol) and B) is used in the recipe. will fix A later.
       if (stack == this.itemHandler.getStackInSlot(0)){
        for (int i = 1; i < this.itemHandler.getSlots(); ++i)
        {
            if (this.itemHandler.getStackInSlot(i) != null)
            {
                this.itemHandler.getStackInSlot(i).shrink(1);
				//decrStackSize should DTRT and null check and all that
            }

        }
    }}
}
