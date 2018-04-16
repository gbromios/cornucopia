package com.gb.cornucopia.cookery;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWaterBasin extends Block {
	public final String name;
	//public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockWaterBasin() {
		super(Material.ROCK);
		this.name = "cookery_water_basin";
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		//this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(0.5F);
		InvModel.add(this);

	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote || playerIn.inventory.getCurrentItem() == null) {
			return true;
		}

		final Item held_item = playerIn.inventory.getCurrentItem().getItem();
		ItemStack filled_container = null;

		if (held_item == Items.GLASS_BOTTLE) {
			filled_container = new ItemStack(Items.POTIONITEM, 1, 0);
		} else if (held_item == Items.BUCKET) {
			filled_container = new ItemStack(Items.WATER_BUCKET, 1, 0);
		} else {
			return true;
		}

		if (!playerIn.inventory.addItemStackToInventory(filled_container)) {
			//worldIn.spawnEntityInWorld(new EntityItem(worldIn, , water_bucket));
			Block.spawnAsEntity(worldIn, pos, filled_container);
		} else if (playerIn instanceof EntityPlayerMP) {
			((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
		}

		// ....wat
		playerIn.inventory.getCurrentItem().shrink(1);
		if (playerIn.inventory.getCurrentItem().getCount() <= 0) {
			playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
		}

		return true;
	}
}
