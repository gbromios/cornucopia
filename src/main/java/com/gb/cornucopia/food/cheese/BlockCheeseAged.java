package com.gb.cornucopia.food.cheese;

import java.util.ArrayList;
import java.util.List;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.food.cuisine.Cuisine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCheeseAged extends Block {
	public static final PropertyInteger TAKEN = PropertyInteger.create("taken", 0, 7);

	public final String name = "cheese_wheel_aged";

	public BlockCheeseAged() {
		super(Material.CAKE);

		this.setDefaultState(this.blockState.getBaseState().withProperty(TAKEN, 0));
		this.setBlockBounds(0.0625F, 0, 0.0625F, 0.9375F, 0.5F,  0.9375F);
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabCuisine);
		GameRegistry.registerBlock(this, this.name);
		InvModel.add(this, this.name);
	}


	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	@Override
	public boolean isFullCube(){
		return false;
	}
	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos)
	{
		return world.isSideSolid(pos.down(), EnumFacing.UP, true);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(TAKEN, meta & 7);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {TAKEN});
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return (Integer)state.getValue(TAKEN);
	}
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		final List<ItemStack> drops = new ArrayList<>();
		if ((int)state.getValue(TAKEN) == 0) {
			drops.add(new ItemStack(this));
		}
		else {
			drops.add(new ItemStack(Cuisine.aged_cheese, 8 - (int)state.getValue(TAKEN)));
		}

		return drops;

	};



	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {return true;}

		final int taken = (int)state.getValue(TAKEN) + 1;
		if (taken < 8) {
			world.setBlockState(pos, state.withProperty(TAKEN, taken));
		} else {
			world.setBlockToAir(pos);
		}
		world.spawnEntityInWorld(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(Cuisine.aged_cheese)));
		return true;
	}

}
