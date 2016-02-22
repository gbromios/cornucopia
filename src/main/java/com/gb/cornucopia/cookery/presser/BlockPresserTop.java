package com.gb.cornucopia.cookery.presser;

import java.util.List;

import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockPresserTop extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL); // TODO get this from the meta above
	public static final PropertyInteger PROGRESS = PropertyInteger.create("progress", 0, 7);
	//private static final String TileEntityPresser = null;
	public final String name = "cookery_pressertop";

	public BlockPresserTop()
	{
		super(Material.iron);
		this.setHardness(10f);
		this.setUnlocalizedName(this.name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PROGRESS, 0));
		this.setBlockUnbreakable();
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
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ)
	{
		final Integer progress = (Integer)state.getValue(PROGRESS);
		final TileEntityPresser presser = (TileEntityPresser)world.getTileEntity(pos.down());
		
		if (progress == 7 || !presser.canPress()) {
			//return false;
			return true;
		}
		if (progress == 6) {
			// trigger the pressing
			presser.press();
		}
		world.setBlockState(pos, state.withProperty(PROGRESS, progress + 1));
		world.markBlockForUpdate(pos.down());
		return true;
	}


	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess world, final BlockPos pos){
		final IBlockState state = world.getBlockState(pos);
		//if (state.getBlock() != Cookery.presser) { return; }
		final float yMin = 0F;
		final float yMax = 0.8125F - ( 0.125F * (Integer)(state.getValue(PROGRESS)) / 2 );
		this.setBlockBounds(
				0.25F, yMin, 0.25F,
				0.75F, yMax, 0.75F);
	}
	
	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos)
	{
		return false; 
	}

	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROGRESS, meta & 7);

	}

	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {FACING, PROGRESS});
	}

	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess world, final BlockPos pos)
	{
		// derive facing from stove block below
		if (world.getBlockState(pos.down()) == Cookery.presser) {
			return state.withProperty(FACING, world.getBlockState(pos.down()).getValue(FACING));
		} else {
			return state;
		}
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return (Integer)state.getValue(PROGRESS);
	}
	
	@Override
	public void onNeighborBlockChange(final World world, final BlockPos pos, final IBlockState state, final Block neighborBlock){
		if (world.getBlockState(pos.down()).getBlock() != Cookery.presser){
			world.setBlockToAir(pos);
		}
	}
	
	@Override
	public void onBlockDestroyedByPlayer(final World world, final BlockPos pos, final IBlockState state)
	{
		// this can happen when removing the block in creative mode.
		if (world.getBlockState(pos.down()).getBlock() != Cookery.presser) {
			world.setBlockToAir(pos); 
		}

	}

	@Override
	public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
		return new java.util.ArrayList<ItemStack>();
	}

}

