package com.gb.cornucopia.bees.block;

import java.util.Arrays;
import java.util.Random;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.bees.crafting.ContainerApiary;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTNT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
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

public class TileEntityApiary extends TileEntity implements IUpdatePlayerListBox, IInventory {
	private final ItemStack[] contents = new ItemStack[9];

	/* for testing r/w
	private final int [] testIntArray = {5, 4, 3, 2, 1};
	private final double [] testDoubleArray = {1, 2, 3, 4, 5, 6};
	private final Double [] testDoubleArrayWithNulls = {61.1, 62.2, null, 64.4, 65.5};
	private final ItemStack testItemStack = new ItemStack(Items.cooked_chicken, 23);
	private final String testString = "supermouse";
	private final BlockPos testBlockPos = new BlockPos(10, 11, 12); 
	*/

	// When the world loads from disk, the server needs to send the TileEntity information to the client
	//  it uses getDescriptionPacket() and onDataPacket() to do this
	//  Not really required for this example since we only use the timer on the client, but included anyway for illustration
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

	// This is where you save any data that you don't want to lose when the tile entity unloads
	// In this case, we only need to store the ticks left until explosion, but we store a bunch of other
	//  data as well to serve as an example.
	// NBTexplorer is a very useful tool to examine the structure of your NBT saved data and make sure it's correct:
	//   http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-tools/1262665-nbtexplorer-nbt-editor-for-windows-and-mac
	@Override
	public void writeToNBT(NBTTagCompound parentNBTTagCompound)
	{

		super.writeToNBT(parentNBTTagCompound); // The super call is required to save the tiles location

		parentNBTTagCompound.setBoolean("hasQueen", false);
		parentNBTTagCompound.setInteger("beeCount", 0);
		parentNBTTagCompound.setIntArray("combSlots", new int[]{0,1,2,3,0,1,2});


		/*
		parentNBTTagCompound.setInteger("ticksLeft", ticksLeftTillDisappear);
		// alternatively - could use parentNBTTagCompound.setTag("ticksLeft", new NBTTagInt(ticksLeftTillDisappear));

		// some examples of other NBT tags - browse NBTTagCompound or search for the subclasses of NBTBase for more examples

		parentNBTTagCompound.setString("testString", testString);

		NBTTagCompound blockPosNBT = new NBTTagCompound();        // NBTTagCompound is similar to a Java HashMap
		blockPosNBT.setInteger("x", testBlockPos.getX());
		blockPosNBT.setInteger("y", testBlockPos.getY());
		blockPosNBT.setInteger("z", testBlockPos.getZ());
		parentNBTTagCompound.setTag("testBlockPos", blockPosNBT);

		NBTTagCompound itemStackNBT = new NBTTagCompound();
		testItemStack.writeToNBT(itemStackNBT);                     // make sure testItemStack is not null first!
		parentNBTTagCompound.setTag("testItemStack", itemStackNBT);

		parentNBTTagCompound.setIntArray("testIntArray", testIntArray);

		NBTTagList doubleArrayNBT = new NBTTagList();                     // an NBTTagList is similar to a Java ArrayList
		for (double value : testDoubleArray) {
			doubleArrayNBT.appendTag(new NBTTagDouble(value));
		}
		parentNBTTagCompound.setTag("testDoubleArray", doubleArrayNBT);

		NBTTagList doubleArrayWithNullsNBT = new NBTTagList();
		for (int i = 0; i < testDoubleArrayWithNulls.length; ++i) {
			Double value = testDoubleArrayWithNulls[i];
			if (value != null) {
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setInteger("i", i+1);   // avoid using 0, so the default when reading a missing value (0) is obviously invalid
				dataForThisSlot.setDouble("v", value);
				doubleArrayWithNullsNBT.appendTag(dataForThisSlot);
			}
		}
		parentNBTTagCompound.setTag("testDoubleArrayWithNulls", doubleArrayWithNullsNBT);
		 */
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.readFromNBT(parentNBTTagCompound); // The super call is required to load the tiles location

		int beeCount = parentNBTTagCompound.getInteger("beeCount");
		boolean hasQueen = parentNBTTagCompound.getBoolean("beeCount");
		int[] combSlots =  parentNBTTagCompound.getIntArray("combSlots");

		System.out.format("%s %s %s\n\n", beeCount, hasQueen, combSlots);

		// important rule: never trust the data you read from NBT, make sure it can't cause a crash
		/*
		final int NBT_INT_ID = 3;					// see NBTBase.createNewByType()
		int readTicks = INVALID_VALUE;
		if (parentNBTTagCompound.hasKey("ticksLeft", NBT_INT_ID)) {  // check if the key exists and is an Int. You can omit this if a default value of 0 is ok.
			readTicks = parentNBTTagCompound.getInteger("ticksLeft");
			if (readTicks < 0) readTicks = INVALID_VALUE;
		}
		ticksLeftTillDisappear = readTicks;

		// some examples of other NBT tags - browse NBTTagCompound or search for the subclasses of NBTBase for more

		String readTestString = null;
		final int NBT_STRING_ID = 8;          // see NBTBase.createNewByType()
		if (parentNBTTagCompound.hasKey("testString", NBT_STRING_ID)) {
			readTestString = parentNBTTagCompound.getString("testString");
		}
		if (!testString.equals(readTestString)) {
			System.err.println("testString mismatch:" + readTestString);
		}

		NBTTagCompound blockPosNBT = parentNBTTagCompound.getCompoundTag("testBlockPos");
		BlockPos readBlockPos = null;
		if (blockPosNBT.hasKey("x", NBT_INT_ID) && blockPosNBT.hasKey("y", NBT_INT_ID) && blockPosNBT.hasKey("z", NBT_INT_ID) ) {
			readBlockPos = new BlockPos(blockPosNBT.getInteger("x"), blockPosNBT.getInteger("y"), blockPosNBT.getInteger("z"));
		}
		if (readBlockPos == null || !testBlockPos.equals(readBlockPos)) {
			System.err.println("testBlockPos mismatch:" + readBlockPos);
		}

		NBTTagCompound itemStackNBT = parentNBTTagCompound.getCompoundTag("testItemStack");
		ItemStack readItemStack = ItemStack.loadItemStackFromNBT(itemStackNBT);
		if (!ItemStack.areItemStacksEqual(testItemStack, readItemStack)) {
			System.err.println("testItemStack mismatch:" + readItemStack);
		}

		int [] readIntArray = parentNBTTagCompound.getIntArray("testIntArray");
		if (!Arrays.equals(testIntArray, readIntArray)) {
			System.err.println("testIntArray mismatch:" + readIntArray);
		}

		final int NBT_DOUBLE_ID = 6;					// see NBTBase.createNewByType()
		NBTTagList doubleArrayNBT = parentNBTTagCompound.getTagList("testDoubleArray", NBT_DOUBLE_ID);
		int numberOfEntries = Math.min(doubleArrayNBT.tagCount(), testDoubleArray.length);
		double [] readDoubleArray = new double[numberOfEntries];
		for (int i = 0; i < numberOfEntries; ++i) {
			 readDoubleArray[i] = doubleArrayNBT.getDouble(i);
		}
		if (doubleArrayNBT.tagCount() != numberOfEntries || !Arrays.equals(readDoubleArray, testDoubleArray)) {
			System.err.println("testDoubleArray mismatch:" + readDoubleArray);
		}

		final int NBT_COMPOUND_ID = 10;					// see NBTBase.createNewByType()
		NBTTagList doubleNullArrayNBT = parentNBTTagCompound.getTagList("testDoubleArrayWithNulls", NBT_COMPOUND_ID);
		numberOfEntries = Math.min(doubleArrayNBT.tagCount(), testDoubleArrayWithNulls.length);
		Double [] readDoubleNullArray = new Double[numberOfEntries];
		for (int i = 0; i < doubleNullArrayNBT.tagCount(); ++i)	{
			NBTTagCompound nbtEntry = doubleNullArrayNBT.getCompoundTagAt(i);
			int idx = nbtEntry.getInteger("i") - 1;
			if (nbtEntry.hasKey("v", NBT_DOUBLE_ID) && idx >= 0 && idx < numberOfEntries) {
				readDoubleNullArray[idx] = nbtEntry.getDouble("v");
			}
		}
		if (!Arrays.equals(testDoubleArrayWithNulls, readDoubleNullArray)) {
			System.err.println("testDoubleArrayWithNulls mismatch:" + readDoubleNullArray);
		}*/
	}

