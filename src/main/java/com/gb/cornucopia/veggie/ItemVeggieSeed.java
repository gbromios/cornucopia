package com.gb.cornucopia.veggie;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

public class ItemVeggieSeed extends ItemSeeds {
	public final String name;

	public ItemVeggieSeed(String name, BlockVeggieCrop crop) {
		super(crop, Blocks.FARMLAND); // TODO tie the plantable block to the crop
		this.name = String.format("veggie_%s_seed", name);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setCreativeTab(CornuCopia.tabVeggies);
		InvModel.add(this);
	}
}