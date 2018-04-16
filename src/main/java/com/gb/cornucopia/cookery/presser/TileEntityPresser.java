package com.gb.cornucopia.cookery.presser;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.cuisine.Cuisine;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.ItemVeggieSeed;
import com.gb.cornucopia.veggie.Veggie;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TileEntityPresser extends TileEntity implements ITickable, IInventory {
	private final ItemStack[] contents = new ItemStack[2];

	@Override
	public String getName() {
		return "presser";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString("presser");
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.contents) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void update() {
	}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack) {
		return false;
	}

	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer player) {
		return new ContainerPresser(playerInventory, (IInventory) this);
	}

	@Override
	public int getField(final int id) {
		return 0;
	}

	@Override
	public void setField(final int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.contents.length; ++i) {
			this.contents[i] = null;
		}
	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		//return !isVanilla || (oldState.getBlock() != newSate.getBlock()); << this makes me want to fucking puke. for shame.
		return (oldState.getBlock() != newState.getBlock());

	}

	private boolean hasOrEmpty(Item i) {
		return this.contents[1] == null || this.contents[1].getItem() == i;
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

	public boolean canPress() {
		if (!this.hasWorld() || this.world.isRemote) {
			return false;
		} else if (this.contents[0] == null) {
			return true; // so players can see how it works without loading it up
		}

		final Item i = this.contents[0].getItem();
		return this.canPress(i);
	}

	public void press(Item output, int ratio, Item byproduct, int byproduct_ratio) {
		final ItemStack in_stack = this.contents[0]; // already null checked in press()
		final ItemStack out_stack = this.contents[1] != null ? this.contents[1] : new ItemStack(output, 0);

		final int can_make = Math.min(in_stack.getCount() / ratio, out_stack.getMaxStackSize() - out_stack.getCount());
		if (can_make == 0) {
			// inputs drop out for certain recipes
			this.world.spawnEntity(new EntityItem(this.world, this.pos.getX() + .5, this.pos.getY() + 1, this.pos.getZ() + .5, in_stack));
			this.contents[0] = null;
			return;
		} else if (contents[1] == null) {
			contents[1] = out_stack; // re assign if we needed a new stack
		}

		final int ingredients_needed = can_make * ratio;

		if (byproduct != null) {
			this.contents[0] = byproduct != null ? new ItemStack(byproduct, ingredients_needed / byproduct_ratio) : null;
		} else {
			this.contents[0] = null;
		}

		out_stack.grow(can_make);
		in_stack.shrink(ingredients_needed);

		if (in_stack.getCount() > 0) {
			this.world.spawnEntity(new EntityItem(this.world, this.pos.getX() + .5, this.pos.getY() + 1, this.pos.getZ() + .5, in_stack));
		}
	}

	public void press(Item output, int ratio, Item byproduct) {
		press(output, ratio, byproduct, 1);
	}

	public void press(Item output, int ratio) {
		press(output, ratio, null, 1);
	}

	public void press() {
		if (!this.hasWorld() || this.world.isRemote || !this.canPress() || this.contents[0] == null) {
			return;
		}
		final Item i = this.contents[0].getItem();
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
	public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
		final ItemStack in_stack = this.contents[0];
		if (in_stack != null && in_stack.getItem() != null) {
			final NBTTagCompound in_tag = new NBTTagCompound();
			in_stack.writeToNBT(in_tag);
			compound.setTag("in", in_tag);
		}

		final ItemStack out_stack = this.contents[1];
		if (out_stack != null && out_stack.getItem() != null) {
			final NBTTagCompound out_tag = new NBTTagCompound();
			out_stack.writeToNBT(out_tag);
			compound.setTag("out", out_tag);
		}
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(final NBTTagCompound parentNBTTagCompound) {
		super.readFromNBT(parentNBTTagCompound); // The super call is required to load the tiles location
		final NBTTagCompound in_tag = parentNBTTagCompound.getCompoundTag("in");
		if (in_tag != null) {
			this.contents[0] = new ItemStack(in_tag);
		} else {
			this.contents[0] = null;
		}

		final NBTTagCompound out_tag = parentNBTTagCompound.getCompoundTag("out");
		if (out_tag != null) {
			this.contents[1] = new ItemStack(out_tag);
		} else {
			this.contents[1] = null;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(this.pos) < 6;
	}

	@Override
	public ItemStack getStackInSlot(final int index) {
		return contents[index];
	}

	@Override
	public ItemStack decrStackSize(final int index, final int count) {
		if (this.contents[index] != null) {
			final ItemStack stack = this.contents[index].splitStack(Math.min(count, this.contents[index].getCount()));
			if (this.contents[index].getCount() == 0) {
				this.contents[index] = null;
			}
			this.markDirty();
			return stack;
		}
		return null;
	}

	public void setInventorySlotContents(final int index, final ItemStack stack) {
		this.contents[index] = stack;

		if (stack != null && stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		this.markDirty();
	}

	@Override
	public void openInventory(final EntityPlayer player) {
	}

	@Override
	public void closeInventory(final EntityPlayer player) {
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		final ItemStack i = this.contents[index];
		this.contents[index] = null;
		return i;
	}

}
