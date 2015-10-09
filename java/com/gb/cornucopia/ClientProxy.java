package com.gb.cornucopia;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.fruit.Fruits;
import com.gb.cornucopia.veggie.Veggies;

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
		Bees.init();
    }

	@Override
    public void postInit(FMLPostInitializationEvent e) {
    	super.postInit(e);
    	ModelRenderer m;
    }
}