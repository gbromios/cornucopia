package com.gb.cornucopia.fruit;

import com.gb.cornucopia.InvModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockFruitCrop extends BlockBush implements IGrowable {
	protected static final AxisAlignedBB CROP_AABB = new AxisAlignedBB(0.3F, 0.3F, 0.3F, 0.7F, 0.95F, 0.7F);
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
	public static final PropertyBool DROP_SAPLING = PropertyBool.create("drop_sapling");
	public final String name;

	private BlockFruitLeaf leaf; // so we know where it's okay to chill.
	private ItemFruitRaw raw;
	private BlockFruitSapling sapling;

	public BlockFruitCrop(String name) {
		super();
		this.name = String.format("fruit_%s_crop", name);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setCreativeTab(null);
		InvModel.add(this);
	}

	public BlockFruitCrop setLeaf(BlockFruitLeaf leaf) {
		this.leaf = leaf;
		return this;
	}

	public BlockFruitCrop setDrops(ItemFruitRaw raw, BlockFruitSapling sapling) {
		this.raw = raw;
		this.sapling = sapling;
		return this;
	}

	//private void breakBlock(){}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		return (Integer) state.getValue(AGE) <= 3;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
		// bonemeal makes fruit not drop saplings, but that had to be handled in onBlockActivate
		return (int) state.getValue(AGE) < 3;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ) {
		// not your normal bonemeal activatation...
		if (!world.isRemote && EnumDyeColor.byDyeDamage(stack.getItemDamage()) == EnumDyeColor.WHITE) {
			world.setBlockState(pos, state.withProperty(DROP_SAPLING, false));
		}
		return false;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (rand.nextInt(32) == 0 && this.canGrow(world, pos, state, true)) { //1/8 chance to grow on tick
			this.grow(world, rand, pos, state);
		}
	}

	@Override
	public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		final List<ItemStack> ret = new ArrayList<ItemStack>();
		if ((Boolean) state.getValue(DROP_SAPLING)) {
			ret.add(new ItemStack(this.sapling));
		}
		if ((Integer) state.getValue(AGE) == 3) {

			ret.add(new ItemStack(this.raw));
			ret.add(new ItemStack(this.raw));
		}
		return ret;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		world.setBlockState(
				pos,
				state
						.withProperty(AGE, Integer.valueOf(java.lang.Math.min(((Integer) state.getValue(AGE)) + 1, 3))
						)
						.withProperty(DROP_SAPLING, state.getValue(DROP_SAPLING)
						),
				2
		);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CROP_AABB;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		if (!(worldIn.getBlockState(pos.up()).getBlock() instanceof BlockLeaves)) {
			if ((Integer) state.getValue(AGE) == 3) {

				spawnAsEntity(worldIn, pos, new ItemStack(this.raw));
				spawnAsEntity(worldIn, pos, new ItemStack(this.raw));
			}
			if ((Boolean) state.getValue(DROP_SAPLING)) {
				spawnAsEntity(worldIn, pos, new ItemStack(this.sapling));
			}
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{AGE, DROP_SAPLING});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		// 1 2 4 8
		// ^ ^     - age of the growing fruit 0-3
		//     ^   - whether the fruit should drop a sapling,
		return getDefaultState().withProperty(AGE, meta & 3).withProperty(DROP_SAPLING, (meta & 4) == 4);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(AGE) | ((Boolean) state.getValue(DROP_SAPLING) ? 4 : 0));
	}
}