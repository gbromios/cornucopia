package com.gb.cornucopia.cookery.presser;

import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockPresserTop extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL); // TODO get this from the meta above
	public static final PropertyInteger PROGRESS = PropertyInteger.create("progress", 0, 7);
	//private static final String TileEntityPresser = null;
	public final String name = "cookery_pressertop";

	public BlockPresserTop() {
		super(Material.IRON);
		this.setHardness(10f);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PROGRESS, 0));
		this.setBlockUnbreakable();
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		final Integer progress = (Integer) state.getValue(PROGRESS);
		final TileEntityPresser presser = (TileEntityPresser) worldIn.getTileEntity(pos.down());

		if (progress == 7 || !presser.canPress()) {
			//return false;
			return true;
		}
		if (progress == 6) {
			// trigger the pressing
			presser.press();
		}
		worldIn.setBlockState(pos, state.withProperty(PROGRESS, progress + 1));
		worldIn.notifyBlockUpdate(pos.down(), state, getDefaultState(), 3);
		return true;
	}


	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, final BlockPos pos) {
		//if (state.getBlock() != Cookery.presser) { return; }
		final float yMin = 0F;
		final float yMax = 0.8125F - (0.125F * (Integer) (state.getValue(PROGRESS)) / 2);
		return new AxisAlignedBB(
				0.25F, yMin, 0.25F,
				0.75F, yMax, 0.75F);
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos) {
		return false;
	}

	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState().withProperty(PROGRESS, meta & 7);

	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING, PROGRESS});
	}

	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
		// derive facing from stove block below
		if (world.getBlockState(pos.down()) == Cookery.presser) {
			return state.withProperty(FACING, world.getBlockState(pos.down()).getValue(FACING));
		} else {
			return state;
		}
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return (Integer) state.getValue(PROGRESS);
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		if (world.getBlockState(pos.down()).getBlock() != Cookery.presser) {
			((World) world).setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(final World world, final BlockPos pos, final IBlockState state) {
		// this can happen when removing the block in creative mode.
		if (world.getBlockState(pos.down()).getBlock() != Cookery.presser) {
			world.setBlockToAir(pos);
		}

	}

	@Override
	public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
		return new java.util.ArrayList<ItemStack>();
	}

}

