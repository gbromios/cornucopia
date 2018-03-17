package com.gb.cornucopia.food.cheese;

import java.util.Random;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCheeseYoung extends Block {

	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);

	public final String name = "cheese_wheel_young";

	public BlockCheeseYoung() {
		super(Material.CAKE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabCuisine);
		this.setTickRandomly(true);
		this.setBlockBounds(0.0625F, 0, 0.0625F, 0.9375F, 0.5F,  0.9375F);
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
	public boolean canPlaceBlockAt(final World world, final BlockPos pos)
	{
		return world.isSideSolid(pos.down(), EnumFacing.UP, true);
	}
	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(AGE, meta);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {AGE});
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		final int age = (int) state.getValue(AGE);
		if (age == 15) {
			world.setBlockState(pos, Cheese.cheese_wheel_aged.getDefaultState());
		}
		if (age < 15 && rand.nextInt(8) == 0) {
			world.setBlockState(pos, state.withProperty(AGE, age + 1));
		}
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return (Integer)state.getValue(AGE);
	}

}
