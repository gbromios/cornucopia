package com.gb.cornucopia.veggie.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockVeggieCropTall extends BlockVeggieCrop {
	public final BlockVeggieStalk stalk;
	public final boolean blocking;
	
	
	public BlockVeggieCropTall(String name) {
		this(name, false);
	}

	
	public BlockVeggieCropTall(String name, boolean blocking) {
		super(name, 7);
		this.blocking = blocking;
		stalk = new BlockVeggieStalk(name, this);
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
		
		// make sure the the block is still there after growing.
		if (new_state.getBlock() != this){
			return;
		}

		// if the veggie is on top of a stalk, then dont do anything different.
		// TODO: if you want to make taller veggies, here's where you'd do `if ( this.stalk.height() >= MAX_HEIGHT  )`
		if (world.getBlockState(pos.down()).getBlock() == this.stalk){
			//if ((Integer)state.getValue(AGE) < (Integer)new_state.getValue(AGE)){
			//world.setBlockState(pos.down(), this.stalk.getDefaultState().withProperty(BlockStalkTallVeggie.AGE, 1));
			
			// stalk's age is veggie's age - 4
			// for growing taller than one, will probably want to shift this to onNeighborChange
			world.setBlockState(pos.down(), this.stalk.getDefaultState().withProperty(
					BlockVeggieStalk.AGE, (Integer)new_state.getValue(AGE) - 4
					));
			return;
		}
		
		// for a crop on the ground, hitting age 4 (i.e. the get taller age)  
		if ((Integer)new_state.getValue(AGE) == 4){
			if (world.isAirBlock(pos.up())) {
				world.setBlockState(pos.up(), new_state);
				world.setBlockState(pos, this.stalk.getDefaultState());
			}
			// if the block above is not air, roll this veggie back to its starting age >:C
			else {
				world.setBlockState(pos, state);
			}
		} 
	}
	
}
