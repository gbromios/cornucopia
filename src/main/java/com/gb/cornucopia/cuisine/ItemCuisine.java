package com.gb.cornucopia.cuisine;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;

import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCuisine extends ItemFood{
	public final String name;
	
	public ItemCuisine(final String name, final int amount, final float saturation) {
		this(name, amount, saturation, false);
	}

	public ItemCuisine(final String name, final int amount, final float saturation, final boolean for_wolf) {
		super(amount, saturation, for_wolf);
		this.name = "cuisine_" + name;
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabCuisine);
		GameRegistry.registerItem(this, this.name);
		InvModel.add(this, this.name);
	}

}
