package com.gb.cornucopia.veggie;

import com.gb.cornucopia.InvModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockVeggieCrop extends BlockCrops {
	private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.1125D, 0.9D), new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.225D, 0.9D), new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.3375D, 0.9D), new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.45D, 0.9D), new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.5625D, 0.9D), new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.675D, 0.9D), new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.7875D, 0.9D), new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D)};
	private ItemVeggieSeed seed;
	private ItemVeggieRaw raw;
	public final String name;

	public BlockVeggieCrop(final String name) {
		super();
		this.name = String.format("veggie_%s_crop", name);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setCreativeTab(null);
		InvModel.add(this);
	}

	public void setDrops(final ItemVeggieRaw raw, final ItemVeggieSeed seed) {
		this.seed = seed;
		this.raw = raw;
	}

	@Override
	protected Item getSeed() {
		return seed;
	}

	@Override
	protected Item getCrop() {
		return raw;
	}

	@SideOnly(Side.CLIENT) // TODO find out what replaced this (or if it's necessary!)
	public int getBlockColor() {
		return ColorizerGrass.getGrassColor(0.5D, 1.0D);
	}

	@SideOnly(Side.CLIENT) // TODO find out what replaced this (or if it's necessary!)
	public int getRenderColor(IBlockState state) {
		return this.getBlockColor();
	}

	@SideOnly(Side.CLIENT) // TODO find out what replaced this (or if it's necessary!)
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
		return BiomeColorHelper.getGrassColorAtPos(world, pos);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (this.canGrow(world, pos, state, true) && this.shouldGrow(world, pos, rand)) {
			this.grow(world, rand, pos, state);
		}
		super.updateTick(world, pos, state, rand);
	}

	@Override
	public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		// TODO: make drops better. this works for now
		java.util.List<ItemStack> ret = new ArrayList<>();
		ret.add(new ItemStack(this.seed));

		if (state.getValue(AGE) == getMaxAge()) {
			// after three grows, veggie is ready to harvest (might spawn a bonus seed)
			ret.add(new ItemStack(this.raw));
			if (RANDOM.nextInt(3) == 0) {
				ret.add(new ItemStack(this.seed));
			}
		}
		return ret;
	}

	public IBlockColor getCropColor() {
		return (state, worldIn, pos, tintIndex) -> tintIndex == 0
				? (worldIn != null && pos != null
				? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos)
				: ColorizerFoliage.getFoliageColorBasic())
				: -1;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CROPS_AABB[(Integer) state.getValue(this.getAgeProperty())];
	}

	private boolean shouldGrow(World world, BlockPos pos, Random rand) {
		float chance = 0.8F;
		for (Block block : getSurroundingBlocks(world, pos.down())) {
			if (block == this) chance /= 2F;
		}
		return rand.nextFloat() < chance;
	}

	private List<Block> getSurroundingBlocks(World world, BlockPos pos) {
		List<Block> blocks = new ArrayList<Block>();
		blocks.add(world.getBlockState(pos.north()).getBlock());
		blocks.add(world.getBlockState(pos.east()).getBlock());
		blocks.add(world.getBlockState(pos.south()).getBlock());
		blocks.add(world.getBlockState(pos.west()).getBlock());
		return blocks;
	}
}
