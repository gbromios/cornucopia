package com.gb.cornucopia.bees.block;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.GuiHandler;
import com.gb.cornucopia.InvModel;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockApiary extends Block implements  ITileEntityProvider{

	public BlockApiary() {
		super(Material.wood);
		this.setCreativeTab(CornuCopia.tabBees);
		this.setHardness(0.6F);
		this.setUnlocalizedName("bee_apiary");
		GameRegistry.registerBlock(this, "bee_apiary");
		GameRegistry.registerTileEntity(TileEntityApiary.class, "bee_apiary_entity");
		GuiHandler.register(this, this);
		InvModel.add(this, "bee_apiary");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityApiary();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float x, float y, float z)
	{
		if (!world.isRemote) {
			player.openGui(CornuCopia.instance, this.getIdFromBlock(this), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;

	}

}
