package com.gb.cornucopia.cookery.stove;

import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cookery.Vessel;
import com.gb.cornucopia.cuisine.dish.Dish;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityStove extends TileEntity implements IUpdatePlayerListBox, IInventory {
	private final ItemStack[] contents = new ItemStack[8]; // 0 = fuel, 1-6 input, 7 output
	
	private int burn_time = 0; // time left burning current fuel: ticks DOWN every update
	private int initial_burn_time = 1; // max burn time of the item currently used for fuel
	
	private int cook_time = 0; // how long the current recipe has been cooking. ticks UP every update
	private int cook_time_goal = 1; // vanilla furnace cooks things in 200.
	
	private Dish whats_cooking = null; // storing this field lets us avoid calculating recipes unless there's a chance it's changed (i.e. changed input i.e. inventory slots )
	private boolean input_changed = false; // set this to true when fucking with the input slots, check in every update and
	
	@Override
	public void readFromNBT(final NBTTagCompound compound)
	{
		//System.out.println("read from nbt: " + compound.toString());
		super.readFromNBT(compound);
		final NBTTagList items = compound.getTagList("items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < 8; i++)
		{
			final NBTTagCompound item = items.getCompoundTagAt(i);
			if (item != null) {
				this.contents[i] = ItemStack.loadItemStackFromNBT(item);
			}
			else {
				this.contents[i] = null;	
			}
		}
		this.markInputChanged();
		
		// TODO: maybe what's cooking is not working? does that need saved?
		// I hope not, because saving dishes to nbt would suck ASS

		this.burn_time = compound.getInteger("burn_time");
		this.initial_burn_time = compound.getInteger("initial_burn_time");
		
		this.cook_time = compound.getInteger("cook_time");
		this.cook_time_goal = compound.getInteger("cook_time_goal");
		

		//final String s = String.format("Stove_B%d/%d_C%d/%d", this.burn_time, this.initial_burn_time, this.cook_time, this.cook_time_goal);
		//System.out.println("...now I'm like: " + s);
		//if (compound.hasKey("CustomName", 8))
		//{
		//this.custom_name = compound.getString("CustomName");
		//}
	}

	@Override
	public void writeToNBT(final NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("burn_time", this.burn_time);
		compound.setInteger("initial_burn_time", this.initial_burn_time);
		compound.setInteger("cook_time", this.cook_time);
		compound.setInteger("cook_time_goal", this.cook_time_goal);

		NBTTagList items = new NBTTagList();
		for (int i = 0; i < 8; i++) {
			final ItemStack s = this.contents[i];
			final NBTTagCompound input_tag = new NBTTagCompound();
			if (s != null) {
				s.writeToNBT(input_tag);
			}
			items.appendTag(input_tag);
		}
		compound.setTag("items", items);
		//if (this.hasCustomName()){ compound.setString("CustomName", "whatever"); }		
		//System.out.println("wrote nbt: " + compound.toString());
	}
	
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
    	//return !isVanilla || (oldState.getBlock() != newSate.getBlock()); << this makes me want to fucking puke. for shame.
    	return (oldState.getBlock() != newState.getBlock());
    	
    }

	
	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound nbtTagCompound = new NBTTagCompound();
		this.writeToNBT(nbtTagCompound);
		final int metadata = getBlockMetadata();
		S35PacketUpdateTileEntity pkt = new S35PacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
		//System.out.println("getting data to send : " + pkt.toString());
		return pkt;
		
	}

	@Override
	public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
	//System.out.println("got data pkt: " + pkt.toString());
		this.readFromNBT(pkt.getNbtCompound());
	}


	@Override
	public String getName() { return "stove"; }

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public IChatComponent getDisplayName() { return new ChatComponentText("stove"); }

	@Override
	public int getSizeInventory() {	return 8; }

	@Override
	public ItemStack getStackInSlot(final int index) {
		return this.contents[index];

	}

	public boolean isBurning()
	{
		return this.burn_time > 0;
	}
	
	public Vessel getVessel() {
		return BlockStove.getVessel(this.worldObj, this.pos);
	}
	
	// different, more specific than markDirty();
	public void markInputChanged() {
	//System.out.format("~~input changed~~!\n");
		this.input_changed = true;
	}
	
	private void _consumeIngredients() {
		for (int i = 1; i <= 6; i++){
			this.decrStackSize(i, 1);
		}
	}
	
	private Dish _whatsCooking(){
		// ok what is getting cooked:
		return this.getVessel().getDishes().findMatchingDish((IInventory)this, 1, 6);
	}
	
	// force the stove block below us to light up (or turn off)
	private void _didBurningChange (final boolean was_burning) {
		if (was_burning != this.isBurning()) {			
			final IBlockState stove_state = this.worldObj.getBlockState(pos);
			if (stove_state.getBlock() == Cookery.stove) {
				this.worldObj.setBlockState(this.pos, stove_state.withProperty(BlockStove.ON, this.isBurning()));
			}
			// if the fire has just gone out this tick without being re-lit, the cooking is over :(
			if (was_burning) {
				this.cook_time = 0;
			}
		}
	}
	
	private boolean _debug_update(){
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
	
	private void _debug_update_end(boolean marked){
		if (this.isBurning() && (this.burn_time + 1) % 50 < 3 ){
		//System.out.format("BURNTIME: %d\n", this.burn_time);	
		}
		if (this.cook_time > 0 && (this.cook_time + 1) % 50 < 3) {
		//System.out.format("COOKTIME: %d\n", this.cook_time);
		}
	}

	@Override
	public void update() {
		if (!this.worldObj.isRemote) {
			final boolean debug = this._debug_update();
			
			// before we tick down, so we can track wether the ON state should change this tick
			final boolean was_burning = this.isBurning();
			if (this.isBurning()) {
				this.burn_time--;
			}
			
			// after the burn counter has had a chance decrement, then we can deal with a non-burning oven.
			if (!this.isBurning() && TileEntityStove.getFuelValue(this.contents[0]) > 0) {
			//System.out.format(" START FIRE: %d -> %s \n", this.burn_time, this.getFuelValue(this.contents[0]));
				this.burn_time = TileEntityStove.getFuelValue(this.contents[0]); 
				this.initial_burn_time = this.burn_time;
				this.decrStackSize(0, 1);
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
				if ( this.whats_cooking != null){
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
						final ItemStack output = this.contents[7];
						
						if (output == null) {
							// take one thing out of each slot in the crafting input
							this._consumeIngredients();
							this.markInputChanged();
							
							// if there's no output stack, put one there.
							this.contents[7] = this.whats_cooking.getItem();
							this.cook_time = 0; //reset the cooking timer. theoretically, a changed input grid should take care of it but whatever?
						}
						
						// existing output is a bit tricker:
						else if ( // stupid way of saying "can these stacks be merged"
								output.isStackable()
								&& output.isItemEqual(result)
								&& ItemStack.areItemStackTagsEqual(output, result) // wuuuut
								&& output.stackSize + result.stackSize <= this.getInventoryStackLimit()
								) {
							this._consumeIngredients();
							output.stackSize += result.stackSize;
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

	// S M H
	public static int getFuelValue(final ItemStack stack)
	{
		if (stack == null)
		{
			return 0;
		}
		else
		{
			Item item = stack.getItem();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
			{
				final Block block = Block.getBlockFromItem(item);

				if (block == Blocks.wooden_slab)
				{
					return 150;
				}

				if (block.getMaterial() == Material.wood)
				{
					return 300;
				}

				if (block == Blocks.coal_block)
				{
					return 16000;
				}
			}

			if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD")) return 200;
			if (item == Items.stick) return 100;
			if (item == Items.coal) return 1600;
			if (item == Items.lava_bucket) return 20000;
			if (item == Item.getItemFromBlock(Blocks.sapling)) return 100;
			if (item == Items.blaze_rod) return 2400;
			return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int index)
	{
		return null;
		/*
		if (this.getStackInSlot(index) != null)
		{
			final ItemStack stack = this.getStackInSlot(index);
			this.setInventorySlotContents(index,  null);
			return stack;
		}

		return null;*/
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(final int index, final ItemStack stack)
	{
		this.contents[index] = stack;
		this.markDirty();
	}

	@Override
	public void openInventory(final EntityPlayer player) {}

	@Override
	public void closeInventory(final EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack) {
		// afaict this doesn't do shit???
		return false;
	}

	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer player)
	{
		return new ContainerStove(playerInventory, this);
	}

	// i think this is obsolete~?
	//public DishRegistry getDishes(){
		//return DishRegistry.byID(((Vessel)this.worldObj.getBlockState(this.pos).getValue(BlockStoveTop.VESSEL)).meta);	
	//}

	@Override
	public void clear() {
	}

	@Override
	public ItemStack decrStackSize(final int index, final int count) { 
		if (this.contents[index] != null)
		{
			ItemStack stack = this.contents[index].splitStack(Math.min(count, this.contents[index].stackSize));
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
	public int getInventoryStackLimit() {return 64;}

	 // TODO: this should actually make sure the player is close enough.
	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) { return true; }


	//ewww. not sure if this is even required....
	// update: yeash its used by the container i guess
	public int getField(final int id)
	{
		switch (id)
		{
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
	public void setField(final int id, final int value)
	{   
		switch (id)
		{
		case 0:
			this.burn_time= value;
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
	};

	public int getFieldCount(){return 4;};
}