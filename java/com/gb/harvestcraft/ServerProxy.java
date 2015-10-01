package com.gb.harvestcraft;

import com.gb.harvestcraft.cookery.Cookery;
import com.gb.harvestcraft.fruit.Fruits;
import com.gb.harvestcraft.garden.Gardens;
import com.gb.harvestcraft.veggie.Veggies;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;


public class ServerProxy {

    public void preInit(FMLPreInitializationEvent e) {
    	Veggies.preInit();
    	Fruits.preInit();
    	Gardens.preInit(); // depends on veggies atm but could hook em up up later in theory
    	Cookery.preInit();
    	
    	
    }

    public void init(FMLInitializationEvent e) {
    	Cookery.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}