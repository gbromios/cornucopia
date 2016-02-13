package com.gb.cornucopia.cookery.item;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.block.Vessel;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCookWare extends Item{
	public final String name;
	public ItemCookWare(final String name) {
		super();
		this.name = "cookery_" + name;
		this.setUnlocalizedName(this.name);
		this.setMaxStackSize(1);
		this.setContainerItem(this);
		this.setCreativeTab(CornuCopia.tabCookery);
		GameRegistry.registerItem(this, this.name);
		InvModel.add(this, this.name);
	}

	public ItemCookWare(final String name, final Vessel v){
		// optionally, associate cookware with a stovetop vessel
		this(name);
		Vessel.register(v, this);
	}

}
