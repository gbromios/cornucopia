package com.gb.cornucopia.cuisine;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

// item for now... potion later??
public class ItemLibation extends Item{
	public final String name;
	public ItemLibation(String name) {
		this.name = "brew_" + name + "_drink";
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabCuisine);
		GameRegistry.registerItem(this, this.name);
		InvModel.add(this, this.name);
	}

}
