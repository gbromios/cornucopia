package com.gb.cornucopia.cookery.block;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.cookery.crafting.ContainerPresser;
import com.gb.cornucopia.cookery.crafting.ContainerStove;
import com.gb.cornucopia.cookery.crafting.DishRegistry;

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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scala.Console;

public class TileEntityPresser extends TileEntity implements IUpdatePlayerListBox, IInventory  {
	private final ItemStack[] contents = new ItemStack[2];
	private int state = 0; // time left burning current fuel
	private int count = 0; // max burn time of the item currently used for fuel

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
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player)
	{
		return new ContainerPresser(playerInventory, (IInventory)this);
	}
	
	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() { return 0; }

	@Override
	public void clear() {
		for (int i = 0; i < this.contents.length; ++i)
		{
			this.contents[i] = null;
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new S35PacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);


	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	
	@Override
	public void writeToNBT(NBTTagCompound parentNBTTagCompound)
	{	
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save the tiles location
		ItemStack c1 = this.contents[0];
		if (c1 != null && c1.getItem() != null) {
			if (c1.getItem() == Bees.honeycomb){
				parentNBTTagCompound.setInteger("state", 1 );
			}
			else {
				parentNBTTagCompound.setInteger("state", 2 );
			}
			parentNBTTagCompound.setInteger("count", c1.stackSize );
			
			
		}
		else {			
			parentNBTTagCompound.setInteger("state", 0 );
			parentNBTTagCompound.setInteger("count", 0 );			
		}

	}
	
	public void press(){
		// hard-coded honeycomb => waxcomb + honey
		// could easily make it use recipes instead
		ItemStack c1 = this.contents[0];
		if (c1 != null && c1.getItem() == Bees.honeycomb) {
			this.contents[0] = new ItemStack(Bees.waxcomb, c1.stackSize);
			this.contents[1] = new ItemStack(Bees.honey_raw, c1.stackSize);;
		}
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.readFromNBT(parentNBTTagCompound); // The super call is required to load the tiles location
		int state = parentNBTTagCompound.getInteger("state");
		int count = parentNBTTagCompound.getInteger("count");
		//System.out.format("READING PRESSER: %d/%d  ===>\n   1. %dx, %s\n   2. %dx, %s\n\n", state, count, this.contents[0].stackSize, this.contents[0].getItem().toString(), this.contents[1].stackSize, this.contents[1].getItem().toString());
		System.out.format("READING PRESSER: %d/%d\n\n", state, count);
		if (state == 1){
			this.contents[0] = new ItemStack(Bees.honeycomb, count);
			this.contents[1] = null;
		}
		else if (state == 2){
			this.contents[0] = new ItemStack(Bees.waxcomb, count);
			this.contents[1] = new ItemStack(Bees.honey_raw, count);
		}
		else {
			this.contents[0] = null;
			this.contents[1] = null;
		}
	}
	
	@Override
	public int getInventoryStackLimit() {return 64;}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {	
		return player.getDistanceSq(this.pos) < 6;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// 0 - queen
		// 1 - workers
		// 2-8 - honeycomb output slots
		return contents[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) { 
		if (this.contents[index] != null)
		{
			ItemStack itemstack;
			itemstack = this.contents[index].splitStack(Math.min(count, this.contents[index].stackSize));
			if (this.contents[index].stackSize == 0)
			{
				this.contents[index] = null;
			}
			this.markDirty();
			return itemstack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		if (this.contents[index] != null)
		{
			ItemStack itemstack = this.contents[index];
			this.contents[index] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.contents[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}
}
