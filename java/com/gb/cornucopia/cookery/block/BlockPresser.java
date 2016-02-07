package com.gb.cornucopia.cookery.block;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.bees.block.TileEntityApiary;
import com.gb.cornucopia.cookery.Cookery;

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
		this.setCreativeTab(CornuCopia.tabCookeryBlock);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PROGRESS, 0));
		GameRegistry.registerBlock(this, this.name);
		GameRegistry.registerTileEntity(TileEntityPresser.class, "cookery_presser_entity");
		InvModel.add(this, this.name);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos){
		return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
	}

	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PROGRESS, 0);
	}

	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PROGRESS, 0), 2);
		world.setBlockState(pos.up(), Cookery.pressertop.getDefaultState().withProperty(Cookery.stovetop.FACING, placer.getHorizontalFacing().getOpposite()));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//final IBlockState upState = world.getBlockState(pos.up()); 
		//return Cookery.stovetop.onBlockActivated(world, pos.up(), upState, playerIn, side, hitX, hitY, hitZ);
		//Integer i = Math.min(7, (Integer)state.getValue(PROGRESS) + 1);
		//world.setBlockState(pos, state.withProperty(PROGRESS, i));
		world.setBlockState(pos.up(), Cookery.pressertop.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(PROGRESS, 0));
		world.markBlockForUpdate(pos.up());
		world.markBlockForUpdate(pos);
		if (!world.isRemote) {
			player.openGui(CornuCopia.instance, 420, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
	}
	
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
		// derive progress from handle above
        return state.withProperty(PROGRESS, world.getBlockState(pos.up()).getValue(PROGRESS));
    }
	
	public int getMetaFromState(IBlockState state)
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
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock){
		if (world.getBlockState(pos.up()).getBlock() != Cookery.pressertop){
			world.setBlockToAir(pos);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		System.out.println("aw yhere gots");
		
		return new TileEntityPresser();
	}
}
