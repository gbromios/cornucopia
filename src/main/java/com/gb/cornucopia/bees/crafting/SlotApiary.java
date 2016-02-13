package com.gb.cornucopia.bees.crafting;

import com.gb.cornucopia.bees.Bees;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotApiary extends Slot{
	private final int iType; // to lazy for an enum or even checking block instances, if this needs to be more complicated it's a trivial change
	public SlotApiary(final IInventory inventoryIn, final int index, final int xPosition, final int yPosition, final int iType) {
		super(inventoryIn, index, xPosition, yPosition);
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
