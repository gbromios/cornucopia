package com.gb.cornucopia.cheese;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Cheese {
	public static BlockCheeseAged cheese_wheel_aged;
	public static void preInit(){
		cheese_wheel_aged = new BlockCheeseAged();
	}
	

	
}
