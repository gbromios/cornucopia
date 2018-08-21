package com.gb.cornucopia.cookery.cuttingboard;

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

}
