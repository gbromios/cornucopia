package com.gb.harvestcraft.fruit.block;

import java.util.ArrayList;
import java.util.Random;

import com.gb.harvestcraft.HarvestCraft;
import com.gb.harvestcraft.fruit.item.ItemRawFruit;

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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCropFruit extends BlockBush implements IGrowable{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);
	public final String name;
	private BlockLeafFruit leaf; // so we know where it's okay to chill.
	private ItemRawFruit raw;
	
	public BlockCropFruit(String name){
		super();
		this.name = "fruit_crop_" + name;
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(HarvestCraft.tabCropFruit);
		
        this.setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.95F, 0.7F);
		
		GameRegistry.registerBlock(this, this.name);
	}
	
	public void setLeaf(BlockLeafFruit leaf){
		this.leaf = leaf;
	}
	public void setDrop(ItemRawFruit raw){
		this.raw = raw;
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) { return (Integer)state.getValue(AGE) <= 2; }

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) { return true; }

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if (this.canGrow(world, pos, state, true) && rand.nextInt(30) == 0) { // lazy right now
			this.grow(world, rand, pos, state);
		}
	}
	
	@Override
	public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		java.util.List<ItemStack> ret = new ArrayList<ItemStack>();
		
		if ((Integer)state.getValue(AGE) == 2){
			ret.add(new ItemStack(this.raw));
			// sometimes drop an extra fruit! yum.
			if (RANDOM.nextInt(4) == 0) { ret.add(new ItemStack(this.raw)); }
		}
		return ret;
	}
	
	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		world.setBlockState(
				pos,
				state.withProperty(
						AGE,
						Integer.valueOf(java.lang.Math.min(((Integer)state.getValue(AGE)) + 1, 2))
						),
				2
				);	
	}

	@Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock){
		if (world.getBlockState(pos).getBlock() != this.leaf){
			if ((Integer)state.getValue(AGE) == 2){
				spawnAsEntity(world, pos, new ItemStack(this.getItem(world, pos)));	
			}
			world.setBlockToAir(pos);
		}
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
}
