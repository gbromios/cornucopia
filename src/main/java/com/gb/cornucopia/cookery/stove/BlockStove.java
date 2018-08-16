package com.gb.cornucopia.cookery.stove;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.GuiHandler;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cookery.Vessel;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Random;

public class BlockStove extends Block implements ITileEntityProvider {
	public final String name;
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool ON = PropertyBool.create("on");

	public BlockStove() {
		super(Material.IRON);
		this.name = "cookery_stove";
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setHardness(1.5F);
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ON, false).withProperty(FACING, EnumFacing.NORTH));
		GameRegistry.registerTileEntity(TileEntityStove.class, "cookery_stove_entity");
		InvModel.add(this);
	}

	@Override
	@Deprecated
	public int getLightValue(IBlockState state){
		return state.getValue(ON) ? 8 : 0;
	}

	private static final PropertyEnum VESSEL = PropertyEnum.create("vessel", Vessel.class);

	public static Vessel getVessel(World world, BlockPos pos) {
		if (world.getBlockState(pos.up()).getBlock() == Cookery.stovetop) {
			return (Vessel) world.getBlockState(pos.up()).getValue(VESSEL);
		} else {
			// sometimes there is no stove top block there. cest la vie!
			return Vessel.NONE;
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote || world.getBlockState(pos.up()).getBlock() != Cookery.stovetop) {
			return true; }

		if (BlockStove.getVessel(world, pos) == Vessel.NONE) {
			final Vessel insertable_vessel = (playerIn.getHeldItem(hand) == ItemStack.EMPTY) ? Vessel.NONE : Vessel.fromItem(playerIn.getHeldItem(hand).getItem());
			// if the held item is associated with any vessel, place that vessel
			if (insertable_vessel != Vessel.NONE) {
				world.setBlockState(pos.up(), world.getBlockState(pos.up()).withProperty(BlockStoveTop.VESSEL, insertable_vessel));
				// this should be okay, since all cookware stacks to one
				playerIn.setHeldItem(hand, ItemStack.EMPTY);
				return true;
			}
		}
		// if there's a vessel already in place, open the GUI
		playerIn.openGui(CornuCopia.instance, GuiHandler.STOVE, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos) {
		return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
	}


	public IBlockState onBlockPlaced(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ON, false);
	}

	public void onBlockPlacedBy(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ON, false), 2);
		world.setBlockState(pos.up(), Cookery.stovetop.getDefaultState().withProperty(BlockStoveTop.FACING, placer.getHorizontalFacing().getOpposite()));
	}

	public void breakBlock(final World world, final BlockPos pos, final IBlockState state) {

		TileEntity stove = world.getTileEntity(pos);
		IItemHandler itemHandler= stove.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

		if (stove instanceof TileEntityStove) {
			Vessel v = BlockStove.getVessel(world, pos);
			if (v != Vessel.NONE) {
				world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(v.getItem())));
				world.setBlockToAir(pos.up());
			}
			for (int i = 0; i < 8; i++) {
				if(!itemHandler.getStackInSlot(i).isEmpty()){
					EntityItem droppedItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), itemHandler.getStackInSlot(i));
					world.spawnEntity(droppedItem);
				}
			}
		}
		if (world.getBlockState(pos.up()).getBlock() == Cookery.stovetop) {
			world.setBlockToAir(pos.up());
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if ((boolean) stateIn.getValue(ON)) {
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D + 0.3D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = rand.nextDouble() * 0.6D - 0.3D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);

		}
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState()
				.withProperty(FACING, EnumFacing.getHorizontal(meta & 3))
				.withProperty(ON, (meta & 4) == 4)
				;
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return (
				(EnumFacing) state.getValue(FACING)).getHorizontalIndex()
				| (((boolean) state.getValue(ON)) ? 4 : 0
		);
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{ON, FACING});
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int meta) {
		return new TileEntityStove();
	}

}