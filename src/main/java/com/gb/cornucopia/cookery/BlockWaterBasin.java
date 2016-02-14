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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockWaterBasin extends Block{
	public final String name;
	//public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockWaterBasin(){
		super(Material.rock);
		this.name = "cookery_water_basin";
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setUnlocalizedName(this.name);
		//this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(0.5F);
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
		GameRegistry.registerBlock(this, this.name);
		InvModel.add(this, this.name);

	}

	public boolean isOpaqueCube() { return false; }

	public boolean isFullCube() { return false; }

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumFacing side, final float hitX, final float hitY, final float hitZ)
	{

		if (world.isRemote || player.inventory.getCurrentItem() == null) { return true; }

		final Item held_item = player.inventory.getCurrentItem().getItem();
		ItemStack filled_container = null;

		if (held_item == Items.glass_bottle)
		{
			filled_container = new ItemStack(Items.potionitem, 1, 0);
		}
		else if (held_item == Items.bucket)
		{
			filled_container = new ItemStack(Items.water_bucket, 1, 0);
		}
		else
		{
			return true;
		}

		if (!player.inventory.addItemStackToInventory(filled_container))
		{
			//world.spawnEntityInWorld(new EntityItem(world, , water_bucket));
			Block.spawnAsEntity(world, pos, filled_container);

		}
		else if (player instanceof EntityPlayerMP)
		{
			((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
		}

		// ....wat
		if (--(player.inventory.getCurrentItem().stackSize) <= 0)
		{
			player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
		}

		return true;
	}
}