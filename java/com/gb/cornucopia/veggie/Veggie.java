package com.gb.cornucopia.veggie;

import java.util.HashMap;

import com.gb.cornucopia.garden.block.BlockGarden;
import com.gb.cornucopia.veggie.block.BlockCropVeggie;
import com.gb.cornucopia.veggie.item.ItemRawVeggie;
import com.gb.cornucopia.veggie.item.ItemSeedVeggie;

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
