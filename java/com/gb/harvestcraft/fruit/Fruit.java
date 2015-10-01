package com.gb.harvestcraft.fruit;

import com.gb.harvestcraft.fruit.block.BlockCropFruit;
import com.gb.harvestcraft.fruit.block.BlockLeafFruit;
import com.gb.harvestcraft.fruit.block.BlockSaplingFruit;
import com.gb.harvestcraft.fruit.item.ItemRawFruit;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class Fruit {
	public final ItemRawFruit raw;
	public final BlockSaplingFruit sapling;
	public final BlockLeafFruit leaf;
	public final BlockCropFruit crop;
	public final IBlockState wood; // could just use a wood type enum here but i think in the future that will be limiting. this isn't any harder? dunno if it's got shitty performance or something maybe
		
	public Fruit(ItemRawFruit raw, BlockSaplingFruit sapling, BlockLeafFruit leaf, BlockCropFruit crop, IBlockState wood){
		this.raw = raw;
		this.sapling = sapling;
		this.leaf = leaf;
		this.crop = crop;
		this.wood = wood;
		
		// hook up needfuls 
		this.sapling.setTreeStates(wood, leaf.getDefaultState());
		this.leaf.setGrows((BlockPlanks.EnumType)wood.getValue(BlockPlanks.VARIANT), crop);
		this.crop.setLeaf(leaf);
		this.crop.setDrop(raw);
	}
}
