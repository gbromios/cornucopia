package com.gb.cornucopia.fruit;

import net.minecraft.block.BlockPlanks;

public class Fruit {
	public final ItemFruitRaw raw;
	public final BlockFruitSapling sapling;
	public final BlockFruitLeaf leaf;
	public final BlockFruitCrop crop;

	public Fruit(final ItemFruitRaw raw, final BlockFruitSapling sapling, final BlockFruitLeaf leaf, final BlockFruitCrop crop, final BlockPlanks.EnumType wood){
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
