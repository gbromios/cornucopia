package com.gb.cornucopia.fruit;

import com.gb.cornucopia.fruit.block.BlockFruitCrop;
import com.gb.cornucopia.fruit.block.BlockFruitLeaf;
import com.gb.cornucopia.fruit.block.BlockFruitSapling;
import com.gb.cornucopia.fruit.item.ItemFruitRaw;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class Fruit {
	public final ItemFruitRaw raw;
	public final BlockFruitSapling sapling;
	public final BlockFruitLeaf leaf;
	public final BlockFruitCrop crop;
		
	public Fruit(ItemFruitRaw raw, BlockFruitSapling sapling, BlockFruitLeaf leaf, BlockFruitCrop crop, IBlockState wood){
		this.raw = raw;
		this.sapling = sapling;
		this.leaf = leaf;
		this.crop = crop;
		
		// hook up needfuls 
		this.sapling.setTreeStates(wood, leaf.getDefaultState());
		this.leaf.setGrows(crop);
		this.crop.setLeaf(leaf);
		this.crop.setDrops(raw, sapling);
	}
}
