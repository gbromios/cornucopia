package com.gb.cornucopia.bees;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class BlockHive extends Block {
	protected static final AxisAlignedBB HIVE_AABB = new AxisAlignedBB(0.2F, 0, 0.2F, 0.8F, 1, 0.8F);
	public final String name = "bee_hive";

	public BlockHive() {
		super(Material.GOURD);
		this.setCreativeTab(CornuCopia.tabBees);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		GameRegistry.register(this);
		InvModel.add(this, name);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}


	@Override
	public java.util.List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
		java.util.List<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(Bees.bee, 1 + RANDOM.nextInt(3)));
		if (RANDOM.nextInt(16) == 0) {
			ret.add(new ItemStack(Bees.queen));
		}
		return ret;
	}

}