package com.gb.cornucopia.veggie;

import java.util.HashMap;

import com.gb.cornucopia.veggie.block.BlockVeggieCrop;
import com.gb.cornucopia.veggie.block.BlockVeggieWild;
import com.gb.cornucopia.veggie.item.ItemVeggieRaw;
import com.gb.cornucopia.veggie.item.ItemVeggieSeed;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Veggie {
/*	public void setGarden(BlockGarden garden){
		this.cropVeg.setGarden(garden);
	}*/
	public final ItemVeggieRaw raw;
	public final ItemVeggieSeed seed;
	public final BlockVeggieCrop crop;
	public final BlockVeggieWild wild;
	
	public Veggie(ItemVeggieRaw raw, ItemVeggieSeed seed, BlockVeggieCrop crop, BlockVeggieWild wild){
		this.raw = raw;
		this.seed = seed;
		this.crop = crop;
		this.wild = wild;
		
		// hook up what needs hookin up
		seed.setCrop(crop);
		crop.setDrops(raw, seed);
	}

}
