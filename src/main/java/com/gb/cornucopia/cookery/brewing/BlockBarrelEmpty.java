package com.gb.cornucopia.cookery.brewing;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockBarrelEmpty extends Block {
	public static final PropertyBool UNDER_BARREL = PropertyBool.create("under_barrel");
	public static final PropertyEnum AXIS = PropertyEnum.create("axis", BlockLog.EnumAxis.class);
	public final String name;

	public BlockBarrelEmpty(String name) {
		super(Material.wood);
		// TODO Auto-generated constructor stub
		this.name = "brew_"+name+"_barrel";
		this.setUnlocalizedName(this.name);
		this.setHardness(1.8F);
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumAxis.Y));
		//this.setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.95F, 0.7F);
		GameRegistry.registerBlock(this, this.name);
		InvModel.add(this, this.name);
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess world, final BlockPos pos){

		switch((EnumAxis)world.getBlockState(pos).getValue(AXIS)) {
		case X:
			this.setBlockBounds( 0.0F, 0.0625F, 0.0625F, 1F, 0.9375F, 0.875F);
			break;
		case Z:
			this.setBlockBounds( 0.0625F, 0.0F, 0.0625F, 0.9375F, 1F, 0.9375F);
			break;
		case Y: case NONE: default:
			this.setBlockBounds( 0.0625F, 0.0F, 0.0625F, 0.9375F, 1F, 0.9375F);
			break;
		}

	}

	protected boolean canBlockStay(final World world, final BlockPos pos)
	{
		return world.isSideSolid(pos.down(), EnumFacing.UP, true) || world.getBlockState(pos.down()).getBlock() instanceof BlockBarrelEmpty;
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos)
	{
		return world.isSideSolid(pos.down(), EnumFacing.UP, true) || world.getBlockState(pos.down()).getBlock() instanceof BlockBarrelEmpty;
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	@Override
	public boolean isFullCube(){
		return false;
	}

	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
		if (world.getBlockState(pos.down()).getBlock() instanceof BlockBarrelEmpty) {
			return this.getDefaultState().withProperty(AXIS, world.getBlockState(pos.down()).getValue(AXIS));
		} else {
			return this.getDefaultState().withProperty(AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
		}
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {AXIS, UNDER_BARREL});
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(
				UNDER_BARREL,
				state.getValue(AXIS) != EnumAxis.Y && world.getBlockState(pos.up()).getBlock() instanceof BlockBarrelEmpty
				);
	}
	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return ((EnumAxis)state.getValue(AXIS)).ordinal() << 2;
	}
	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(AXIS, EnumAxis.values()[(meta & 12) >> 2]);
	}






}
