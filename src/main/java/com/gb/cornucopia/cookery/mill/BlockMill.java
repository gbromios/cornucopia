	package com.gb.cornucopia.cookery.mill;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Cookery;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMill extends Block  implements ITileEntityProvider{
	public static final PropertyInteger PROGRESS = PropertyInteger.create("progress", 0, 3);
	public final String name = "cookery_mill"; 

	public BlockMill()
	{
		super(Material.WOOD);
		this.setUnlocalizedName(this.name);
		this.setHardness(1.5F);
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PROGRESS, 0));
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.75F, 1F);
		GameRegistry.registerBlock(this, this.name);
		GameRegistry.registerTileEntity(TileEntityMill.class, "cookery_mill_entity");
		InvModel.add(this, this.name);
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumFacing side, final float hitX, final float hitY, final float hitZ)
	{
		if (!world.isRemote) {
			player.openGui(CornuCopia.instance, 420, world, pos.getX(), pos.getY(), pos.getZ());
			//world.setBlockState(pos, state.withProperty(PROGRESS, ((int)state.getValue(PROGRESS) + 1) % 16 ));
		}
		

		
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack)
	{
		world.setBlockState(pos.up(), Cookery.milltop.getDefaultState());
	}
	
	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos){
		return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
	}

	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROGRESS, meta);
	}

	public int getMetaFromState(final IBlockState state)
	{
		return (int)state.getValue(PROGRESS);
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {PROGRESS});
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
		if (world.getBlockState(pos.up()).getBlock() != Cookery.milltop){
			// breaking the handle
			this.dropBlockAsItem(world, pos, state, 0);
			this.breakBlock(world, pos, state);
			world.setBlockToAir(pos);
		}
	}
	public void breakBlock(final World world, final BlockPos pos, final IBlockState state)
	{
	//System.out.println("break presser");

		TileEntity mill = world.getTileEntity(pos);

		if (mill instanceof TileEntityMill)
		{
			InventoryHelper.dropInventoryItems(world, pos, (TileEntityMill)mill);
		}
		super.breakBlock(world, pos, state);
	}
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMill();
	}
}
