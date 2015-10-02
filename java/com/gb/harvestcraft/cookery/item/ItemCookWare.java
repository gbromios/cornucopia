package com.gb.harvestcraft.cookery.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCookWare extends Item{
	public final String name;
	public ItemCookWare(String name){
		super();
		this.name = "cookware_" + name;
		this.setUnlocalizedName(this.name);
		this.setMaxStackSize(1);
		this.setContainerItem(this);
		GameRegistry.registerItem(this, this.name);
	}
	
}
