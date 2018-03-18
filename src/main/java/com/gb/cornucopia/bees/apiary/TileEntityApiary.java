package com.gb.cornucopia.bees.apiary;

import java.util.ArrayList;
import java.util.Random;

import com.gb.cornucopia.bees.Bees;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDoublePlant.EnumPlantType;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityApiary extends TileEntity implements ITickable, IInventory {
	public static final int TICK_PERIOD = 20 * 30 ; //tick 2x per minute, because it might be kind of expensive >__>
	public static final Random RANDOM = new Random();
	private final ItemStack[] contents = new ItemStack[9]; // 0 = queen slot, 1 = worker slot, 2-8 are the honeycombs
	private int ticks = 0;
	private ArrayList<IBlockState> flower_survey = new ArrayList<>();
	private int flower_score = 0;

	//System.out.format("\n");


	@Override
	public void update() {
		if (!this.hasWorldObj()) return;
		final World world = this.getWorld();
		if (world.isRemote) return;

		if (this.ticks++ < TICK_PERIOD){ return; }

		// time to actually do the tick!
		this.ticks = 0;
		//System.out.format("\n   ===   TICKING!   ===\n");

		// no bees???? nothing I can do
		if (this.contents[1] == null){ return; }


		flowerSurvey();
		// hungry bees? no soup for u
		if (this.feedBees()) {
			produce();
			cloneFlower();
		}

		this.markDirty();

	}

	private void produce(){
		// 75%~ chance to produce with max bees and max flowers!
		final int fs = Math.min(this.flower_score, 32);
		if (fs == 0) {return;} // need at least one flower!
		int grow_chance = (this.beeCount() + fs) / (1 + (this.getNeighboringApiaries() * 2));
		//System.out.format(" @ produce bee gifts (%d / 128)\n ~ ( %d bees + %d flowers ) / (%d neighbors * 2)\n", grow_chance, this.beeCount(), fs, this.getNeighboringApiaries());
		if (RANDOM.nextInt(128) < grow_chance){
			// look for empties first:
			for (int i=2; i<9; i++){
				if (this.contents[i] == null) {
					this.contents[i] = (new ItemStack(Bees.waxcomb));
					//System.out.format("    make a wax\n");
					return;
				}
			}

			// then look for plain wax:
			for (int i=2; i<9; i++){
				if (this.contents[i].getItem() == Bees.waxcomb) {
					this.contents[i].setItem(Bees.honeycomb);
					//System.out.format("    make a honey comb\n");
					return;
				}
			}

			// once the hive is full of honey, 
			// if there's a queen in the queen slot and slot 6 is not already jelly
			// AND you've got a royal flower AAAND you're really lucky...
			if (
					this.contents[6].getItem() != Bees.royal_jelly
					&& this.beeCount() == 64
					&& this.nearRoyalBloom()
					&& RANDOM.nextInt(64) == 0)
			{									
				// can happen without a queen, but it's super rare!
				if (this.hasQueen() || RANDOM.nextInt(32) == 0) {
					// royal jelly comes at a heavy price though. it uses up most of the honey, and the bee population will suffer
					for (int i : new int[]{2,3,4,5,7,8}) { // outer comb slots
						this.contents[i] = null;

					}
					this.contents[6].setItem(Bees.royal_jelly);
					this.contents[1].stackSize = 8;

					return;
				}
			}
		}
	}

	private int getNeighboringApiaries() {
		// remember to leave 4 blocks of space between your apiaries!
		int n = 0;
		for (int x = -4; x < 5; x++){
			for (int z = -4; z < 5; z++){
				for (int y = -1; y < 2; y++){
					if (this.getWorld().getBlockState(this.pos.add(x, y, z)).getBlock() == Bees.apiary){
						n++;
					}
				}
			}			
		}
		return n-1;
	}


	private boolean feedBees(){
		final int food = this.flower_score - (this.beeCount() / 4);
		//System.out.format("%d food\n", food);

		final ItemStack workers = this.contents[1];

		if (food < -4){
			if (--workers.stackSize < 8){
				workers.stackSize = 8;
			}
			return false;
		}
		else if (food > 4 && this.hasQueen() && RANDOM.nextInt((12 - Math.min(food, 11)) * (1 + (2 * this.getNeighboringApiaries()) )) == 0){
			//System.out.format("   bees grew! :)\n");
			workers.stackSize = Math.min(workers.stackSize + 1, workers.getMaxStackSize()); 
		}
		return true;
	}

	private boolean nearRoyalBloom(){
		for (int x = -2; x < 3; x++){
			for (int z = -2; z < 3; z++){
				if (this.getWorld().getBlockState(this.pos.add(x, 0, z)).getBlock() == Bees.royal_bloom){
					return true;
				}
			}			
		} 
		return false;
	}

	private void cloneFlower(){
		// pick a random block near the apiary.
		final BlockPos fpos = this.pos.add(RANDOM.nextInt(19) - 9, RANDOM.nextInt(3) - 1, RANDOM.nextInt(19) - 9);

		double d = fpos.distanceSq(pos.getX(), pos.getY(), pos.getZ()) + 10.0;

		double rs = RANDOM.nextDouble() * d;
		//System.out.format("%s => %s : (%s / %s)\n",pos, fpos, rs, d);
		if (rs < 2.0) {

			if ((this.worldObj.isAirBlock(fpos) || this.worldObj.getBlockState(fpos).getBlock() == Blocks.TALLGRASS) && this.worldObj.getBlockState(fpos.down()).getBlock() == Blocks.GRASS && RANDOM.nextInt(80) < this.beeCount()){
				// stop growing if there are 48 flowers in the vicinity (don't grow at all if there's no flowwers :()
				if (this.flower_score > 48 || this.flower_survey.size() < 1 ){ return; }
				IBlockState b = this.flower_survey.get(RANDOM.nextInt(this.flower_survey.size()));
				//public void placeAt(World worldIn, BlockPos lowerPos, BlockDoublePlant.EnumPlantType variant, int flags)

				this.worldObj.setBlockState(fpos, b, 0);
				if (b.getBlock() instanceof BlockDoublePlant) {
					Blocks.DOUBLE_PLANT.placeAt(worldObj, fpos, (EnumPlantType)b.getValue(BlockDoublePlant.VARIANT), 2);
				}
				this.worldObj.markBlockForUpdate(fpos);
			}

			// royal flower!
			final BlockPos rpos = this.pos.add(RANDOM.nextInt(5) - 2, 0, RANDOM.nextInt(5) - 2);
			if (this.beeCount() == 64 && this.flower_score > 16 && !this.nearRoyalBloom() && RANDOM.nextInt(this.hasQueen() ? 512 : 1024) == 0){
				if((this.worldObj.isAirBlock(rpos) || this.worldObj.getBlockState(rpos).getBlock() == Blocks.TALLGRASS || this.worldObj.getBlockState(rpos).getBlock() instanceof BlockFlower) && this.worldObj.getBlockState(rpos.down()).getBlock() == Blocks.GRASS){
					this.worldObj.setBlockState(rpos, Bees.royal_bloom.getDefaultState(), 0);
					this.worldObj.markBlockForUpdate(rpos);				
				}
			}
		}
	}

	private void flowerSurvey(){
		this.flower_survey.clear();
		this.flower_score = this.flowerSample(this.pos, 7, true);
	}

	private int flowerSample(final BlockPos pos, final int r, final boolean record_states){
		// sample a radius * radius area around the apiary N times, return the number of flowers found
		int density = 0;
		for (int z = -r; z <= r; z++){
			for (int x = -r; x <= r; x++){
				for (int y = -1; y <= 1; y++) {
					// pollenate-able?
					BlockPos fpos = pos.add(x, y, z);
					IBlockState bs = this.getWorld().getBlockState(fpos);
					if (bs.getBlock() instanceof BlockFlower || bs.getBlock() instanceof BlockDoublePlant){
						density++;
						// this list could potentially slow the server down, searching a 15x15 area...
						if (record_states){
							this.flower_survey.add(bs);
						}
						// stop counting after a while?
					}
				}

			}			
		}		
		return density;
	}

	public boolean hasQueen(){
		// theoretically slot permissions means we don't need to check the item
		return contents[0] != null;
	}

	public int beeCount(){
		return contents[1] == null ? 0 : contents[1].stackSize;
	}

	// for writing to nbt
	public int[] combSlots(){
		final int[] a = new int[7];

		for (int i = 2; i < 9; i++) {
			// 0 == empty
			if (contents[i] == null){
				a[i-2] = 0;
				continue;
			}

			Item comb = contents[i].getItem();
			if(comb == Bees.waxcomb) {
				a[i-2] = 1;
			}
			else if(comb == Bees.honeycomb) {
				a[i-2] = 2;
			}
			else if(comb == Bees.royal_jelly) {
				a[i-2] = 3;
			}
			else { // if something else hid here... gtfo!
				a[i-2] = 0;				
			}

		}

		return a;
	}

	@Override
	public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void writeToNBT(final NBTTagCompound parentNBTTagCompound)
	{
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save the tiles location
		parentNBTTagCompound.setBoolean("hasQueen", this.hasQueen() );
		parentNBTTagCompound.setInteger("beeCount", this.beeCount() );
		parentNBTTagCompound.setIntArray("combSlots", this.combSlots());
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(final NBTTagCompound parentNBTTagCompound)
	{
		super.readFromNBT(parentNBTTagCompound); // The super call is required to load the tiles location

		final boolean hasQueen = parentNBTTagCompound.getBoolean("hasQueen");
		final int beeCount = parentNBTTagCompound.getInteger("beeCount");
		final int[] combSlots =  parentNBTTagCompound.getIntArray("combSlots");

		// queen bee
		if (hasQueen){
			this.contents[0] = new ItemStack(Bees.queen);
		}
		else{
			this.contents[0] = null;
		}

		// worker bees
		if (beeCount > 0){
			this.contents[1] = new ItemStack(Bees.bee, Math.min(beeCount, 64));
		}
		else{
			this.contents[1] = null;
		}

		// honeycomb slots
		for (int i = 2; i < 9; i++){
			switch(combSlots[i-2]){
			case 1:
				this.contents[i] = new ItemStack(Bees.waxcomb);
				break;
			case 2:
				this.contents[i] = new ItemStack(Bees.honeycomb);
				break;
			case 3:
				this.contents[i] = new ItemStack(Bees.royal_jelly);
				break;
			case 0: default:
				this.contents[i] = null;
			}
		}
	}

	@Override
	public String getName() { return "apiary"; }

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public ITextComponent getDisplayName() { return new TextComponentString("apiary"); }

	@Override
	public int getSizeInventory() {	return 9; }

	@Override
	public int getInventoryStackLimit() {return 64;}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {	
		return player.getDistanceSq(this.pos) < 6;
	}

	@Override
	public ItemStack getStackInSlot(final int index) {
		// 0 - queen
		// 1 - workers
		// 2-8 - honeycomb output slots
		return contents[index];
	}

	@Override
	public ItemStack decrStackSize(final int index, final int count) { 
		if (this.contents[index] != null)
		{
			final ItemStack itemstack = this.contents[index].splitStack(Math.min(count, this.contents[index].stackSize));
			if (this.contents[index].stackSize == 0)
			{
				this.contents[index] = null;
			}
			this.markDirty();
			return itemstack;
		}
		return null;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
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
	public boolean isItemValidForSlot(final int index, final ItemStack stack) {
		// afaict this doesn't do shit???
		return false;
	}

	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer player)
	{
		return new ContainerApiary(playerInventory, this, this.worldObj, this.pos);
	}

	@Override
	public int getField(final int id) {
		return 0;
	}

	@Override
	public void setField(final int id, final int value) {

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

	@Override
	public ItemStack removeStackFromSlot(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
