package com.gb.cornucopia.cookery.presser;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockPresser extends Block implements ITileEntityProvider {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyInteger PROGRESS = PropertyInteger.create("progress", 0, 7); // TODO get this from the meta above
	public final String name = "cookery_presser";

	public BlockPresser() {
		super(Material.WOOD);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setHardness(1.5F);
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PROGRESS, 0));
		GameRegistry.register(this);
		GameRegistry.registerTileEntity(TileEntityPresser.class, String.format("%s_entity", name));
		InvModel.add(this);
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos) {
		return super.canPlaceBlockAt(world, pos); //&& (world.isAirBlock(pos.up()) || world.getBlockState(pos.up()).getBlock() == Cookery.pressertop);
	}

	public IBlockState onBlockPlaced(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PROGRESS, 0);
	}

	public void onBlockPlacedBy(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
		//world.setBlockState(pos, state, 2);
		//super.onBlockPlacedBy(world, pos, state, placer, stack);
		//if (this.canPlaceBlockAt(world, pos)) {
		//	 world.setBlockState(pos.up(), Cookery.pressertop.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		//}

		if (world.isAirBlock(pos.up())) {
			world.setBlockState(pos.up(), Cookery.pressertop.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}

	}

	public void breakBlock(final World world, final BlockPos pos, final IBlockState state) {

		TileEntity presser = world.getTileEntity(pos);

		if (presser instanceof TileEntityPresser) {
			InventoryHelper.dropInventoryItems(world, pos, (TileEntityPresser) presser);
		}

		if (world.getBlockState(pos.up()).getBlock() == Cookery.pressertop) {
			world.setBlockToAir(pos.up());
		}

		super.breakBlock(world, pos, state);
	}


	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final ItemStack stack, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (world.getBlockState(pos.up()).getBlock() == Cookery.pressertop) {
			world.setBlockState(pos.up(), Cookery.pressertop.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(PROGRESS, 0));
			world.notifyBlockUpdate(pos.up(), state, getDefaultState(), 3);
		}
		if (!world.isRemote) {
			player.openGui(CornuCopia.instance, 420, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
	}

	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
		// derive progress from handle above
		final IBlockState pup = world.getBlockState(pos.up());
		return state.withProperty(PROGRESS, pup.getBlock() == Cookery.pressertop ? pup.getValue(PROGRESS) : 0);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING, PROGRESS});
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
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		//System.out.println("...");
		if (worldIn.isAirBlock(pos.up())) {
			worldIn.setBlockState(pos.up(), Cookery.pressertop.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPresser();
	}
}
