package com.gb.cornucopia.cookery.mill;

import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockMillTop extends Block {
	private static final AxisAlignedBB MILL_AABB = new AxisAlignedBB(0.125F, -0.5F, 0.125F, 0.875F, 0.125F, 0.875F);
	public static final PropertyInteger PROGRESS = PropertyInteger.create("progress", 0, 3);
	//private static final String TileEntityMill = null;
	public final String name = "cookery_milltop";

	public BlockMillTop() {
		super(Material.WOOD);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setHardness(5F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PROGRESS, 0));
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity mill = world.getTileEntity(pos.down());
			if (mill instanceof TileEntityMill && ((TileEntityMill) mill).mill()) {
				world.playSound(playerIn, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 0.3f, 0.9f);
				final Integer progress = ((Integer) state.getValue(PROGRESS) + 1) % 4;
				world.setBlockState(pos, state.withProperty(PROGRESS, progress));
				world.setBlockState(pos.down(), world.getBlockState(pos.down()).withProperty(PROGRESS, progress));
				world.notifyBlockUpdate(pos.down(), state, getDefaultState(), 3);
			}

		}

		return true;
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos) {
		return false;
	}

	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState().withProperty(PROGRESS, meta & 15);

	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{PROGRESS});
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return MILL_AABB;
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return (Integer) state.getValue(PROGRESS);
	}

	@Override
	public void neighborChanged(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn) {
		// if our underlying mill goes away, so do we
		if (worldIn.getBlockState(pos.down()).getBlock() != Cookery.mill) {
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(final World world, final BlockPos pos, final IBlockState state) {
		//world.setBlockState(pos, state);
	}

	@Override
	public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
		final List<ItemStack> drop_stacks = new java.util.ArrayList<ItemStack>();
		return drop_stacks;

	}

}

