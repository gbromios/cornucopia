package com.gb.cornucopia.proxy;

import java.io.File;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.Settings;
import com.gb.cornucopia.WildGrowth;
import com.gb.cornucopia.farming.bees.Bees;
import com.gb.cornucopia.food.cheese.Cheese;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.food.cuisine.Cuisine;
import com.gb.cornucopia.farming.fruit.Fruit;
import com.gb.cornucopia.farming.veggie.Veggie;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class ServerProxy extends CommonProxy {
    public void preInit(final FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    public void init(final FMLInitializationEvent e) {
        super.init(e);
    }

    public void postInit(final FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
