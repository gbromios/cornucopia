package com.gb.cornucopia.bees.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.bees.Bees;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHive extends Block{
	public BlockHive()
	{
		super(Material.gourd);
		this.setBlockBounds(0.2F, 0, 0.2F, 0.8F, 1, 0.8F);
		this.setCreativeTab(CornuCopia.tabBees);
	}
	
	
	public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean isFullCube()
    {
        return false;
    }
	

	@Override
	public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		java.util.List<ItemStack> ret = new ArrayList<ItemStack>();

		ret.add(new ItemStack(Bees.bee, 1 + RANDOM.nextInt(3)));
		if (RANDOM.nextInt(16) == 0) {
			ret.add(new ItemStack(Bees.queen));
		}

		return ret;
	}

}