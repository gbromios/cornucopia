package com.gb.cornucopia.veggie;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemVeggieRaw extends ItemFood{
	public final String name; 

	public ItemVeggieRaw(final String name) {
		super(3, 0.6F, false);
		this.name = String.format("veggie_%s_raw", name);

		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabVeggies);
		GameRegistry.register(this);
	}

}
