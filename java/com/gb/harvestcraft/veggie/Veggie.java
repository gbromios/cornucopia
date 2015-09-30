package com.gb.harvestcraft.veggie;

import java.util.HashMap;

import com.gb.harvestcraft.garden.block.BlockGarden;
import com.gb.harvestcraft.veggie.block.BlockCropVeggie;
import com.gb.harvestcraft.veggie.item.ItemRawVeggie;
import com.gb.harvestcraft.veggie.item.ItemSeedVeggie;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Veggie {
/*	public void setGarden(BlockGarden garden){
		this.cropVeg.setGarden(garden);
	}*/
	public final ItemRawVeggie raw;
	public final ItemSeedVeggie seed;
	public final BlockCropVeggie crop;
	
	public Veggie(ItemRawVeggie raw, ItemSeedVeggie seed, BlockCropVeggie crop){
		this.raw = raw;
		this.seed = seed;
		this.crop = crop;
		
		// hook up what needs hookin up
		seed.setCrop(crop);
		crop.setDrops(raw, seed);
	}

}
