package com.gb.cornucopia.fruit;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFruitSapling extends BlockSapling {
	public final String name;
	private IBlockState wood;
	private IBlockState leaf;

	public BlockFruitSapling(String name) {
//		super();
		this.name = String.format("fruit_%s_sapling", name);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(TYPE, BlockPlanks.EnumType.OAK)
				.withProperty(STAGE, Integer.valueOf(0)));
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setCreativeTab(CornuCopia.tabFruit);
		InvModel.add(this);
	}

	public void setTreeStates(BlockPlanks.EnumType wood_type, IBlockState leaf) {
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

	@Override
	public void generateTree(World world, BlockPos pos, IBlockState state, Random rand) {
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

	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (this.canGrow(world, pos, state, true) && this.shouldGrow(world, pos, rand)) {
			this.grow(world, rand, pos, state);
		}
	}

	protected boolean shouldGrow(World world, BlockPos pos, Random rand) {
		float g = 0.1F;
		float r = rand.nextFloat();
		return r < g;
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		// saplings need light and 3x3 empty space above them
		if (world.getLightFromNeighbors(pos.up()) < 9) {
			return false;
		}
		for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
			for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
				for (int y = pos.getY() + 1; y <= pos.getY() + 3; y++) {
					BlockPos p = new BlockPos(x, y, z);
					if (!world.getBlockState(p).getBlock().isReplaceable(world, p)) {
						return false;
					}
				}
			}
		}
		return true;
	}
}