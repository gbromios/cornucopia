package com.gb.cornucopia.cookery.presser;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.cuisine.Cuisine;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggies;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class TileEntityPresser extends TileEntity implements IUpdatePlayerListBox, IInventory  {
	private final ItemStack[] contents = new ItemStack[2];

	@Override
	public String getName() { return "presser"; }

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public IChatComponent getDisplayName() { return new ChatComponentText("presser"); }

	@Override
	public int getSizeInventory() {	return 2; }

	@Override
	public void update() {}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack) {
		return false;
	}

	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer player)
	{
		return new ContainerPresser(playerInventory, (IInventory)this);
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

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
		//return !isVanilla || (oldState.getBlock() != newSate.getBlock()); << this makes me want to fucking puke. for shame.
		return (oldState.getBlock() != newState.getBlock());

	}

	public static boolean canPress(Item i) {
		return i == Bees.honeycomb
				|| i == Fruit.olive.raw
				|| i == Veggies.grape.raw
				;
	}
	
	public boolean canPress(){
		if (!this.hasWorldObj() || this.worldObj.isRemote){
			return false;
		} else if (this.contents[0] == null) {
			return true; // so players can see how it works without loading it up
		}

		final Item i = this.contents[0].getItem();
		return TileEntityPresser.canPress(i);
	}

	public void press(Item output, int ratio, Item byproduct, int byproduct_ratio){
		final ItemStack in_stack = this.contents[0]; // already null checked in press()
		final ItemStack out_stack = this.contents[1] != null ? this.contents[1] : new ItemStack(output, 0);

		final int can_make = Math.min(in_stack.stackSize / ratio, out_stack.getMaxStackSize() - out_stack.stackSize);
		if ( can_make == 0 ) {
			// inputs drop out for certain recipes
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj,  this.pos.getX() + .5,  this.pos.getY() + 1, this.pos.getZ() + .5, in_stack));
			this.contents[0] = null;
			return;
		} else if (contents[1] == null)  {
			contents[1] = out_stack; // re assign if we needed a new stack
		}

		final int ingredients_needed = can_make * ratio;

		if (byproduct != null){
			this.contents[0] = byproduct != null ? new ItemStack(byproduct, ingredients_needed / byproduct_ratio) : null;
		} else {
			this.contents[0] = null;
		}
		
		out_stack.stackSize += can_make;
		in_stack.stackSize -= ingredients_needed;
		
		if (in_stack.stackSize > 0) {
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj,  this.pos.getX() + .5,  this.pos.getY() + 1, this.pos.getZ() + .5, in_stack));
		}
	}

	public void press(Item output, int ratio, Item byproduct){
		press(output, ratio, byproduct, 1);
	}

	public void press(Item output, int ratio){
		press(output, ratio, null, 1);
	}

	public void press(){
		if (!this.hasWorldObj() || this.worldObj.isRemote || !this.canPress() || this.contents[0] == null){
			return;
		}
		final Item i = this.contents[0].getItem();
		if (i == Bees.honeycomb) {
			press(Bees.honey_raw, 2, Bees.waxcomb);

		} else if (i == Fruit.olive.raw) {
			press(Cuisine.oil_olive, 4);

		} else if (i == Veggies.grape.raw) {
			press(Cuisine.grape_juice, 8);

		}
	}

	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new S35PacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void writeToNBT(final NBTTagCompound parentNBTTagCompound)
	{	
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save the tiles location
		final ItemStack in_stack = this.contents[0];
		if (in_stack != null && in_stack.getItem() != null) {
			final NBTTagCompound in_tag = new NBTTagCompound();
			in_stack.writeToNBT(in_tag);
			parentNBTTagCompound.setTag("in", in_tag);
		} //else {
		//parentNBTTagCompound.setTag("in", null);
		//}

		final ItemStack out_stack = this.contents[1];
		if (out_stack != null && out_stack.getItem() != null) {
			final NBTTagCompound out_tag = new NBTTagCompound();
			out_stack.writeToNBT(out_tag);
			parentNBTTagCompound.setTag("out", out_tag);
		} //else {
		//parentNBTTagCompound.setTag("out", null);
		//}
	}

	@Override
	public void readFromNBT(final NBTTagCompound parentNBTTagCompound)
	{
		super.readFromNBT(parentNBTTagCompound); // The super call is required to load the tiles location
		final NBTTagCompound in_tag = parentNBTTagCompound.getCompoundTag("in");
		if (in_tag != null) {
			this.contents[0] = ItemStack.loadItemStackFromNBT(in_tag);
		} else {
			this.contents[0] = null;
		}

		final NBTTagCompound out_tag = parentNBTTagCompound.getCompoundTag("out");
		if (out_tag != null) {
			this.contents[1] = ItemStack.loadItemStackFromNBT(out_tag);
		} else {
			this.contents[1] = null;
		}
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

	@Override
	public ItemStack getStackInSlotOnClosing(final int index)
	{
		if (this.contents[index] != null)
		{
			final ItemStack stack = this.contents[index];
			this.contents[index] = null;
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
}
