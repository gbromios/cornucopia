package com.gb.cornucopia.veggie.item;

import com.gb.cornucopia.CornuCopia;

import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ItemVeggieRaw extends ItemFood{
	public final String name; 
	
	public ItemVeggieRaw(String name) {
		// raw veggies: not very filling, also not enjoyed by doggies
		super(1, 0.3F, false);
		this.name = "veggie_" + name + "_raw";
		
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabRawVeg);
		GameRegistry.registerItem(this, this.name);
		OreDictionary.registerOre(
				"food" + Character.toUpperCase(name.charAt(0)) + name.substring(1),
				this
			);
		OreDictionary.registerOre(
				"crop" + Character.toUpperCase(name.charAt(0)) + name.substring(1),
				this
			);
	}

}