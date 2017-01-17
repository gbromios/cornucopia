package com.gb.cornucopia.veggie;

import java.util.ArrayList;
import java.util.Random;

import com.gb.cornucopia.InvModel;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockVeggieCrop extends BlockBush implements IGrowable
{
	public static final int MAX_AGE = 7;
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

	private ItemVeggieSeed seed;
	private ItemVeggieRaw raw;

	public final String name;

	public BlockVeggieCrop(final String name){
		super();
		this.name = "veggie_" + name + "_crop";
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(null);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		this.setHardness(0.0F);

		GameRegistry.registerBlock(this, this.name);
		InvModel.add(this, this.name);
	}

	public void setDrops(final ItemVeggieRaw raw, final ItemVeggieSeed seed) {
		this.seed = seed;
		this.raw = raw;
	}

	@Override
	public boolean canBlockStay(final World world, final BlockPos pos, final IBlockState state)
	{
		// possibility there may be veggies subclasses that use a different
		// "soil" block in the future, but i think we just need farmland.
		return world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND;
	}


	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AGE });
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer)state.getValue(AGE));
	}
	
    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return ColorizerGrass.getGrassColor(0.5D, 1.0D);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(IBlockState state)
    {
        return this.getBlockColor();
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass)
    {
        return BiomeColorHelper.getGrassColorAtPos(world, pos);
    }

	@Override
	public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand){
		if (this.canGrow(world, pos, state, true) && this.shouldGrow(world, pos, rand)){
			this.grow(world, rand, pos, state);
		}
		super.updateTick(world, pos, state, rand);
	}

	@Override
	public java.util.List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune)
	{
		// TODO: make drops better. this works for now
		java.util.List<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this.seed));

		if ((Integer)state.getValue(AGE) == MAX_AGE){
			// after three grows, veggie is ready to harvest (might spawn a bonus seed)
			ret.add(new ItemStack(this.raw));
			if (RANDOM.nextInt(3) == 0) {
				ret.add(new ItemStack(this.seed));
			}

		}

		return ret;
	}

	protected boolean shouldGrow(final World world, final BlockPos pos, final Random rand){
		float g = 0.8F; // 80% initial chance to grow
		// this is essentially where I'd add biome/crop rotation mechanics 
		// either a straight up manual mapping or temp/elevation/rainfall based
		
		// every neighboring plant cuts the chance in half
		if (world.getBlockState(pos.add(-1, 0, -1)).getBlock() == this) { g /= 2F; }
		if (world.getBlockState(pos.add(1, 0, -1)).getBlock() == this) { g /= 2F; }
		if (world.getBlockState(pos.add(-1, 0, 1)).getBlock() == this) { g /= 2F; }
		if (world.getBlockState(pos.add(1, 0, 1)).getBlock() == this) { g /= 2F; }
		
		return rand.nextFloat() < g;	
	}

	@Override
	public boolean canGrow(final World world, final BlockPos pos, final IBlockState state, final boolean isClient) {
		if (world.getLightFromNeighbors(pos.up()) < 9 ){ return false; }
		return ((Integer)state.getValue(AGE)).intValue() < MAX_AGE; 
	}

	@Override
	public boolean canUseBonemeal(final World world, final Random rand, final BlockPos pos, final IBlockState state) { return true; }

	@Override
	public void grow(final World world, final Random rand, final BlockPos pos, final IBlockState state) {
		world.setBlockState(
				pos,
				state.withProperty(
						AGE,
						Integer.valueOf(java.lang.Math.min(((Integer)state.getValue(AGE)) + 1, MAX_AGE))
						),
				2
				);
	}




}
