package com.gb.harvestcraft.fruit.item;

import com.gb.harvestcraft.HarvestCraft;

import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ItemRawFruit extends ItemFood{
	public final String name;
	
	public ItemRawFruit(String name) {
		super(1, 0.3F, false);
		this.name = "fruit_raw_" + name;
		
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(HarvestCraft.tabRawFruit);
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