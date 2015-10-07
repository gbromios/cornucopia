package com.gb.cornucopia.fruit.block;

import java.util.Random;

import com.gb.cornucopia.HarvestCraft;
import com.jcraft.jorbis.Block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockSaplingFruit extends BlockBush implements IPlantable, IGrowable{
	
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 1);
	public final String name;
	private IBlockState wood;
	private IBlockState leaf;
	
	public BlockSaplingFruit(String name){
		super();
		this.name = "fruit_sapling_" + name;	
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(HarvestCraft.tabSaplingFruit);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		GameRegistry.registerBlock(this, this.name);
	}
	
	public void setTreeStates(IBlockState wood, IBlockState leaf){
		this.wood = wood;
		this.leaf = leaf;
	}
	
	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand){
		for (int x=-1; x<=1; x++){
			for (int z=-1; z<=1; z++){
				worldIn.setBlockState(pos.add(x, 2, z), this.leaf);
				if (z == 0 || x == 0 || rand.nextInt(3) != 0)
				{
					worldIn.setBlockState(pos.add(x, 3, z), this.leaf);
				}
			}			
		}
		for (int y=0; y < 3; y++) {
			worldIn.setBlockState(pos.add(0, y, 0), this.wood);
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

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Plains; // ugh, later.
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		return this.getDefaultState();
	}
	
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
    		if (this.canGrow(world, pos, state, true) && this.shouldGrow(world, pos, rand)){
    			this.grow(world, rand, pos, state);
    		}
    }
    
	protected boolean shouldGrow(World world, BlockPos pos, Random rand){
		float g = 0.1F;
		float r = rand.nextFloat();
		return r < g;	
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		if (world.getLightFromNeighbors(pos.up()) < 9 ){ return false; }
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
		return this.canGrow(world, pos, state, true) && this.shouldGrow(world, pos, rand);
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		System.out.format("eyyy lmaey %d\n", (Integer)state.getValue(AGE));
			switch ((Integer)state.getValue(AGE)){
				case 0:
					worldIn.setBlockState(
							pos,
							state.withProperty(
									AGE,
									Integer.valueOf(1)
									),
							2
							);
					break;
				case 1:
						System.out.println("boosh");
						this.generateTree(worldIn, pos, state, rand);
					break;
			}
	}
	
}
