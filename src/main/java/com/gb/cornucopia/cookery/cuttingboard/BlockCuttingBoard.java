package com.gb.cornucopia.cookery.cuttingboard;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockCuttingBoard extends Block {
	protected static final AxisAlignedBB[] BOARD_AABB = new AxisAlignedBB[]{
			new AxisAlignedBB(0.0625F, 0.0F, 0.125F, 0.9375F, 0.0625F, 0.875F),
			new AxisAlignedBB(0.125F, 0.0F, 0.0625F, 0.875F, 0.0625F, 0.9375F)
	};

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public final String name;

	public BlockCuttingBoard() {
		super(Material.WOOD);
		this.name = "cookery_cutting_board";
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(0.4F);
		InvModel.add(this);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(CornuCopia.instance, 420, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;

	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		if (!this.canBlockStay(world, pos)) {
			this.dropBlockAsItem((World) world, pos, world.getBlockState(pos), 0);
			((World) world).setBlockToAir(pos);
		}
	}

	protected boolean canBlockStay(IBlockAccess world, BlockPos pos) {
		return world.isSideSolid(pos.down(), EnumFacing.UP, true);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return world.isSideSolid(pos.down(), EnumFacing.UP, true);
	}

	@Override
	@Nonnull
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return (state.getValue(FACING).getAxis() == EnumFacing.Axis.X) ? BOARD_AABB[0] : BOARD_AABB[1];
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		// must be horizontal
		final EnumFacing facing = EnumFacing.getFront(meta).getAxis() == EnumFacing.Axis.Y
				? EnumFacing.NORTH
				: EnumFacing.getFront(meta);
		;
		return getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}

}
