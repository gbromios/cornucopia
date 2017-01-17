package com.gb.cornucopia.bees.apiary;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockApiary extends Block implements  ITileEntityProvider{

	public BlockApiary() {
		super(Material.WOOD);
		this.setCreativeTab(CornuCopia.tabBees);
		this.setHardness(0.6F);
		this.setUnlocalizedName("bee_apiary");
		GameRegistry.registerBlock(this, "bee_apiary");
		GameRegistry.registerTileEntity(TileEntityApiary.class, "bee_apiary_entity");
		InvModel.add(this, "bee_apiary");
	}

	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new TileEntityApiary();
	}
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumFacing side, final float x, final float y, final float z)
	{
		if (!world.isRemote) {
			player.openGui(CornuCopia.instance, 420, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;

	}

	public void breakBlock(final World world, final BlockPos pos, final IBlockState state)
	{
		TileEntity apiary = world.getTileEntity(pos);

		if (apiary instanceof TileEntityApiary)
		{	
			InventoryHelper.dropInventoryItems(world, pos, (IInventory)apiary);
		}
		super.breakBlock(world, pos, state);
	}

}

