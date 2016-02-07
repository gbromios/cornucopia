package com.gb.cornucopia.cookery.block;

import java.util.List;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
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
	private static final String TileEntityPresser = null;
	public final String name = "cookery_pressertop";

	public BlockPresserTop()
	{
		super(Material.wood);
		this.setUnlocalizedName(this.name);
		this.setHardness(1.5F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PROGRESS, 0));
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//final IBlockState upState = world.getBlockState(pos.up()); 
		//return Cookery.stovetop.onBlockActivated(world, pos.up(), upState, playerIn, side, hitX, hitY, hitZ);
		final Integer progress = (Integer)state.getValue(PROGRESS);
		if (progress == 7) {
			return false;
		}
		if (progress == 6) {
			// nice!
			((TileEntityPresser)world.getTileEntity(pos.down())).press();
		}
		world.setBlockState(pos, state.withProperty(PROGRESS, progress + 1));
		world.markBlockForUpdate(pos.down());
		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos){
		//final IBlockState state = world.getBlockState(pos.down());
		//if (state.getBlock() != Cookery.presser) { return; }
		this.setBlockBounds(0.25F, 0F, 0.25F, 0.75F, 0.125F, 0.75F);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return false; 
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROGRESS, meta & 7);

	}

	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {FACING, PROGRESS});
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		// derive facing from stove block below
		return state.withProperty(FACING, world.getBlockState(pos.down()).getValue(FACING));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer)state.getValue(PROGRESS);
	}
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock){
		if (world.getBlockState(pos.down()).getBlock() != Cookery.presser){
			world.setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state){}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> drop_stacks = new java.util.ArrayList<ItemStack>();
		drop_stacks.add(new ItemStack(Cookery.presser));
		return drop_stacks;

	}

}

