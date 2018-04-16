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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockApiary extends Block implements ITileEntityProvider {
	public final String name = "bee_apiary";

	public BlockApiary() {
		super(Material.WOOD);
		this.setCreativeTab(CornuCopia.tabBees);
		this.setHardness(0.6F);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		GameRegistry.registerTileEntity(TileEntityApiary.class, String.format("%s_entity", name));
		InvModel.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new TileEntityApiary();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(CornuCopia.instance, 420, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;

	}

	public void breakBlock(final World world, final BlockPos pos, final IBlockState state) {
		TileEntity apiary = world.getTileEntity(pos);

		if (apiary instanceof TileEntityApiary) {
			InventoryHelper.dropInventoryItems(world, pos, (IInventory) apiary);
		}
		super.breakBlock(world, pos, state);
	}

}

