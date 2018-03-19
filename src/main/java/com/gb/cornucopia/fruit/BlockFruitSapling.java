package com.gb.cornucopia.fruit;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class BlockFruitSapling extends BlockBush implements IPlantable, IGrowable {

	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 1);
	public final String name;
	private IBlockState wood;
	private IBlockState leaf;

	public BlockFruitSapling(final String name) {
		super();
		this.name = "fruit_" + name + "_sapling";
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabFruit);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		GameRegistry.registerBlock(this, this.name);
		InvModel.add(this, this.name);
	}

	public void setTreeStates(final BlockPlanks.EnumType wood_type, final IBlockState leaf) {
		switch (wood_type) {
			case OAK:
			case SPRUCE:
			case BIRCH:
			case JUNGLE:
				this.wood = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, wood_type);
				break;
			case ACACIA:
			case DARK_OAK:
				this.wood = Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, wood_type);
				break;
		}

		this.leaf = leaf;
	}

	public void generateTree(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				world.setBlockState(pos.add(x, 2, z), this.leaf);
				if (z == 0 || x == 0 || rand.nextInt(3) != 0) {
					world.setBlockState(pos.add(x, 3, z), this.leaf);
				}
			}
		}
		for (int y = 0; y < 3; y++) {
			world.setBlockState(pos.add(0, y, 0), this.wood);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{AGE});
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(AGE));
	}

	@Override
	public EnumPlantType getPlantType(final IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Plains; // ugh, later.
	}

	@Override
	public IBlockState getPlant(final IBlockAccess world, final BlockPos pos) {
		return this.getDefaultState();
	}

	public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
		if (this.canGrow(world, pos, state, true) && this.shouldGrow(world, pos, rand)) {
			this.grow(world, rand, pos, state);
		}
	}

	protected boolean shouldGrow(final World world, final BlockPos pos, final Random rand) {
		final float g = 0.1F;
		final float r = rand.nextFloat();
		return r < g;
	}

	@Override
	public boolean canGrow(final World world, final BlockPos pos, final IBlockState state, final boolean isClient) {
		// saplings need light and 3x3 empty space above them
		if (world.getLightFromNeighbors(pos.up()) < 9) {
			return false;
		}
		for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
			for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
				for (int y = pos.getY() + 1; y <= pos.getY() + 3; y++) {
					final BlockPos p = new BlockPos(x, y, z);
					if (!world.getBlockState(p).getBlock().isReplaceable(world, p)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean canUseBonemeal(final World world, final Random rand, final BlockPos pos, final IBlockState state) {
		return this.canGrow(world, pos, state, true) && this.shouldGrow(world, pos, rand);
	}

	@Override
	public void grow(final World world, final Random rand, final BlockPos pos, final IBlockState state) {
		switch ((Integer) state.getValue(AGE)) {
			case 0:
				world.setBlockState(
						pos,
						state.withProperty(AGE, Integer.valueOf(1)),
						2
				);
				break;
			case 1:
				this.generateTree(world, pos, state, rand);
				break;
		}
	}
}