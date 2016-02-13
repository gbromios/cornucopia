	package com.gb.cornucopia.cookery.presser;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cookery.stove.BlockStoveTop;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockPresser extends Block  implements ITileEntityProvider{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyInteger PROGRESS = PropertyInteger.create("progress", 0, 7); // TODO get this from the meta above
	public final String name = "cookery_presser"; 

	public BlockPresser()
	{
		super(Material.wood);
		this.setUnlocalizedName(this.name);
		this.setHardness(1.5F);
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PROGRESS, 0));
		GameRegistry.registerBlock(this, this.name);
		GameRegistry.registerTileEntity(TileEntityPresser.class, "cookery_presser_entity");
		InvModel.add(this, this.name);
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos){
		return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
	}

	public IBlockState onBlockPlaced(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PROGRESS, 0);
	}

	public void onBlockPlacedBy(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack)
	{
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PROGRESS, 0), 2);
		world.setBlockState(pos.up(), Cookery.pressertop.getDefaultState().withProperty(BlockStoveTop.FACING, placer.getHorizontalFacing().getOpposite()));
	}
	
	public void breakBlock(final World world, final BlockPos pos, final IBlockState state)
	{

		TileEntity presser = world.getTileEntity(pos);

		if (presser instanceof TileEntityPresser)
		{
			InventoryHelper.dropInventoryItems(world, pos, (TileEntityPresser)presser);
		}
		super.breakBlock(world, pos, state);
	}


	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumFacing side, final float hitX, final float hitY, final float hitZ)
	{
		if (world.getBlockState(pos.up()).getBlock() == Cookery.pressertop) {
			world.setBlockState(pos.up(), Cookery.pressertop.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(PROGRESS, 0));
			world.markBlockForUpdate(pos.up());
			world.markBlockForUpdate(pos);
		}
		if (!world.isRemote) {
			player.openGui(CornuCopia.instance, 420, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
	}

	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess world, final BlockPos pos)
	{
		// derive progress from handle above
		final IBlockState pup = world.getBlockState(pos.up());
		return pup.getBlock() == Cookery.pressertop ? state.withProperty(PROGRESS, pup.getValue(PROGRESS)) : state ;
		
	}

	public int getMetaFromState(final IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
	}

	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {FACING, PROGRESS});
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
	public void onNeighborBlockChange(final World world, final BlockPos pos, final IBlockState state, final Block neighborBlock){
		if (world.getBlockState(pos.up()).getBlock() != Cookery.pressertop){
			this.dropBlockAsItem(world, pos, state, 0);
			this.breakBlock(world, pos, state);
			world.setBlockToAir(pos);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPresser();
	}
}
