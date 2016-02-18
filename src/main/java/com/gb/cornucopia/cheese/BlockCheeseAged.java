package com.gb.cornucopia.cheese;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCheeseAged extends Block {
	public static final PropertyInteger TAKEN = PropertyInteger.create("taken", 0, 3);
	
	public final String name = "cheese_wheel_aged";

	public BlockCheeseAged() {
		super(Material.cake);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(TAKEN, 0));
		
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabCuisine);
		GameRegistry.registerBlock(this, this.name);
		InvModel.add(this, this.name);
	}
	
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	@Override
	public boolean isFullCube(){
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(TAKEN, meta & 3);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {TAKEN});
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return (Integer)state.getValue(TAKEN);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		world.setBlockState(pos, state.withProperty(TAKEN, (((Integer)state.getValue(TAKEN)) + 1) % 4 ));
		return true;
	}

}
