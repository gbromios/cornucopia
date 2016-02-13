package com.gb.cornucopia.fruit.item;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemFruitRaw extends ItemFood{
	public final String name;
	
	public ItemFruitRaw(final String name) {
		super(4, 0.3F, false);
		this.name = "fruit_" + name + "_raw";
		
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabFruit);
		GameRegistry.registerItem(this, this.name);
		InvModel.add(this, this.name);
	}

}