package com.gb.cornucopia.bees.apiary;

import com.gb.cornucopia.bees.Bees;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotApiary extends SlotItemHandler{

	private final int iType; //type of bee that belongs in slot
	private final IItemHandler itemHandler;
	private final int index;

	public SlotApiary(final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition, final int iType) {
		super(itemHandler, index, xPosition, yPosition);
		this.itemHandler = itemHandler;
		this.index = index;
		this.iType = iType;
	}

	public boolean isItemValid(ItemStack stack) {
		switch(this.iType){
		case 0: 
			return stack.getItem() == Bees.queen;
		case 1: 
			return stack.getItem() == Bees.bee;			
		default: 
			return false;
		}

	}


}
