package com.gb.cornucopia.cookery.cuttingboard;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.block.Block;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCuttingBoard extends Block {
	protected static final AxisAlignedBB[] BOARD_AABB = new AxisAlignedBB[]{
			new AxisAlignedBB(0.0625F, 0.0F, 0.125F, 0.9375F, 0.0625F, 0.875F),
			new AxisAlignedBB(0.125F, 0.0F, 0.0625F, 0.875F, 0.0625F, 0.9375F)
	};

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public final String name;

	public BlockCuttingBoard() {
		super(Material.WOOD);
		this.name = "cookery_cutting_board";
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(0.4F);
		GameRegistry.register(this);
		InvModel.add(this, this.name);
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, EnumHand hand, ItemStack stack, final EnumFacing side, final float x, final float y, final float z) {
		if (!world.isRemote) {
			player.openGui(CornuCopia.instance, 420, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;

	}

	@Override
	public void neighborChanged(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn) {
		if (!this.canBlockStay(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	protected boolean canBlockStay(final IBlockAccess world, final BlockPos pos) {
		return world.isSideSolid(pos.down(), EnumFacing.UP, true);
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos) {
		return world.isSideSolid(pos.down(), EnumFacing.UP, true);
	}


	@Override
	public IBlockState onBlockPlaced(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		final EnumFacing facing = state.getValue(FACING);
		return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
				? BOARD_AABB[0]
				: BOARD_AABB[1];
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		// must be horizontal
		final EnumFacing facing = EnumFacing.getFront(meta).getAxis() == EnumFacing.Axis.Y
				? EnumFacing.NORTH
				: EnumFacing.getFront(meta);
		;
		return getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}

}
