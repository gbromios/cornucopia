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
    private final TileEntityStove stove;
    private int stackSize;

    public SlotStoveOutput(EntityPlayer player, IInventory stove, int slotIndex, int xPosition, int yPosition)
    {
        super(stove, slotIndex, xPosition, yPosition);
        this.stove = (TileEntityStove)stove;
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
    public ItemStack decrStackSize(int amount)
    {
        if (this.getHasStack())
        {
            this.stackSize += Math.min(amount, this.getStack().stackSize);
        }

        return super.decrStackSize(amount);
    }

    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
    {
        this.onCrafting(stack);
        super.onPickupFromSlot(playerIn, stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void onCrafting(ItemStack stack, int amount)
    {
        this.stackSize += amount;
        this.onCrafting(stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void onCrafting(ItemStack stack)
    {
        stack.onCrafting(this.player.worldObj, this.player, this.stackSize);

        if (!this.player.worldObj.isRemote)
        {
            int i = this.stackSize;
            float f = FurnaceRecipes.instance().getSmeltingExperience(stack);
            int j;

            if (f == 0.0F)
            {
                i = 0;
            }
            else if (f < 1.0F)
            {
                j = MathHelper.floor_float((float)i * f);

                if (j < MathHelper.ceiling_float_int((float)i * f) && Math.random() < (double)((float)i * f - (float)j))
                {
                    ++j;
                }

                i = j;
            }

            while (i > 0)
            {
                j = EntityXPOrb.getXPSplit(i);
                i -= j;
                this.player.worldObj.spawnEntityInWorld(new EntityXPOrb(this.player.worldObj, this.player.posX, this.player.posY + 0.5D, this.player.posZ + 0.5D, j));
            }
        }

        this.stackSize = 0;

        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerSmeltedEvent(player, stack);

        if (stack.getItem() == Items.iron_ingot)
        {
            this.player.triggerAchievement(AchievementList.acquireIron);
        }

        if (stack.getItem() == Items.cooked_fish)
        {
            this.player.triggerAchievement(AchievementList.cookFish);
        }
    }
}
