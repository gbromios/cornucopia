package com.gb.cornucopia.cookery.crafting;

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

public class SlotStoveOutput extends Slot {
    /** The player that is using the GUI where this slot resides. */
    private final EntityPlayer player;
    private final IInventory stove;
    private int stackSize;
	private DishRegistry dish_registry;

    public SlotStoveOutput(EntityPlayer player, IInventory stove, int slotIndex, int xPosition, int yPosition)
    {
        super(stove, slotIndex, xPosition, yPosition);
        this.stove = stove;
        this.player = player; // so we know where to spawn any potential XP Orbs
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */

}
