package com.gb.cornucopia.cookery.stove;

import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cookery.Vessel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
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

// inventory 

public class BlockStoveTop extends Block {
	public final String name;
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyEnum VESSEL = PropertyEnum.create("vessel", Vessel.class);

	public BlockStoveTop() {
		super(Material.PLANTS);
		this.name = "cookery_stovetop";
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(VESSEL, Vessel.NONE));
		this.setHardness(0.5F);
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
	public boolean canPlaceBlockAt(final World world, final BlockPos pos) {
		// this block never exists except on top of a stove. also how the fuck did you get this in your inventory
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch ((Vessel) state.getValue(VESSEL)) {
			case POT:
				return new AxisAlignedBB(0.25F, 0F, 0.25F, 0.75F, 0.5F, 0.75F);
			case PAN:
				return new AxisAlignedBB(0.25F, 0F, 0.25F, 0.75F, 0.5F, 0.75F);
			case NONE:
			default:
				switch ((EnumFacing) source.getBlockState(pos.down()).getValue(FACING)) {
					case NORTH:
					case SOUTH:
						return new AxisAlignedBB(0.25F, 0F, 0.1875F, 0.75F, 0.0625F, 0.8125F);
					default:
						return new AxisAlignedBB(0.1875F, 0F, 0.25F, 0.8125F, 0.0625F, 0.75F);
				}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		// defer activations to the stove below.
		// not too concerned about the edge case of the wrong block type... those can get right clicked too
		final IBlockState stove_state = worldIn.getBlockState(pos.down());
		return stove_state.getBlock().onBlockActivated(worldIn, pos.down(), stove_state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		if (world.getBlockState(pos.down()).getBlock() != Cookery.stove) {
			this.dropBlockAsItem((World) world, pos, world.getBlockState(pos), 0);
			((World) world).setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(final World world, final BlockPos pos, final IBlockState state) {
		// this can happen when removing the block in creative mode.
		if (world.getBlockState(pos.down()).getBlock() != Cookery.stove) {
			this.dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		} else {
			world.setBlockState(pos, state.withProperty(VESSEL, Vessel.NONE));
		}
	}

	@Override
	public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
		final Vessel v = (Vessel) state.getValue(VESSEL);
		final List<ItemStack> drop_stacks = new java.util.ArrayList<ItemStack>();
		if (v != Vessel.NONE) {
			drop_stacks.add(new ItemStack(v.getItem()));
		}
		return drop_stacks;

	}

	// destroy/hit effects: don't show the particles please
	/*@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(final World world, final BlockPos pos, final net.minecraft.client.particle.EffectRenderer effectRenderer)
	{
		return true;
	}
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(final World worldObj, final MovingObjectPosition target, final net.minecraft.client.particle.EffectRenderer effectRenderer)
	{
		return true;
	}*/

	@Override
	public IBlockState getStateFromMeta(final int meta) {

		return this.getDefaultState().withProperty(VESSEL, Vessel.values()[meta & 7]);
	}

	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
		IBlockState stove = world.getBlockState(pos.down());
		if (stove.getBlock() == Cookery.stove) {
			return state.withProperty(FACING, stove.getValue(FACING));
		} else {
			return state;
		}
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Vessel) state.getValue(VESSEL)).meta;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{VESSEL, FACING});
	}

}