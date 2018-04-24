package com.gb.cornucopia.cheese;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cuisine.Cuisine;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockCheeseAged extends Block {
	protected static final AxisAlignedBB CHEESE_AABB = new AxisAlignedBB(0.0625F, 0, 0.0625F, 0.9375F, 0.5F, 0.9375F);
	public static final PropertyInteger TAKEN = PropertyInteger.create("taken", 0, 7);

	public final String name = "cheese_wheel_aged";

	public BlockCheeseAged() {
		super(Material.CAKE);

		this.setDefaultState(this.blockState.getBaseState().withProperty(TAKEN, 0));
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setCreativeTab(CornuCopia.tabCuisine);
		InvModel.add(this);
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
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return world.isSideSolid(pos.down(), EnumFacing.UP, true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CHEESE_AABB;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TAKEN, meta & 7);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TAKEN);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TAKEN);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		final List<ItemStack> drops = new ArrayList<>();
		if (state.getValue(TAKEN) == 0) {
			drops.add(new ItemStack(this));
		} else {
			drops.add(new ItemStack(Cuisine.aged_cheese, 8 - state.getValue(TAKEN)));
		}

		return drops;

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}

		final int taken = state.getValue(TAKEN) + 1;
		if (taken < 8) {
			worldIn.setBlockState(pos, state.withProperty(TAKEN, taken));
		} else {
			worldIn.setBlockToAir(pos);
		}
		worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(Cuisine.aged_cheese)));
		return true;
	}

}
