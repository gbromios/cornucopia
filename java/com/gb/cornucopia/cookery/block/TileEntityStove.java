package com.gb.cornucopia.cookery.block;

import com.gb.cornucopia.bees.crafting.ContainerApiary;
import com.gb.cornucopia.cookery.crafting.ContainerStove;
import com.gb.cornucopia.cookery.crafting.DishRegistry;

import net.minecraft.block.BlockFurnace;
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
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityStove extends TileEntity implements IUpdatePlayerListBox, IInventory  {
	private final ItemStack[] contents = new ItemStack[11]; // 0 = fuel, 1-9 input, 10 output
	private int burn_time; // time left burning current fuel
	private int burn_time_max; // max burn time of the item currently used for fuel
	private int cook_time; // ticks the current recipe has been cooking
	private final static int initial_cook_time = 120; // vanilla furnace cooks things in 200 for reference
	private ItemStack currently_cooking; // the 

	@Override
	public String getName() { return "stove"; }

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public IChatComponent getDisplayName() { return new ChatComponentText("stove"); }

	@Override
	public int getSizeInventory() {	return 11; }

	@Override
	public ItemStack getStackInSlot(int index) {
		return contents[index];
	}

	public boolean isBurning()
	{
		return this.burn_time > 0;
	}

	@Override
	public void update() {
		if (this.currently_cooking == null) {
			// nothing to do except burn
			if (this.isBurning()){
				this.cook_time = 0;
				this.burn_time--;
				this.markDirty(); // might not be necessary? only counter values are changing.
			}
			return;
		}

		ItemStack fuel = this.contents[0];
		ItemStack output = this.contents[10];
		boolean dirty = false;        

		// no burning = try to start a fire
		if (!this.isBurning() && fuel != null && GameRegistry.getFuelValue(fuel) > 0){
			this.burn_time_max = this.burn_time = GameRegistry.getFuelValue(fuel);
		}

		if (this.isBurning()){
			// an item has been cooked!
			if (this.cook_time == this.initial_cook_time){
				// produce output in the output slot
				if (output == null){
					this.contents[10] = this.currently_cooking.copy();
				}
				// we can assume that the item in the output slot is the same, otherwise currently_cooking would be null
				else {
					this.contents[10].stackSize += this.currently_cooking.stackSize;
				}

				// reset the counter
				this.cook_time = 0;

				// decrement all the input stacks
				for (int i = 1; i < 10; i++){
					this.decrStackSize(i, 1);
				}

			}
			else{

			}
		}

		// still no fire... 
		else {
		}



		if (dirty)
		{
			this.markDirty();
		}

	}

	@Override
	public ItemStack decrStackSize(int index, int count){
		return this.decrStackSize(index, count, true);
	}
	public ItemStack decrStackSize(int index, int count, boolean recalc) { 
		if (this.contents[index] != null)
		{
			ItemStack stack = this.contents[index].splitStack(Math.min(count, this.contents[index].stackSize));
			if (this.contents[index].stackSize == 0)
			{
				// no idea how smart itemstacks are about null references, so check it pls
				Item ci = stack.getItem().getContainerItem();
				this.contents[index] = (ci == null) ? new ItemStack(ci) : null; 
			}

			this.markDirty();
			if (recalc){ this.calculateOutput(); }
			return stack;
		}
		else
		{
			return null;
		}
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

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// afaict this doesn't do shit???
		return false;
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player)
	{
		return new ContainerStove(playerInventory, (IInventory)this);
	}
	
	public DishRegistry getDishes(){
		return DishRegistry.byID(((Vessel)this.worldObj.getBlockState(this.pos).getValue(BlockStoveTop.VESSEL)).meta);	
	}
	
	public void calculateOutput(){
		ItemStack stack = null;
		
		this.currently_cooking = (stack == null) ? null : stack.copy();
		this.cook_time = 0;		
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
	public int getInventoryStackLimit() {return 64;}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) { return true; }
	
	

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
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.readFromNBT(parentNBTTagCompound); // The super call is required to load the tiles location
	}


}
