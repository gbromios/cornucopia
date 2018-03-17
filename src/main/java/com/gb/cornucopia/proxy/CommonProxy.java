package com.gb.cornucopia.proxy;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.Settings;
import com.gb.cornucopia.WildGrowth;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.farming.bees.Bees;
import com.gb.cornucopia.farming.fruit.Fruit;
import com.gb.cornucopia.farming.veggie.Veggie;
import com.gb.cornucopia.food.cheese.Cheese;
import com.gb.cornucopia.food.cuisine.Cuisine;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
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

    public void init(FMLInitializationEvent e) {
        Veggie.init();
        Bees.init();
        Cheese.init();
        Cookery.init();
        Cuisine.init();

        MinecraftForge.EVENT_BUS.register(CornuCopia.config);
        //MinecraftForge.EVENT_BUS.register(new SaltyBoy()); // cf bug #61
        MinecraftForge.EVENT_BUS.register(new WildGrowth());
    }

    public void postInit(FMLPostInitializationEvent e) {
        Fruit.postInit();
        Veggie.postInit();
        Bees.postInit();
        Cheese.postInit();
        Cookery.postInit();
        Cuisine.postInit();
    }
}
