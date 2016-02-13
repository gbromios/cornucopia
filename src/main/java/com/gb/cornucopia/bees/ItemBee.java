package com.gb.cornucopia.bees;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBee extends Item{
	public final String name;
	public ItemBee(final String name){
		this.name = "bee_" + name;
		this.setCreativeTab(CornuCopia.tabBees);
		this.setUnlocalizedName(this.name);
		GameRegistry.registerItem(this, this.name);
		InvModel.add(this, this.name);
	}
}
