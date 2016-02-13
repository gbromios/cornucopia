package com.gb.cornucopia.cookery.mill;

import java.util.List;

import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMillTop extends Block {
	public static final PropertyInteger PROGRESS = PropertyInteger.create("progress", 0, 3);
	//private static final String TileEntityMill = null;
	public final String name = "cookery_milltop";

	public BlockMillTop()
	{
		super(Material.wood);
		this.setUnlocalizedName(this.name);
		this.setHardness(5F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PROGRESS, 0));
		this.setBlockBounds(
				0.125F, -0.5F, 0.125F,
				0.875F, 0.125F, 0.875F
				);
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
		if (!world.isRemote){	
			TileEntity mill = world.getTileEntity(pos.down());
			if (mill instanceof TileEntityMill && ((TileEntityMill) mill).mill()) {
				world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "dig.gravel", 0.3f, 0.9f);
				final Integer progress = ((Integer)state.getValue(PROGRESS) + 1) % 4;
				world.setBlockState(pos, state.withProperty(PROGRESS, progress));
				world.setBlockState(pos.down(), world.getBlockState(pos.down()).withProperty(PROGRESS, progress));
				world.markBlockForUpdate(pos.down());
			}
			
		}
		
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos)
	{
		return false; 
	}

	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROGRESS, meta & 15);

	}

	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {PROGRESS});
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return (Integer)state.getValue(PROGRESS);
	}
	
	@Override
	public void onNeighborBlockChange(final World world, final BlockPos pos, final IBlockState state, final Block neighborBlock){
		// if our underlying mill goes away, so do we
		if (world.getBlockState(pos.down()).getBlock() != Cookery.mill){
			world.setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(final World world, final BlockPos pos, final IBlockState state)
	{
		//world.setBlockState(pos, state);
	}

	@Override
	public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
		final List<ItemStack> drop_stacks = new java.util.ArrayList<ItemStack>();
		return drop_stacks;

	}

}

