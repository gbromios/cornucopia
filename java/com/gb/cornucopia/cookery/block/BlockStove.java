package com.gb.cornucopia.cookery.block;

import java.util.Collection;
import java.util.Random;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Console;

public class BlockStove extends Block{
	public final String name;
	// facing enum takes 3 bits. I NEED THOSE BITS.
	public static final PropertyEnum FACING = PropertyEnum.create("facing", EnumFacing.class, EnumFacing.Plane.HORIZONTAL);
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

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			return true;
		}
		// the player can place	a pan/pot/griddle/whatver here
		if ((boolean)(world.getBlockState(pos).getValue(ON))){
			world.setBlockState(pos, state.withProperty(ON, false));
		}
		else{
			world.setBlockState(pos, state.withProperty(ON, true));
		}
		return true;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos){
		return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
	}


    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ON, false);
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ON, false), 2);
        worldIn.setBlockState(pos.up(), Cookery.stovetop.getDefaultState().withProperty(Cookery.stovetop.NS, (placer.getHorizontalFacing().getHorizontalIndex() == 0 || placer.getHorizontalFacing().getHorizontalIndex() == 2)));
    }


	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityFurnace)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFurnace)tileentity);
		}

		super.breakBlock(worldIn, pos, state);
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