	// Since our TileEntity implements IUpdatePlayerListBox, we get an update method which is called once per tick (20 times / second)
	//    (yes, the name IUpdatePlayerListBox is misleading)
	// When the timer elapses, replace our block with a random one.
	@Override
	public void update() {
		if (!this.hasWorldObj()) return;
		World world = this.getWorld();
		if (world.isRemote) return;   // don't bother doing anything on the client side.
		//		this.markDirty();            // if you update a tileentity variable on the server and this should be communicated to the client

	}

	@Override
	public String getName() { return "apiary"; }

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public IChatComponent getDisplayName() { return new ChatComponentText("apiary"); }

	@Override
	public int getSizeInventory() {	return 9; }

	@Override
	public int getInventoryStackLimit() {return 64;}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {	return true; } // TODO: distance check
	
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

            if (this.contents[index].stackSize <= count)
            {
                itemstack = this.contents[index];
                this.contents[index] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.contents[index].splitStack(count);

                if (this.contents[index].stackSize == 0)
                {
                    this.contents[index] = null;
                }

                this.markDirty();
                return itemstack;
            }
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
        return new ContainerApiary(playerInventory, this, this.worldObj, this.pos);
    }
	
	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {
        for (int i = 0; i < this.contents.length; ++i)
        {
            this.contents[i] = null;
        }
	}

}
