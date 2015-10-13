package com.gb.cornucopia.cookery.item;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

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
		this.setCreativeTab(CornuCopia.tabCookeryBlock);
		GameRegistry.registerItem(this, this.name);
		InvModel.add(this, this.name);
	}
	
}
