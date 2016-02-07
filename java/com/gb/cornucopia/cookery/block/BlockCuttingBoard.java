package com.gb.cornucopia.cookery.block;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCuttingBoard extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public final String name;
    public BlockCuttingBoard() {
		super(Material.wood);
		this.name = "cookery_cutting_board";
		this.setCreativeTab(CornuCopia.tabCookeryBlock);
		this.setUnlocalizedName(this.name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(0.4F);

		GameRegistry.registerBlock(this, this.name);
		InvModel.add(this, this.name);
	}
    
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float x, float y, float z)
	{
		if (!world.isRemote) {
			player.openGui(CornuCopia.instance, 420, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;

	}
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (!this.canBlockStay(world, pos))
		{
			this.dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}

		super.onNeighborBlockChange(world, pos, state, neighborBlock);
	}

	protected boolean canBlockStay(World world, BlockPos pos)
	{
		return world.isSideSolid(pos.down(), EnumFacing.UP, true);
	}

    @Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
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
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos){

    	EnumFacing f = (EnumFacing)world.getBlockState(pos).getValue(FACING);
    	if (f == EnumFacing.NORTH || f == EnumFacing.SOUTH) {
    		this.setBlockBounds(0.0625F, 0.0F, 0.125F, 0.9375F, 0.0625F, 0.875F); // figure out a better place to do this...
    	}
    	else {
    		this.setBlockBounds( 0.125F, 0.0F, 0.0625F, 0.875F, 0.0625F, 0.9375F);
    	}
    	
    }
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return getDefaultState().withProperty(FACING, enumfacing);
	}
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {FACING});
	}

}
