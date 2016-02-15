package com.gb.cornucopia.brewing;

import java.io.Console;

import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityBarrel extends TileEntity implements IUpdatePlayerListBox{
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
		S35PacketUpdateTileEntity pkt = new S35PacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
		return pkt;

	}

	@Override
	public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
		//System.out.println("got data pkt: " + pkt.toString());
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void update() {
		if (this.hasWorldObj() && !this.worldObj.isRemote) {
			if ( this.ticks++ < 50 ) {
				return; // long timescales so don't need to check every frame.
			}
			this.ticks = 0;
			
			final IBlockState state = this.worldObj.getBlockState(this.getPos());  
			final int age = (int)state.getValue(BlockBarrel.AGE); 

			
			final long s = System.currentTimeMillis();
			final long t = s - this.born;
			System.out.format("@%s born %d ;; %d ago ---- %d\n", this.pos, this.born, t, s);
			
			// barrel doesn't age after max_age;
			if (age >= ((BlockBarrel)state.getBlock()).last_age){
				return;
			}


			if (t > ((BlockBarrel)state.getBlock()).fermentation_time) {
				this.worldObj.setBlockState(this.pos, state.withProperty(BlockBarrel.AGE, age + 1));
			}
			
			
		}
	}
}


