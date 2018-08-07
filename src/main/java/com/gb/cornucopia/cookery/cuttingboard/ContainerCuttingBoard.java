package com.gb.cornucopia.cookery.cuttingboard;

import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cookery.SlotBowls;
import com.gb.cornucopia.cuisine.dish.Dish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.Sys;


public class ContainerCuttingBoard extends Container {

/*	private final World world; // these are the same as the super class but they're private so i can't override anything :I
	private final BlockPos pos;
	public final InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public final IInventory craftResult = new InventoryCraftResult();
	private IInventory bowl = new InventoryBasic("bowls", false, 1);
	private Dish currentRecipe = null;*/

	private TileEntityCuttingBoard board;
	private IItemHandler boardInventory;
	private int slot_index = 0;

	public ContainerCuttingBoard(InventoryPlayer player, TileEntityCuttingBoard board) {

		this.board = board;

		boardInventory = board.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

		// 0 = output
		this.addSlotToContainer(new SlotCuttingBoardOutput(boardInventory,slot_index++, 123, 35, false));
		// 1 = bowls
		//TODO Put bowl back once stove gui crap working
		/*this.addSlotToContainer(new SlotBowls(this.bowl, 0, 123, 55));*/

		// 2 - 10 crafting matrix
		int i;
		int j;

		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 3; ++j) {
				this.addSlotToContainer(new SlotCuttingBoardOutput(boardInventory, slot_index++ /*j + i * 3*/, 30 + j * 18, 17 + i * 18, true));
			}
		}

		// 11-37 player inventory backpack
		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		// 38-46 player inventory hotbar
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}

		/*this.onInputChanged(boardInventory);*/

	}

/*	public boolean hasBowl() {
		// skip the count check on this side, i dont think it matters?
		return this.bowl.getStackInSlot(0) != null && this.bowl.getStackInSlot(0).getItem() == Items.BOWL;
	}*/

/*	public boolean hasWater() {
		//Block a = this.world.getBlockState(this.pos.add(0, -1, 1)).getBlock();
		//Block b = this.world.getBlockState(this.pos.add(0, -1, -1)).getBlock();
		//Block c = this.world.getBlockState(this.pos.add(1, -1, 0)).getBlock();
		//Block d = this.world.getBlockState(this.pos.add(-1, -1, 0)).getBlock();
		//System.out.println(this.pos);

		return // im lazy
				this.world.getBlockState(this.pos.add(1, -1, 0)).getBlock() == Cookery.water_basin
						|| this.world.getBlockState(this.pos.add(-1, -1, 0)).getBlock() == Cookery.water_basin
						|| this.world.getBlockState(this.pos.add(0, -1, 1)).getBlock() == Cookery.water_basin
						|| this.world.getBlockState(this.pos.add(0, -1, -1)).getBlock() == Cookery.water_basin
				;
	}*/

	public boolean canInteractWith(EntityPlayer player) {

		return true;
/*
		// vanilla method has block hard coded.
		return
				!this.world.isAirBlock(this.pos) // more ideally, we'd just check that it's the actual block; this should be almost as good as far as "block is still there" is concerned
						&& player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D
				;
*/

	}

	//matrix will always just be this.craftMatrix afaict
/*	public void onCraftMatrixChanged(IInventory matrix) {
*//*		//if (this.world.isRemote) {return;}
		//this.craftResult.setInventorySlotContents(0, this.dishRegistry.findMatchingDish(this.craftMatrix).getItem());
		final Dish d = Dish.cutting_board.findMatchingDish(this.craftMatrix, this.hasBowl(), this.hasWater());
		this.craftResult.setInventorySlotContents(0, d == null ? null : d.getItem());
		this.currentRecipe = d;*//*
	}*/

/*	public void onInputChanged(IItemHandler inventory){
		final Dish dish_output = Dish.cutting_board.findMatchingDish(inventory, false, board.hasWater());

		System.out.println("Make this dish: " + dish_output + " what is in slot 0? " + boardInventory.getStackInSlot(0));

		if (dish_output != null && boardInventory.getStackInSlot(0).isEmpty()){
			System.out.println("There is space for an output.");
			ItemStack output = dish_output.getItem();
			boardInventory.insertItem(0, output, false);
		}
	}*/


/*	boolean requiresBowl() {
		return this.currentRecipe != null && this.currentRecipe.requiresBowl();

	}*/


/*	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		if (!this.world.isRemote) {
			for (int i = 0; i < 9; i++) {
				ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
				if (itemstack != null) {
					player.dropItem(itemstack, false);
				}
			}
			ItemStack itemstack = this.bowl.getStackInSlot(0);
			if (itemstack != null) {
				player.dropItem(itemstack, false);
			}
		}
	}*/

	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

			if (index < containerSlots) {
				if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

/*	public boolean canMergeSlot(ItemStack stack, Slot slot) {
		return slot.inventory != this.craftResult && super.canMergeSlot(stack, slot);
	}*/
	// endregion

}
