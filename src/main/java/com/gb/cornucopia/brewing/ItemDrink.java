package com.gb.cornucopia.brewing;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

// item for now... potion later??
public class ItemDrink extends Item{
	public final String name;
	public ItemDrink(String name) {
		this.name = "brew_" + name + "_drink";
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabCuisine);
		GameRegistry.registerItem(this, this.name);
		InvModel.add(this, this.name);
	}

}
