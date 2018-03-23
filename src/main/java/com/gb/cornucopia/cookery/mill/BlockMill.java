package com.gb.cornucopia.cookery.mill;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMill extends Block implements ITileEntityProvider {
	protected static final AxisAlignedBB MILL_AABB = new AxisAlignedBB(0F, 0F, 0F, 1F, 0.75F, 1F);
	public static final PropertyInteger PROGRESS = PropertyInteger.create("progress", 0, 3);
	public final String name = "cookery_mill";

	public BlockMill() {
		super(Material.WOOD);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setHardness(1.5F);
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PROGRESS, 0));
		GameRegistry.register(this);
		GameRegistry.registerTileEntity(TileEntityMill.class, "cookery_mill_entity");
		InvModel.add(this);
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, EnumHand hand, ItemStack stack, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (!world.isRemote) {
			player.openGui(CornuCopia.instance, 420, world, pos.getX(), pos.getY(), pos.getZ());
			//world.setBlockState(pos, state.withProperty(PROGRESS, ((int)state.getValue(PROGRESS) + 1) % 16 ));
		}


		return true;
	}

	@Override
	public void onBlockPlacedBy(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
		world.setBlockState(pos.up(), Cookery.milltop.getDefaultState());
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos) {
		return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return MILL_AABB;
	}

	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState().withProperty(PROGRESS, meta);
	}

	public int getMetaFromState(final IBlockState state) {
		return (int) state.getValue(PROGRESS);
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{PROGRESS});
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
		if (worldIn.getBlockState(pos.up()).getBlock() != Cookery.milltop) {
			// breaking the handle
			this.dropBlockAsItem(worldIn, pos, state, 0);
			this.breakBlock(worldIn, pos, state);
			worldIn.setBlockToAir(pos);
		}
	}

	public void breakBlock(final World world, final BlockPos pos, final IBlockState state) {
		//System.out.println("break presser");

		TileEntity mill = world.getTileEntity(pos);

		if (mill instanceof TileEntityMill) {
			InventoryHelper.dropInventoryItems(world, pos, (TileEntityMill) mill);
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMill();
	}
}
