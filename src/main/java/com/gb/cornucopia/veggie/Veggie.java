package com.gb.cornucopia.veggie;

import com.gb.cornucopia.veggie.block.BlockVeggieCrop;
import com.gb.cornucopia.veggie.block.BlockVeggieWild;
import com.gb.cornucopia.veggie.item.ItemVeggieRaw;
import com.gb.cornucopia.veggie.item.ItemVeggieSeed;

public class Veggie {
	public final ItemVeggieRaw raw;
	public final ItemVeggieSeed seed;
	public final BlockVeggieCrop crop;
	public final BlockVeggieWild wild;

	public Veggie(final ItemVeggieRaw raw, final ItemVeggieSeed seed, final BlockVeggieCrop crop, final BlockVeggieWild wild){
		this.raw = raw;
		this.seed = seed;
		this.crop = crop;
		this.wild = wild;

		// hook up what needs hookin up
		seed.setCrop(crop);
		crop.setDrops(raw, seed);
		wild.setDrop(raw, seed);
	}

}
