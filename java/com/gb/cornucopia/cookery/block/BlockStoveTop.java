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
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
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
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// inventory 

public class BlockStoveTop extends Block implements ITileEntityProvider{
	public final String name;

	public static final PropertyBool FACING = PropertyBool.create("facing"); // calculated using stove block below
	public static final PropertyEnum VESSEL = PropertyEnum.create("vessel", Vessel.class);

	public BlockStoveTop()
	{
		super(Material.iron);
		this.name = "cookery_stovetop";
		this.setUnlocalizedName(this.name);
		this.setHardness(0.5F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, true).withProperty(VESSEL, Vessel.NONE));
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

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		// this block never exists except on top of a stove. also how the fuck did you get this in your inventory
		return false; 
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos){
		if ((boolean)(world.getBlockState(pos).getValue(FACING))) {
			this.setBlockBounds(0.25F, 0F, 0.1875F, 0.75F, 0.0625F, 0.8125F);    		
		}
		else {
			this.setBlockBounds(0.1875F, 0F, 0.25F, 0.8125F, 0.0625F, 0.75F);
		}    	
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float x, float y, float z)
	{
		if (world.isRemote)
		{
			return true;
		}

		else if (state.getValue(VESSEL) == Vessel.NONE) {
			if(player.getHeldItem() == null) { return true; } // lol maybe burn your hands?
			Vessel v = Vessel.fromItem(player.getHeldItem().getItem());
			// if the held item is associated with any vessel, place that vessel
			if (v != Vessel.NONE) {
				world.setBlockState(pos, state.withProperty(VESSEL, v));

				// this should be okay, since all cookware stacks to one
				player.destroyCurrentEquippedItem();
			}
		}
		// if there's a vessel in place, 
		else {
			player.openGui(CornuCopia.instance, ((Vessel)state.getValue(VESSEL)).meta, world, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;

	}

	public int guiID(IBlockState state) {
		// this is the vessel meta (i.e. index) not the block meta!
		return ((Vessel)state.getValue(VESSEL)).meta;
	}

	public void dropItemAt(World world, BlockPos pos, Vessel v) {
		if (v == Vessel.NONE){ return; }
		world.spawnEntityInWorld(
				new EntityItem(
						world,
						pos.getX() + 0.5D,
						pos.getY() + 1.125D,
						pos.getZ() + 0.5D,
						new ItemStack(v.getItem())
						)
				)
		; 
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock){
		if (world.getBlockState(pos.down()).getBlock() != Cookery.stove){
			dropItemAt(world, pos, (Vessel)state.getValue(VESSEL));
			world.setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
	{
		// "breaking" the stovetop reverts it to an empty grate and drops the associated item.
		// to get rid of it the stove below needs to be broken.
		dropItemAt(world, pos, (Vessel)state.getValue(VESSEL));
		world.setBlockState(pos, state.withProperty(VESSEL, Vessel.NONE));
	}
	
	// destroy/hit effects: don't show the particles please
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, net.minecraft.client.particle.EffectRenderer effectRenderer)
    {
        return true;
    }
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, net.minecraft.client.particle.EffectRenderer effectRenderer)
    {
        return true;
    }

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VESSEL, Vessel.values()[meta & 7]);
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
		return ((Vessel)state.getValue(VESSEL)).meta; 
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {VESSEL, FACING});
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new TileEntityStove();
	}
}