package com.gb.cornucopia.farming.veggie.item;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemVeggieRaw extends ItemFood{
	public final String name; 

	public ItemVeggieRaw(final String name) {
		// raw veggies: not very filling, also not enjoyed by doggies
		super(3, 0.6F, false);
		this.name = "veggie_" + name + "_raw";

		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabVeggies);
		GameRegistry.registerItem(this, this.name);
		InvModel.add(this, this.name);
	}

}
