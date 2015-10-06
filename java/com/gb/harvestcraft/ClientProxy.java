package com.gb.harvestcraft;

import com.gb.harvestcraft.cookery.Cookery;
import com.gb.harvestcraft.fruit.Fruits;
import com.gb.harvestcraft.garden.Gardens;
import com.gb.harvestcraft.veggie.Veggies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {

	@Override
    public void preInit(FMLPreInitializationEvent e) {
    	super.preInit(e);
    }

	@Override
    public void init(FMLInitializationEvent e) {
    	super.init(e);

		Veggies.init();
		Fruits.init();
    	Gardens.init();
    }

	@Override
    public void postInit(FMLPostInitializationEvent e) {
    	super.postInit(e);
    	ModelRenderer m;
    }
}