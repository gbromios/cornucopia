package com.gb.cornucopia;

import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.fruit.Fruits;
import com.gb.cornucopia.veggie.Veggies;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class ServerProxy {

    public void preInit(FMLPreInitializationEvent e) {
    	Veggies.preInit();
    	Fruits.preInit();
    	Cookery.preInit();
    	
    	
    }

    public void init(FMLInitializationEvent e) {
    	// TODO: take this ugly shit out and hook into BoP~!
    	GameRegistry.registerWorldGenerator(new DummyGen(), 9999);
    	Cookery.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
