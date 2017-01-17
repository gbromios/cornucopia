package com.gb.cornucopia.cookery.brewing;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityBarrel extends TileEntity implements ITickable{
	private long born; // just incase wilson is still up in 2038
	private int ticks = 0;

	public TileEntityBarrel(){
		this.born = System.currentTimeMillis();

	}

	@Override
	public void readFromNBT(final NBTTagCompound compound)
	{
		//System.out.println("read from nbt: " + compound.toString());
		super.readFromNBT(compound);
		this.born = compound.getLong("born");

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
		compound.setLong("born", this.born);
	}

	// actually want the stupidass behavior for this one
	//public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
	//return (oldState.getBlock() != newState.getBlock());
	//}


	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound nbtTagCompound = new NBTTagCompound();
		this.writeToNBT(nbtTagCompound);
		final int metadata = getBlockMetadata();
		SPacketUpdateTileEntity pkt = new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
		return pkt;

	}

	@Override
	public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
		//System.out.println("got data pkt: " + pkt.toString());
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void update() {
		if (this.hasWorldObj() && !this.worldObj.isRemote) {
			if ( this.ticks-- > 0 ) {
				return; // long timescales so don't need to check every frame.
			}
			this.ticks = 100;
			
			final IBlockState state = this.worldObj.getBlockState(this.getPos());  
			final int age = (int)state.getValue(BlockBarrel.AGE); 

			
			final long s = System.currentTimeMillis();
			final long t = s - this.born;
		//System.out.format("@%s born %d ;; %d ago ---- %d\n", this.pos, this.born, t, s);
			
			// barrel doesn't age after max_age;
			if (age >= ((BlockBarrel)state.getBlock()).last_age){
				return;
			}


			if ( ((BlockBarrel)state.getBlock()).fermented(t) ) {
				this.worldObj.setBlockState(this.pos, state.withProperty(BlockBarrel.AGE, age + 1));
			}
			
			
		}
	}
}


