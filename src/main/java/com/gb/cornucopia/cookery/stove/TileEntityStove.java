package com.gb.cornucopia.cookery.stove;

import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cookery.Vessel;
import com.gb.cornucopia.cuisine.dish.Dish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityStove extends TileEntity implements ITickable {
	public ItemStackHandler inventory = new ItemStackHandler(8); // 0 = fuel, 1-6 input, 7 output, 8 bowls, 9 vessel slot

	private int burn_time = 0; // time left burning current fuel: ticks DOWN every update
	private int initial_burn_time = 1; // max burn time of the item currently used for fuel

	private int cook_time = 0; // how long the current recipe has been cooking. ticks UP every update
	private int cook_time_goal = 1; // vanilla furnace cooks things in 200.

	private Dish whats_cooking = null; // storing this field lets us avoid calculating recipes unless there's a chance it's changed (i.e. changed input i.e. inventory slots )
	private boolean input_changed = false; // set this to true when fucking with the input slots, check in every update and


	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		//return !isVanilla || (oldState.getBlock() != newSate.getBlock()); << this makes me want to fucking puke. for shame.
		return (oldState.getBlock() != newState.getBlock());

	}

	@Override
	public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
		//System.out.println("got data pkt: " + pkt.toString());
		this.readFromNBT(pkt.getNbtCompound());
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
        this.burn_time = compound.getInteger("BurnTime");
        this.cook_time = compound.getInteger("CookTime");
        this.cook_time_goal = compound.getInteger("CookTimeGoal");
		return super.writeToNBT(compound);

	}


	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        compound.setInteger("BurnTime", (short)this.burn_time);
        compound.setInteger("CookTime", (short)this.cook_time);
        compound.setInteger("CookTimeGoal", (short)this.cook_time_goal);
		super.readFromNBT(compound);
	}


	public boolean isBurning() {
		return this.burn_time > 0;
	}

	public Vessel getVessel() {
		return BlockStove.getVessel(this.world, this.pos);
	}

	// different, more specific than markDirty();
	public void markInputChanged() {
		//System.out.format("~~input changed~~!\n");
		this.input_changed = true;
	}

	private void _consumeIngredients() {
		for (int i = 1; i <= 6; i++) {
			inventory.getStackInSlot(i).shrink(1);
		}
		if (this.whats_cooking.requiresBowl()) {
			// i hope i can get away with this >__>
			inventory.getStackInSlot(8).shrink(1);
		}
	}

	private Dish _whatsCooking() {
		// ok what is getting cooked:

        return Dish.grill.findMatchingDish(inventory, false, true);

        //TODO put this logic back at some point
		/*return this.getVessel().getDishes().findMatchingDish((IInventory) this, 1, 6, this.hasBowl(), this.hasWater());*/
	}

	public boolean hasBowl() {
		return !inventory.getStackInSlot(8).isEmpty() && inventory.getStackInSlot(8).getCount() > 0 && inventory.getStackInSlot(8).getItem() == Items.BOWL;
	}

	public boolean hasWater() {
		return this.hasWorld() && (
				this.world.getBlockState(this.pos.add(1, 0, 0)).getBlock() == Cookery.water_basin
						|| this.world.getBlockState(this.pos.add(-1, 0, 0)).getBlock() == Cookery.water_basin
						|| this.world.getBlockState(this.pos.add(0, 0, 1)).getBlock() == Cookery.water_basin
						|| this.world.getBlockState(this.pos.add(0, 0, -1)).getBlock() == Cookery.water_basin
		);
	}

	// force the stove block below us to light up (or turn off)
	private void _didBurningChange(final boolean was_burning) {
		if (was_burning != this.isBurning()) {
			final IBlockState stove_state = this.world.getBlockState(pos);
			if (stove_state.getBlock() == Cookery.stove) {
				this.world.setBlockState(this.pos, stove_state.withProperty(BlockStove.ON, this.isBurning()));
			}
			// if the fire has just gone out this tick without being re-lit, the cooking is over :(
			if (was_burning) {
				this.cook_time = 0;
			}
		}
	}

	private boolean _debug_update() {
		if (
				(this.isBurning() && (this.burn_time % 50 < 2))
						|| (this.cook_time > 0 && this.cook_time % 50 < 2)
				) {
			//System.out.format(" = = = =\n");
			//System.out.format("BURNTIME: %d\n", this.burn_time);	
			//System.out.format("COOKTIME: %d\n", this.cook_time);
			return true;
		}
		return false;
	}

	private void _debug_update_end(boolean marked) {
		if (this.isBurning() && (this.burn_time + 1) % 50 < 3) {
			//System.out.format("BURNTIME: %d\n", this.burn_time);	
		}
		if (this.cook_time > 0 && (this.cook_time + 1) % 50 < 3) {
			//System.out.format("COOKTIME: %d\n", this.cook_time);
		}
	}

	@Override
	public void update() {
		if (!this.world.isRemote) {
			final boolean debug = this._debug_update();

			// before we tick down, so we can track whether the ON state should change this tick
			final boolean was_burning = this.isBurning();
			if (this.isBurning()) {
				this.burn_time--;
			}

			// stove cooks for longer than a normal furnace
			int fuel_value = 8 * TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0));

			// after the burn counter has had a chance decrement, then we can deal with a non-burning oven.
			if (!this.isBurning() && fuel_value > 0) {
				//System.out.format(" START FIRE: %d -> %s \n", this.burn_time, this.getFuelValue(inventory.getStackInSlot(0)));
				this.burn_time = fuel_value;
				this.initial_burn_time = this.burn_time;
				inventory.getStackInSlot(0).shrink(1);
				this.markDirty();
			}

			// figure out the recipe output??
			if (this.input_changed) {
				//System.out.format(" INPUT CHANGED: %s -> %s \n", this.whats_cooking, this._whatsCooking());
				this.whats_cooking = this._whatsCooking();
				this.input_changed = false; // reset the flag
				this.cook_time = 0; // changing the input resets the timer also
				this.cook_time_goal = this.whats_cooking != null ? this.whats_cooking.cook_time : 1;
			}

			// check if visual update for stove is required
			this._didBurningChange(was_burning);

			// ugly cooking logic: sorry for the obtuse multi line conditional future me
			if (this.isBurning()) {
				//System.out.format("burn...");
				// if there is viable input:
				if (this.whats_cooking != null) {
					//System.out.format(" cooking -- %s -- %d/%d \n", this.whats_cooking, this.cook_time, this.cook_time_goal);
					// increment the cooking timer and check if the cooking is done
					if (this.cook_time++ >= this.cook_time_goal) {
						//System.out.format(" done?? %s \n", this.whats_cooking);
						// if items are not equal OR are of differing subtypes or have different tags
						/*if (!slot_stack.isItemEqual(in_stack)
								|| ( slot_stack.getHasSubtypes() && slot_stack.getMetadata() != in_stack.getMetadata() )
								|| ! 
								) { continue; }*/

						// cooking time is done
						final ItemStack result = this.whats_cooking.getItem();
						final ItemStack output = inventory.getStackInSlot(7);

						if (output.isEmpty()) {
							// take one thing out of each slot in the crafting input
							this._consumeIngredients();
							this.markInputChanged();

							// if there's no output stack, put one there.
							inventory.setStackInSlot(7, this.whats_cooking.getItem());
							this.cook_time = 0; //reset the cooking timer. theoretically, a changed input grid should take care of it but whatever?
						}

						// existing output is a bit tricker:
						else if ( // stupid way of saying "can these stacks be merged"
								output.isStackable()
										&& output.isItemEqual(result)
										&& ItemStack.areItemStackTagsEqual(output, result) // wuuuut
										&& output.getCount() + result.getCount() <= inventory.getStackInSlot(7).getMaxStackSize()
								) {
							this._consumeIngredients();
							output.grow(result.getCount());
							this.markInputChanged();
							this.cook_time = 0;
						}

						// overflow?? do nothing for now but it might be funny to drop overflowing food on the ground huehue
						else {
						}
					}
				}
			}
			this._debug_update_end(debug); // wat changed??
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */


	// i think this is obsolete~?
	//public DishRegistry getDishes(){
	//return DishRegistry.byID(((Vessel)this.worldObj.getBlockState(this.pos).getValue(BlockStoveTop.VESSEL)).meta);	
	//}


	// TODO: this should actually make sure the player is close enough.

	public boolean isUsableByPlayer(final EntityPlayer player) {
		return true;
	}


	//ewww. not sure if this is even required....
	// update: yeash its used by the container i guess
	public int getField(final int id) {
		switch (id) {
			case 0:
				return this.burn_time;
			case 1:
				return this.initial_burn_time;
			case 2:
				return this.cook_time;
			case 3:
				return this.cook_time_goal;
			default:
				return 0;
		}
	}

	public void setField(final int id, final int value) {
		switch (id) {
			case 0:
				this.burn_time = value;
				break;
			case 1:
				this.initial_burn_time = value;
				break;
			case 2:
				this.cook_time = value;
				break;
			case 3:
				this.cook_time_goal = value;
				break;
		}
	}

}