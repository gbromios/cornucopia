package com.gb.cornucopia.cookery.block;

import com.gb.cornucopia.cookery.crafting.ContainerStove;
import com.gb.cornucopia.cookery.crafting.DishRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityStove extends TileEntity implements IUpdatePlayerListBox, IInventory {
	private final ItemStack[] contents = new ItemStack[8]; // 0 = fuel, 1-6 input, 7 output
	private int burn_time; // time left burning current fuel
	private int burn_time_max; // max burn time of the item currently used for fuel
	private int cook_time; // ticks the current recipe has been cooking
	private final static int initial_cook_time = 120; // vanilla furnace cooks things in 200 for reference
	private ItemStack currently_cooking; // the

	public Vessel getVessel() {
		// ask the BlockStoveTop above us. if there isn't one above us, something horrible happened a lot earlier~
		return (Vessel)this.worldObj.getBlockState(this.pos).getValue(((BlockStoveTop)this.blockType).VESSEL);
	}

	private class DummyContainer extends Container {
		@Override
		public boolean canInteractWith(EntityPlayer playerIn) {	return false; }

	}
	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
		
		System.out.format("aloha! \n");
		
        super.readFromNBT(compound);
        NBTTagList items = compound.getTagList("items", Constants.NBT.TAG_COMPOUND);
        //this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        this.clear();
        
        for (int i = 0; i < 8; i++)
        {
            NBTTagCompound item = items.getCompoundTagAt(i);
            if (item != null) {
            	this.contents[i] = ItemStack.loadItemStackFromNBT(item);
            }
            else {
            	this.contents[i] = null;	
            }

        }

        this.burn_time = compound.getInteger("burn_time");
        this.cook_time = compound.getInteger("cook_time");
        //this.initial_cook_time = compound.getInteger("initial_cook_time");
        this.burn_time_max = compound.getInteger("burn_time_max");

        //if (compound.hasKey("CustomName", 8))
        //{
            //this.furnaceCustomName = compound.getString("CustomName");
        //}
    }

	@Override
    public void writeToNBT(NBTTagCompound compound)
    {
		
		System.out.format("i send u...! \n");
		
        super.writeToNBT(compound);
        compound.setInteger("burn_time", this.burn_time);
        compound.setInteger("burn_time_max", this.burn_time_max);
        compound.setInteger("cook_time", this.cook_time);
        compound.setInteger("initial_cook_time", this.initial_cook_time);
        
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

        //if (this.hasCustomName()){ compound.setString("CustomName", this.furnaceCustomName); }
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
	public ItemStack getStackInSlot(int index) {
		return this.contents[index];

	}

	public boolean isBurning()
	{
		return this.burn_time > 0;
	}

	@Override
	public void update() {
		if (!this.worldObj.isRemote) {
			//System.out.println("tick..........");
			//System.out.println(this.fuel);
			this.burn_time--; 
			// no burning = try to start a fire
			if (!this.isBurning() && this.getFuelValue(this.contents[0]) > 0) {
				this.burn_time = this.getFuelValue(this.contents[0]) * 4; 
				this.burn_time_max = this.burn_time;
				this.decrStackSize(0, 1);
				this.markDirty();
				this.worldObj.markBlockForUpdate(this.pos.down());
			}
			
			if (this.isBurning()){
				this.cook_time++;
				if (this.cook_time >= this.initial_cook_time){
					// ok what is getting cooked:
					System.out.println("COOOK!");
					System.out.println(this.cook_time);
					System.out.println(this.burn_time);
					final DishRegistry d = this.getVessel().getDishes();
					ItemStack match = d.findMatchingDish((IInventory)this, worldObj, 1, 6);

					if (match == null) {
						//match = new ItemStack(Items.clay_ball); // have a special "Burnt Failure" item later;
						this.cook_time = 0;
						this.markDirty();
						return;
					} else {
					}
					
					if (this.contents[7] != null) {
						if (this.contents[7].getItem() == match.getItem()) {
							this.contents[7].stackSize++;
							for (int i = 1; i <= 6; i++){
								this.decrStackSize(i, 1);
							}
							this.cook_time = 0;
							this.markDirty();
						}
						else {
							// something else is in the way :I
						}
						
					}
					else {
						this.contents[7] = match;
						this.cook_time = 0;
						for (int i = 1; i <= 6; i++){
							this.decrStackSize(i, 1);
						}
					}

				}
				this.markDirty();
			}

			// fire goes out 
			else if (this.cook_time > 0) {
				this.cook_time = 0;
				this.worldObj.markBlockForUpdate(this.pos.down());
				this.markDirty();
			}
		}
	}
	
	// S M H
    public static int getFuelValue(ItemStack stack)
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
                Block block = Block.getBlockFromItem(item);

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
	public ItemStack getStackInSlotOnClosing(int index)
	{
		if (this.getStackInSlot(index) != null)
		{
			ItemStack itemstack = this.getStackInSlot(index);
			this.setInventorySlotContents(index,  null);
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

	@Override
	public void clear() {
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

	//ewww
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return this.burn_time;
		case 1:
			return this.burn_time_max;
		case 2:
			return this.cook_time;
		case 3:
			return this.initial_cook_time;
		default:
			return 0;
		}
	}
	public void setField(int id, int value)
	{    
		switch (id)
		{
		case 0:
			this.burn_time= value;
			break;
		case 1:
			this.burn_time_max = value;
			break;
		case 2:
			this.cook_time = value;
			break;
		}
	};

	public int getFieldCount(){return 4;};


}
