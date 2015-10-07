package com.gb.cornucopia.cookery.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class SlotCookingOutput extends Slot {
    /** The craft matrix inventory linked to this result slot. */
    private final InventoryCrafting craftMatrix;
    /** The player that is using the GUI where this slot resides. */
    private final EntityPlayer player;
    /** The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset. */
    private final DishRegistry dishRegistry;

	public SlotCookingOutput(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inv, int slotIndex, int x, int y, DishRegistry dishRegistry) {
		super(inv, slotIndex, x, y);
		this.player = player;
		this.craftMatrix = craftingInventory;
		this.dishRegistry = dishRegistry;
	}

	// can't put anything in the output slot
	public boolean isItemValid(ItemStack stack) { return false; }
	
    public void onPickupFromSlot(EntityPlayer player, ItemStack stack)
    {
        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, craftMatrix);
        this.onCrafting(stack);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
        ItemStack[] aitemstack = this.dishRegistry.getChangedInput(this.craftMatrix, player.worldObj);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);
            ItemStack itemstack2 = aitemstack[i];

            if (itemstack1 != null)
            {
                this.craftMatrix.decrStackSize(i, 1);
            }

            if (itemstack2 != null)
            {
                if (this.craftMatrix.getStackInSlot(i) == null)
                {
                    this.craftMatrix.setInventorySlotContents(i, itemstack2);
                }
                else if (!this.player.inventory.addItemStackToInventory(itemstack2))
                {
                    this.player.dropPlayerItemWithRandomChoice(itemstack2, false);
                }
            }
        }
    }
}
