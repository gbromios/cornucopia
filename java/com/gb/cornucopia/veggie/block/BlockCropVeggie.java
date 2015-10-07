package com.gb.cornucopia.veggie.block;

import java.util.ArrayList;
import java.util.Random;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.veggie.item.ItemRawVeggie;
import com.gb.cornucopia.veggie.item.ItemSeedVeggie;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCropVeggie extends BlockBush implements IGrowable
{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

	private ItemSeedVeggie seed;
	private ItemRawVeggie raw;
	private BlockWildVeggie wild;
	
	public final String name;

	public BlockCropVeggie(String name){
		super();
		this.name = "veggie_crop_" + name;
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabCropVeg);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		this.setHardness(0.0F);
		this.setStepSound(soundTypeGrass);
		
		GameRegistry.registerBlock(this, this.name);
	}

	public void setDrops(ItemRawVeggie raw, ItemSeedVeggie seed, BlockWildVeggie wild) {
		this.seed = seed;
		this.raw = raw;
		this.wild = wild;
	}
	
	@Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
    {
		// possibility there may be veggies subclasses that use a different
		// "soil" block in the future, but i think we just need farmland.
		return world.getBlockState(pos.down()).getBlock() == Blocks.farmland;
    }


	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { AGE });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer)state.getValue(AGE));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if (this.canGrow(world, pos, state, true) && this.shouldGrow(world, pos, rand)){
			this.grow(world, rand, pos, state);
		}
		super.updateTick(world, pos, state, rand);
	}

	@Override
	public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		// TODO: make drops better. this works for now
		java.util.List<ItemStack> ret = new ArrayList<ItemStack>();
		
		if ((Integer)state.getValue(AGE) == 7){
			// after three grows, veggie is ready to harvest
			ret.add(new ItemStack(this.raw));
			
			if (RANDOM.nextInt(500) == 0){
				// very tiny chance to drop the wild veggie...!4
				ret.add(new ItemStack(this.wild));
			}
		}
		else {
			ret.add(new ItemStack(this.seed));
		}
		
		return ret;
	}
	
	protected boolean shouldGrow(World world, BlockPos pos, Random rand){
		float g = 0.06F; // initial small chance to grow
		// this is essentially where I'd add biome mechanics. 
		// either a straight up manual mapping or temp/elevation/rainfall based		
		return rand.nextFloat() < g;	
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		if (world.getLightFromNeighbors(pos.up()) < 9 ){ return false; }
		return ((Integer)state.getValue(AGE)).intValue() < 7; 
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) { return true; }

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
			world.setBlockState(
					pos,
					state.withProperty(
							AGE,
							Integer.valueOf(java.lang.Math.min(((Integer)state.getValue(AGE)) + 1, 7))
							),
					2
					);
	}




}
