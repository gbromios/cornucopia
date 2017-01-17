package com.gb.cornucopia.cookery.mill;

import com.gb.cornucopia.cuisine.Cuisine;
import com.gb.cornucopia.veggie.Veggie;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityMill extends TileEntity implements ITickable, IInventory  {
	private final ItemStack[] contents = new ItemStack[9];

	@Override
	public String getName() { return "mill"; }

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public ITextComponent getDisplayName() { return new TextComponentString("mill"); }

	@Override
	public int getSizeInventory() {	return 9; }

	@Override
	public void update() {}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack) {
		return false;
	}

	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer player)
	{
		return new ContainerMill(playerInventory, (IInventory)this);
	}

	@Override
	public int getField(final int id) { return 0; }

	@Override
	public void setField(final int id, int value) {}

	@Override
	public int getFieldCount() { return 0; }

	@Override
	public void clear() {
		for (int i = 0; i < this.contents.length; ++i)
		{
			this.contents[i] = null;
		}
	}

	private boolean _canMakeFlour() {
		return this._hasInput(Items.WHEAT) + this._hasInput(Veggie.barley.raw) == 3;
	}

	private boolean _makeFlour(){
		// 3 grain => 2 flour
		final ItemStack drop = new ItemStack(Cuisine.flour, 2);
		final int where = this._canAccept(drop);
		if (where == -1) {
			return false;
		}
		try {
			if ( --this.contents[0].stackSize == 0) { this.contents[0] = null; }
			if ( --this.contents[1].stackSize == 0) { this.contents[1] = null; }
			if ( --this.contents[2].stackSize == 0) { this.contents[2] = null; }
		} catch (NullPointerException e) {
			// screw whoever broke my sweet machine
			this.contents[0] = null;
			this.contents[1] = null;
			this.contents[2] = null;
			return false;
		}

		final ItemStack output = this.contents[where];
		if (output != null) {
			output.stackSize += drop.stackSize;;
		} else {
			this.contents[where] = drop;
		}
		return true;
	}

	private boolean _canMakePeanutButter() {
		return this._hasInput(Veggie.peanut.raw) == 3;
	}

	private boolean _makePeanutButter(){
		final ItemStack i = new ItemStack(Cuisine.peanut_butter);
		final int where = this._canAccept(i);
		if (where == -1) {
			return false;
		}
		try {
			if ( --this.contents[0].stackSize == 0) { this.contents[0] = null; }
			if ( --this.contents[1].stackSize == 0) { this.contents[1] = null; }
			if ( --this.contents[2].stackSize == 0) { this.contents[2] = null; }
		} catch (NullPointerException e) {
			// screw whoever broke my sweet machine
			this.contents[0] = null;
			this.contents[1] = null;
			this.contents[2] = null;
			return false;
		}

		final ItemStack o = this.contents[where];
		if (o != null) {
			o.stackSize++;;
		} else {
			this.contents[where] = i;
		}
		return true;
	}

	private boolean _canMakeHerbs() {
		return this._hasInput(Veggie.herb.raw) > 0;
	}

	private boolean _makeHerbs(){
		final ItemStack drop = new ItemStack(Cuisine.herb_drops.getRandom());
		final int where = this._canAccept(drop);
		if (where == -1) {
			return false;
		}

		for (int i = 0; i < 2; i++) {
			final ItemStack input = contents[i];
			if ( input == null || input.getItem() != Veggie.herb.raw) { 
				continue; 
			} else if (--input.stackSize < 1) {
				contents[i] = null;
			}

			final ItemStack output = this.contents[where];
			if (output == null) {
				this.contents[where] = drop;
			} else {
				output.stackSize++;
			}
			return true;
		}
		return false;
	}

	private boolean _canMakeSpices() {
		return this._hasInput(Veggie.spice.raw) > 0;
	}
	private boolean _makeSpices(){
		final ItemStack drop = new ItemStack(Cuisine.spice_drops.getRandom());
		final int where = this._canAccept(drop);
		if (where == -1) {
			return false;
		}

		for (int i = 0; i < 2; i++) {
			final ItemStack input = contents[i];
			if ( input == null || input.getItem() != Veggie.spice.raw) { 
				continue; 
			} 

			if (--input.stackSize < 1) {
				contents[i] = null;
			}
			final ItemStack output = this.contents[where];
			if (output == null) {
				this.contents[where] = drop;
			} else {
				output.stackSize++;
			}
			return true;
		}
		return false;
	}


	public boolean mill(){
		// returns true if an item was milled
		// shutup its fine

		if (this._hasEmptyInput()) { return false; }		
		if (this._canMakeFlour()) { return this._makeFlour(); }
		if (this._canMakePeanutButter()) { return this._makePeanutButter(); }
		if (this._canMakeHerbs()) { return this._makeHerbs(); }
		if (this._canMakeSpices()) { return this._makeSpices(); }

		return false; // these input items don't make anything >:(	
	}

	private boolean _hasEmptyInput() {
		return this.contents[0] == null && this.contents[1] == null && this.contents[2] == null;
	}

	private int _hasInput(Item i){
		return ((contents[0] != null && contents[0].getItem() == i) ? 1 : 0) 
				+ ((contents[1] != null && contents[1].getItem() == i) ? 1 : 0)
				+ ((contents[2] != null && contents[2].getItem() == i) ? 1 : 0);
	}

	int _canAccept(ItemStack drop) {
		// drop items in a specific order
		for (int i : new int[]{7, 6, 8, 4, 3, 4}) {
			final ItemStack output = this.contents[i];
			if ( output == null || (drop.isItemEqual(output) && drop.stackSize + output.stackSize <= output.getMaxStackSize())) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	@Override
	public void readFromNBT(final NBTTagCompound compound)
	{
		//System.out.println("read from nbt: " + compound.toString());
		super.readFromNBT(compound);
		final NBTTagList items = compound.getTagList("items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < 6; i++)
		{
			final NBTTagCompound item = items.getCompoundTagAt(i);
			if (item != null) {
				this.contents[i] = ItemStack.loadItemStackFromNBT(item);
			}
			else {
				this.contents[i] = null;	
			}
		}
	}


	@Override
	public void writeToNBT(final NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < 6; i++) {
			final ItemStack s = this.contents[i];
			final NBTTagCompound input_tag = new NBTTagCompound();
			if (s != null) {
				s.writeToNBT(input_tag);
			}
			items.appendTag(input_tag);
		}
		compound.setTag("items", items);

	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
		//return !isVanilla || (oldState.getBlock() != newSate.getBlock()); << this makes me want to fucking puke. for shame.
		return (oldState.getBlock() != newState.getBlock());

	}

	@Override
	public int getInventoryStackLimit() {return 64;}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {	
		return player.getDistanceSq(this.pos) < 6;
	}

	@Override
	public ItemStack getStackInSlot(final int index) {
		return contents[index];
	}

	@Override
	public ItemStack decrStackSize(final int index, final int count) { 
		if (this.contents[index] != null)
		{
			final ItemStack stack = this.contents[index].splitStack(Math.min(count, this.contents[index].stackSize));
			if (this.contents[index].stackSize == 0)
			{
				this.contents[index] = null;
			}
			this.markDirty();
			return stack;
		}
		return null;
	}

	public void setInventorySlotContents(final int index, final ItemStack stack)
	{
		this.contents[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	@Override
	public void openInventory(final EntityPlayer player) {}

	@Override
	public void closeInventory(final EntityPlayer player) {}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		final ItemStack i = this.contents[index];
		this.contents[index] = null;
		return i;
	}
}
