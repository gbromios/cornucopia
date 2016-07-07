package com.gb.cornucopia.cuisine;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

// item for now... potion later??
public class ItemLibation extends ItemCuisine{
	//public final String name;
	public ItemLibation(String name) {
		super(name, 4, 0.4F);
		this.setAlwaysEdible();
	}

}
