package com.gb.cornucopia.cookery.mill;

import com.gb.cornucopia.cuisine.Cuisine;
import com.gb.cornucopia.veggie.Veggie;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityMill extends TileEntity implements ITickable {
	public ItemStackHandler inventory = new ItemStackHandler(9);

	@Override
	public void update() {
	}

	private boolean inputIsEmpty() {
		return inventory.getStackInSlot(0).isEmpty() && inventory.getStackInSlot(1).isEmpty() && inventory.getStackInSlot(2).isEmpty();
	}

	private int hasInputItem(Item i) {
		return ((!inventory.getStackInSlot(0).isEmpty() && inventory.getStackInSlot(0).getItem() == i) ? 1 : 0)
				+ ((!inventory.getStackInSlot(1).isEmpty() && inventory.getStackInSlot(1).getItem() == i) ? 1 : 0)
				+ ((!inventory.getStackInSlot(2).isEmpty() && inventory.getStackInSlot(2).getItem() == i) ? 1 : 0);
	}

	private int findOutputSlot(ItemStack drop) {
		//TODO Check order of slots, as had to change the double up so all slots would be used -- drop items in a specific order
		for (int i : new int[]{7, 6, 8, 5, 3, 4}) {
			final ItemStack output = inventory.getStackInSlot(i);
			if (output.isEmpty() || (drop.isItemEqual(output) && (drop.getCount() + output.getCount() <= output.getMaxStackSize()))) {
				return i;
			}
		}
		return -1;
	}

	//TODO maybe condense these methods based on recipe type ie Flour & Cornflour could be condensed and Spices & Herbs
	// must have grain in all three slots to craft
	private boolean _canMakeFlour() {
		return this.hasInputItem(Items.WHEAT) + this.hasInputItem(Veggie.barley.raw) == 3;
	}

	private boolean _makeFlour() {
		// 3 grain => 2 flour
		final ItemStack drop = new ItemStack(Cuisine.flour, 2);
		final int freeSlot = this.findOutputSlot(drop);
		if (freeSlot == -1) {
			return false;
		}

			for (int i = 0; i < 3; i++) {
				inventory.getStackInSlot(i).shrink(1);
			}

		final ItemStack output = inventory.getStackInSlot(freeSlot);
		if (output.isEmpty()) {
			inventory.setStackInSlot(freeSlot, drop);
		} else {
			output.grow(2);
		}
		return true;
	}

	// must have raw peanuts in all three slots to craft
	private boolean _canMakePeanutButter() {
		return this.hasInputItem(Veggie.peanut.raw) == 3;
	}

	// 3 raw peanuts => 1 peanut butter
	private boolean _makePeanutButter() {
		final ItemStack drop = new ItemStack(Cuisine.peanut_butter);
		final int freeSlot = this.findOutputSlot(drop);
		if (freeSlot == -1) {
			return false;
		}

		for (int i = 0; i < 3; i++) {
			inventory.getStackInSlot(i).shrink(1);
		}

		final ItemStack output = inventory.getStackInSlot(freeSlot);
		if (output.isEmpty()) {
			inventory.setStackInSlot(freeSlot, drop);
		} else {
			output.grow(1);
		}
		return true;
	}

	// must have raw corn in all three slots to craft
	private boolean _canMakeCornmeal() {
		return this.hasInputItem(Veggie.corn.raw) == 3;
	}

	// 3 raw corn => 2 corn_flour/cornmeal
	private boolean _makeCornmeal() {
		final ItemStack drop = new ItemStack(Cuisine.corn_flour, 2);
		final int freeSlot = this.findOutputSlot(drop);
		if (freeSlot == -1) {
			return false;
		}

		for (int i = 0; i < 3; i++) {
			inventory.getStackInSlot(i).shrink(1);
		}

		final ItemStack output = inventory.getStackInSlot(freeSlot);
		if (output.isEmpty()) {
			inventory.setStackInSlot(freeSlot, drop);
		} else {
			output.grow(2);
		}
		return true;
	}

	private boolean _canMakeHerbs() {
		return this.hasInputItem(Veggie.herb.raw) > 0;
	}

	private boolean _makeHerbs() {
		final ItemStack drop = new ItemStack(Cuisine.herb_drops.getRandom());
		final int freeSlot = this.findOutputSlot(drop);
		if (freeSlot == -1) {
			return false;
		}

		for (int i = 0; i < 2; i++) {
			final ItemStack input = inventory.getStackInSlot(i);
			if (input.isEmpty() || input.getItem() != Veggie.herb.raw) {
				continue;
			}

			input.shrink(1);

			final ItemStack output = inventory.getStackInSlot(freeSlot);
			if (output.isEmpty()) {
				inventory.setStackInSlot(freeSlot, drop);
			} else {
				output.grow(1);
			}
			return true;
		}
		return false;
	}

	private boolean _canMakeSpices() {
		return this.hasInputItem(Veggie.spice.raw) > 0;
	}

	private boolean _makeSpices() {
		final ItemStack drop = new ItemStack(Cuisine.spice_drops.getRandom());
		final int freeSlot = this.findOutputSlot(drop);
		if (freeSlot == -1) {
			return false;
		}

		for (int i = 0; i < 2; i++) {
			final ItemStack input = inventory.getStackInSlot(i);
			if (input.isEmpty() || input.getItem() != Veggie.spice.raw) {
				continue;
			}

			input.shrink(1);

			final ItemStack output = inventory.getStackInSlot(freeSlot);
			if (output.isEmpty()) {
				inventory.setStackInSlot(freeSlot, drop);
			} else {
				output.grow(1);
			}
			return true;
		}
		return false;
	}


	public boolean can_mill() {
		// returns true if is possible to mill something

		if (this.inputIsEmpty()) {
			return false;
		}
		if (this._canMakeFlour()) {
			return this._makeFlour();
		}
		if (this._canMakePeanutButter()) {
			return this._makePeanutButter();
		}
		if (this._canMakeCornmeal()) {
			return this._makeCornmeal();
		}
		if (this._canMakeHerbs()) {
			return this._makeHerbs();
		}
		if (this._canMakeSpices()) {
			return this._makeSpices();
		}

		return false;
	}

	@Override
	public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
	}


	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}


	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return (oldState.getBlock() != newState.getBlock());

	}

}
