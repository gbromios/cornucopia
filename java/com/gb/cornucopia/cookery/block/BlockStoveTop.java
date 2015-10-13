package com.gb.cornucopia.cookery.block;

import java.util.Random;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
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

public class BlockStoveTop extends Block{
	public final String name;
	// facing enum takes 3 bits. I NEED THOSE BITS.
	public static final PropertyBool NS = PropertyBool.create("ns");

	public BlockStoveTop()
	{
		super(Material.iron);
		this.name = "cookery_stovetop";
		this.setUnlocalizedName(this.name);
		this.setHardness(1.5F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NS, true));
		this.setBlockBounds(0.1875F, 0F, 0.1875F, 0.8125F, 0.0625F, 0.8125F);
		GameRegistry.registerBlock(this, this.name);
	}
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	@Override
	public boolean isFullCube(){
		return false;
	}
	
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
    	// this block never exists except on top of a stove
        return false; 
    }

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		// the player can place	a pan/pot/griddle/whatver here
		return true;
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
	}

	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(NS, (meta & 1) == 1);
	}

	public int getMetaFromState(IBlockState state)
	{
		return (boolean)state.getValue(NS) ? 1 : 0; 
	}

	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {NS});
	}

}