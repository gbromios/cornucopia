package com.gb.cornucopia.cookery;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCookWare extends Item {
	public final String name;

	public ItemCookWare(final String name) {
		super();
		this.name = String.format("cookery_%s", name);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setMaxStackSize(1);
		this.setContainerItem(this);
		this.setCreativeTab(CornuCopia.tabCookery);
		GameRegistry.register(this);
		InvModel.add(this);
	}

	public ItemCookWare(final String name, final Vessel v) {
		// optionally, associate cookware with a stovetop vessel
		this(name);
		Vessel.register(v, this);
	}

}
