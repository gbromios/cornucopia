package com.gb.cornucopia;

import java.io.File;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.cheese.Cheese;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cuisine.Cuisine;
import com.gb.cornucopia.cuisine.SaltyBoy;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggie;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.Console;


public class ServerProxy {
	public void preInit(final FMLPreInitializationEvent e) {
		CornuCopia.config =  new Settings(new File(e.getModConfigurationDirectory(), CornuCopia.MODID));
		Fruit.preInit();
		Veggie.preInit();
		Bees.preInit();
		Cheese.preInit(); // not sure where cheese will go in order tbh;
		Cookery.preInit();
		Cuisine.preInit();
		
		
		// testing shelf
		//BlockShelf.preInit();
		
	}

	public void init(final FMLInitializationEvent e) {
		//Fruit.init(); client only: leaf graphics
		Veggie.init();
		Bees.init();
		Cheese.init(); 
		Cookery.init();
		Cuisine.init();
		
		MinecraftForge.EVENT_BUS.register(CornuCopia.config);
		MinecraftForge.EVENT_BUS.register(new SaltyBoy());
		MinecraftForge.EVENT_BUS.register(new WildGrowth());
	}

	public void postInit(final FMLPostInitializationEvent e) {
		Fruit.postInit();
		Veggie.postInit();
		Bees.postInit();
		Cheese.postInit(); 
		Cookery.postInit();
		Cuisine.postInit();

	}
}
