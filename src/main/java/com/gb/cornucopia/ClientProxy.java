package com.gb.cornucopia;

import com.gb.cornucopia.fruit.Fruit;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {


	@Override
	public void preInit(final FMLPreInitializationEvent e) {
		super.preInit(e);
	}

	@Override
	public void init(final FMLInitializationEvent e) {
		super.init(e);
		Fruit.init();
	}

	@Override
	public void postInit(final FMLPostInitializationEvent e) {
		super.postInit(e);
		InvModel.register();
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String loc) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(loc, "inventory"));
	}
}