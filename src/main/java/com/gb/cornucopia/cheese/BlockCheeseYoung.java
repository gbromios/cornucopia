package com.gb.cornucopia.cheese;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class BlockCheeseYoung extends Block {
	protected static final AxisAlignedBB CHEESE_AABB = new AxisAlignedBB(0.0625F, 0, 0.0625F, 0.9375F, 0.5F, 0.9375F);
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);

	public final String name = "cheese_wheel_young";

	public BlockCheeseYoung() {
		super(Material.CAKE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setCreativeTab(CornuCopia.tabCuisine);
		this.setTickRandomly(true);
		GameRegistry.register(this);
		InvModel.add(this, this.name);
	}


	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos) {
		return world.isSideSolid(pos.down(), EnumFacing.UP, true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CHEESE_AABB;
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState().withProperty(AGE, meta);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{AGE});
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
	public int getMetaFromState(final IBlockState state) {
		return (Integer) state.getValue(AGE);
	}

}
