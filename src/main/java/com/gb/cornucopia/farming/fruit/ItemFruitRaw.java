package com.gb.cornucopia.farming.fruit;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemFruitRaw extends ItemFood{
	public final String name;
	
	public ItemFruitRaw(final String name) {
		super(4, 0.3F, false);
		this.name = String.format("fruit_%s_raw", name);
		
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabFruit);
		GameRegistry.register(this);
	}

}