package com.gb.cornucopia.veggie.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockVeggieCropTall extends BlockVeggieCrop {
	public final BlockVeggieStalk stalk;
	public final boolean blocking;


	public BlockVeggieCropTall(final String name) {
		this(name, false);
	}


	public BlockVeggieCropTall(final String name, final boolean blocking) {
		super(name);
		this.blocking = blocking;
		stalk = new BlockVeggieStalk(name, this);
	}

	@Override
	public boolean canBlockStay(final World world, final BlockPos pos, final IBlockState state)
	{
		return super.canBlockStay(world, pos, state) || world.getBlockState(pos.down()).getBlock() == this.stalk;
	}

	@Override
	public void grow(final World world, final Random rand, final BlockPos pos, final IBlockState state) {
		super.grow(world, rand, pos, state);
		final IBlockState new_state = world.getBlockState(pos); // get our updated state

		// make sure the the block is still there after growing.
		if (new_state.getBlock() != this){
			return;
		}

		// if the veggie is on top of a stalk, then dont do anything different.
		// TODO: if you want to make taller veggies, here's where you'd do `if ( this.stalk.height() >= MAX_HEIGHT  )`
		if (world.getBlockState(pos.down()).getBlock() == this.stalk){
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
