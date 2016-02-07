package com.gb.cornucopia.cookery.block;

import java.util.List;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockStove extends Block{
	public final String name;
	// facing enum takes 3 bits. I NEED THOSE BITS.
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool ON = PropertyBool.create("on");

	public BlockStove()
	{
		super(Material.iron);
		this.name = "cookery_stove";
		this.setUnlocalizedName(this.name);
		this.setHardness(1.5F);
		this.setCreativeTab(CornuCopia.tabCookeryBlock);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ON, true).withProperty(FACING, EnumFacing.NORTH));
		GameRegistry.registerBlock(this, this.name);
		InvModel.add(this, this.name);
	}
	
    public int getLightValue(IBlockAccess world, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        if (block != this)
        {
            return block.getLightValue(world, pos);
        }
        return (boolean)world.getBlockState(pos).getValue(ON) ? 2 : 0;
    }

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		final IBlockState upState = world.getBlockState(pos.up()); 
		return Cookery.stovetop.onBlockActivated(world, pos.up(), upState, playerIn, side, hitX, hitY, hitZ);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos){
		return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
	}


	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ON, false);
	}

	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ON, false), 2);
		world.setBlockState(pos.up(), Cookery.stovetop.getDefaultState().withProperty(Cookery.stovetop.FACING, placer.getHorizontalFacing().getOpposite()));
	}

	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		super.breakBlock(world, pos, state);
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(ON, (meta & 8) == 8).withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
	}
	
	public int getMetaFromState(IBlockState state)
	{
		return ((boolean)state.getValue(ON) ? 8 : 0) | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
	}

	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {ON, FACING});
	}
	


}