package com.gb.cornucopia.cookery.block;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockCuttingBoard extends BlockCookingTable {
    public BlockCuttingBoard() {
		super("cutting_board");
	}
    
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos){

    	EnumFacing f = (EnumFacing)world.getBlockState(pos).getValue(FACING);
    	if (f == EnumFacing.NORTH || f == EnumFacing.SOUTH) {
    		this.setBlockBounds(0.0625F, 0.0F, 0.125F, 0.9375F, 0.0625F, 0.875F); // figure out a better place to do this...
    	}
    	else {
    		this.setBlockBounds( 0.125F, 0.0F, 0.0625F, 0.875F, 0.0625F, 0.9375F);
    	}
    	
    }
}
