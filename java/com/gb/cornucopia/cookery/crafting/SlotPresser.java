package com.gb.cornucopia.cookery.crafting;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.cookery.block.TileEntityStove;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotPresser extends Slot {
    /** The player that is using the GUI where this slot resides. */

    public SlotPresser(IInventory inv, int slotIndex, int xPosition, int yPosition)
    {
        super(inv, slotIndex, xPosition, yPosition);
    }

    /**
     * nay
     */
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() == Bees.honeycomb;
    }
}
