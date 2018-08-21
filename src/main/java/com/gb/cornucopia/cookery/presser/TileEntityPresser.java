package com.gb.cornucopia.cookery.presser;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.cuisine.Cuisine;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.ItemVeggieSeed;
import com.gb.cornucopia.veggie.Veggie;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
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

public class TileEntityPresser extends TileEntity implements ITickable {
	public ItemStackHandler inventory = new ItemStackHandler(2);

	@Override
	public void update() {
	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return (oldState.getBlock() != newState.getBlock());

	}

	private boolean hasOrEmpty(Item i) {
		return inventory.getStackInSlot(1).isEmpty() || inventory.getStackInSlot(1).getItem() == i;
	}

	public boolean canPress(Item i) {
		return (i == Bees.honeycomb && this.hasOrEmpty(Bees.honey_raw))
				|| (i == Fruit.olive.raw && this.hasOrEmpty(Cuisine.olive_oil))
				|| (Cuisine.hathJuice(i) && this.hasOrEmpty(Cuisine.getJuice(i)))
				|| (i == Items.MILK_BUCKET && this.hasOrEmpty(Cuisine.butter))
				|| (i == Veggie.peanut.raw && this.hasOrEmpty(Cuisine.canola_oil))
				|| (i == Cuisine.pasta_dough && this.hasOrEmpty(Cuisine.fresh_pasta))
				|| (i == Veggie.soy.raw && this.hasOrEmpty(Cuisine.tofu))
				|| (i == Items.WHEAT_SEEDS && this.hasOrEmpty(Cuisine.canola_oil))
				|| (i instanceof ItemVeggieSeed && this.hasOrEmpty(Cuisine.canola_oil))
				;
	}

	public static boolean pressable(Item i) {
		return (i == Bees.honeycomb)
				|| (i == Fruit.olive.raw)
				|| (Cuisine.hathJuice(i))
				|| (i == Items.MILK_BUCKET)
				|| (i == Veggie.peanut.raw)
				|| (i == Cuisine.pasta_dough)
				|| (i == Veggie.soy.raw)
				|| (i == Items.WHEAT_SEEDS)
				|| (i instanceof ItemVeggieSeed)
				;
	}

	public boolean canPress() {
		if (!this.hasWorld() || this.world.isRemote) {
			return false;
		} else if (inventory.getStackInSlot(0).isEmpty()) {
			return true; // so players can see that it works without opening gui
		}

		final Item i = inventory.getStackInSlot(0).getItem();
		return this.canPress(i);
	}

	public void press(Item output, int ratio, Item byproduct, int byproduct_ratio) {

		ItemStack in_stack = inventory.getStackInSlot(0);
		ItemStack out_stack = inventory.getStackInSlot(1);

		//returns how many can be made based on ratio and how much space left in output slot
		final int make_amount = Math.min(in_stack.getCount() / ratio, out_stack.getMaxStackSize() - out_stack.getCount());
		final int ingredients_needed = make_amount * ratio;
		System.out.println("make amount: " + make_amount + " and ingredients needed: " + ingredients_needed);

		//stuff can be made and the output slot is empty
		if (in_stack.getCount() >= ingredients_needed && out_stack.isEmpty()) {
			in_stack.shrink(ingredients_needed);
			inventory.setStackInSlot(1, new ItemStack(output, make_amount));
			if (byproduct != null) {
				//TODO currently nothing has a byproduct ratio of more than one, this will need to be revisited if that ever becomes the case
				ItemStack dropped_byproduct = new ItemStack(byproduct, ingredients_needed/byproduct_ratio);
				this.world.spawnEntity(new EntityItem(this.world, this.pos.getX() + .5, this.pos.getY() + 1, this.pos.getZ() + .5, dropped_byproduct));
			}
		}
		//stuff can be made and the output slot already has some output items (if has anything else then nothing will happen)
		if (in_stack.getCount() >= ingredients_needed && out_stack.getItem()==output) {
			in_stack.shrink(ingredients_needed);
			out_stack.grow(make_amount);
			if (byproduct != null) {
				//TODO currently nothing has a byproduct ratio of more than one, this will need to be revisited if that ever becomes the case
				ItemStack dropped_byproduct = new ItemStack(byproduct, ingredients_needed/byproduct_ratio);
				this.world.spawnEntity(new EntityItem(this.world, this.pos.getX() + .5, this.pos.getY() + 1, this.pos.getZ() + .5, dropped_byproduct));
			}
		}
	}

	public void press(Item output, int ratio, Item byproduct) {
		press(output, ratio, byproduct, 1);
	}

	public void press(Item output, int ratio) {
		press(output, ratio, null, 1);
	}

	public void press() {
		if (!this.hasWorld() || this.world.isRemote || !this.canPress() || inventory.getStackInSlot(0).isEmpty()) {
			return;
		}
		final Item i = inventory.getStackInSlot(0).getItem();
		if (i == Bees.honeycomb) {
			press(Bees.honey_raw, 2, Bees.waxcomb);

		} else if (i == Fruit.olive.raw) {
			press(Cuisine.olive_oil, 4);

		} else if (i == Items.MILK_BUCKET) {
			press(Cuisine.butter, 1, Items.BUCKET);

		} else if (i == Items.WHEAT_SEEDS) {
			press(Cuisine.canola_oil, 32);

		} else if (i instanceof ItemVeggieSeed) {
			press(Cuisine.canola_oil, 16);

		} else if (i == Veggie.soy.raw) {
			press(Cuisine.tofu, 8);

		} else if (i == Veggie.peanut.raw) {
			press(Cuisine.canola_oil, 8);

		} else if (Cuisine.hathJuice(i)) {
			press(Cuisine.getJuice(i), 9); // experimenting with non po2

		} else if (i == Cuisine.pasta_dough) {
			press(Cuisine.fresh_pasta, 1); // experimenting with non po2

		}
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

}
