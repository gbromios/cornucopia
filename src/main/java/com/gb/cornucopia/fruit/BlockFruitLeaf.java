package com.gb.cornucopia.fruit;

import com.gb.cornucopia.InvModel;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockFruitLeaf extends BlockLeaves {
	public final String name;
	private BlockFruitCrop crop;
	public static final PropertyBool SAPLING_GENERATOR = PropertyBool.create("sapling_generator");

	public BlockFruitLeaf(String name) {
		super();
		this.name = String.format("fruit_%s_leaf", name);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setCreativeTab(null);
		this.setDefaultState(
				this.blockState.getBaseState()
						.withProperty(CHECK_DECAY, Boolean.TRUE)
						.withProperty(DECAYABLE, Boolean.TRUE)
						.withProperty(SAPLING_GENERATOR, Boolean.FALSE) // other leaves use the first two bits as VARIANT
		);
		InvModel.add(this, "minecraft");
	}

	public BlockPlanks.EnumType getWoodType(int meta) {
		return EnumType.OAK;
	}

	public void setGrows(BlockFruitCrop crop) {
		this.crop = crop;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		// this does all the brutal leaf decay code
		super.updateTick(world, pos, state, rand);
		// super call might destroy the block so make sure it's still there afterwards
		if (world.getBlockState(pos).getBlock() != this) {
			return;
		}

		// fruits spawn in open air below a leaf only
		if (!world.isAirBlock(pos.down())) {
			return;
		}

		// watch for crowding
		int max_neighbors = 1;
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if ((world.getBlockState(pos.add(x, -1, z)).getBlock() == this.crop) && --max_neighbors < 1) {
					return;
				}
			}
		}
		if (rand.nextInt(64) == 0) {
			world.setBlockState(pos, state.withProperty(SAPLING_GENERATOR, true));
		}

		world.setBlockState(pos.down(), this.crop.getDefaultState().withProperty(
				BlockFruitCrop.DROP_SAPLING,
				state.getValue(SAPLING_GENERATOR) && rand.nextInt(4) == 0
		));

	}

	// 1 2 4 8
	//   ^     - when this leaf spawns a fruit: will the fruit drop a sapling?
	//     ^ ^ - no fucking idea, BlockLeaf uses these to check for decay. 4 seems to work in reverse but i just copied BlockNewLeaf for those...
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
				.withProperty(SAPLING_GENERATOR, (meta & 2) == 2)
				.withProperty(DECAYABLE, (meta & 4) == 0)
				.withProperty(CHECK_DECAY, (meta & 8) == 8)
				;
	}

	public int getMetaFromState(IBlockState state) {
		return (state.getValue(SAPLING_GENERATOR) ? 2 : 0)
				| (state.getValue(DECAYABLE) ? 0 : 4)
				| (state.getValue(CHECK_DECAY) ? 8 : 0)
				;

	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE, SAPLING_GENERATOR);
	}

	//region MOJANG PLS
	// fruit trees don't drop shit when you break em
	//private void destroy(World world, BlockPos pos)
	//{
	//world.setBlockToAir(pos);
	//}

	public int quantityDropped(Random random) {
		return 0;
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.SAPLING);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
		return;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return new ArrayList<>();
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return new ArrayList<>();
	}
}