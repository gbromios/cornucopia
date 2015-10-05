package com.gb.harvestcraft.cookery.block;

import com.gb.harvestcraft.HarvestCraft;
import com.gb.harvestcraft.cookery.Cookery;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCookingTable extends Block{
	public final String name;

	public BlockCookingTable(String name){
		super(Material.wood);
		this.name = "cooking_table_" + name;
		this.setCreativeTab(HarvestCraft.tabCookeryBlock);
		this.setUnlocalizedName(this.name);
		GameRegistry.registerBlock(this, this.name);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float x, float y, float z)
	{
		if (!world.isRemote) {
			player.openGui(HarvestCraft.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;


	}
	
	
}