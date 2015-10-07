package com.gb.cornucopia.veggie.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockCropTallVeggie extends BlockCropVeggie {
	public final BlockStalkTallVeggie stalk;
	public final boolean blocking;
	
	
	public BlockCropTallVeggie(String name) {
		this(name, false);
	}
	
	public BlockCropTallVeggie(String name, boolean blocking) {
		super(name);
		this.blocking = blocking;
		stalk = new BlockStalkTallVeggie(name, this);
	}
	
	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{
		return super.canBlockStay(world, pos, state) || world.getBlockState(pos.down()).getBlock() == this.stalk;
	}

	
	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		super.grow(world, rand, pos, state);
		IBlockState new_state = world.getBlockState(pos); // get our updated state

		// if the veggie is on top of a stalk, then dont do anything different.
		// TODO: if you want to make taller veggies, here's where you'd do `if ( this.stalk.height() >= MAX_HEIGHT  )`
		if (world.getBlockState(pos.down()).getBlock() == this.stalk){
			if ((Integer)state.getValue(AGE) == 2  && (Integer)new_state.getValue(AGE) == 3){
				// if we grew to age = 3, age the stalk below
				world.setBlockState(pos.down(), this.stalk.getDefaultState().withProperty(BlockStalkTallVeggie.AGE, 1));
			} 
			return;
		}
		
		// for a crop on the ground, hitting age 2  
		if ((Integer)new_state.getValue(AGE) == 2){
			if (world.isAirBlock(pos.up())) {
				// turn the block into a stalk and make the air above into a veggie of age = 1
				world.setBlockState(pos.up(), state.withProperty(AGE, 1));
				world.setBlockState(pos, this.stalk.getDefaultState());
			}
			// if the block above is not air, roll this veggie back to age = 1 >:C
			else {
				world.setBlockState(pos, state.withProperty(AGE, 1));
			}
		} 
	}
	
}
