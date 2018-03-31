package com.gb.cornucopia.fruit;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.item.ItemFood;

public class ItemFruitRaw extends ItemFood {
	public final String name;

	public ItemFruitRaw(final String name) {
		super(4, 0.3F, false);
		this.name = String.format("fruit_%s_raw", name);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setCreativeTab(CornuCopia.tabFruit);
		InvModel.add(this);
	}

}