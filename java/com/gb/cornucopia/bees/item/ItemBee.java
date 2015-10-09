package com.gb.cornucopia.bees.item;

import com.gb.cornucopia.CornuCopia;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBee extends Item{
	public final String name;
	public ItemBee(String name){
		this.name = "bee_" + name;
		this.setCreativeTab(CornuCopia.tabBees);
		this.setUnlocalizedName(this.name);
		GameRegistry.registerItem(this, this.name);
	}
}
