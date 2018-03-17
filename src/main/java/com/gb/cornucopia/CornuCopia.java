package com.gb.cornucopia;

import com.gb.cornucopia.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;


@Mod(modid = CornuCopia.MODID, version = CornuCopia.VERSION)
public class CornuCopia {
    public static final String NAME = "CornuCopia";
    public static final String MODID = "cornucopia";
    public static final String VERSION = "1.2.5";

    @Mod.Instance(value = CornuCopia.MODID)
    public static CornuCopia instance;
    public static Settings config;

    @SidedProxy(clientSide = "com.gb.cornucopia.proxy.ClientProxy", serverSide = "com.gb.cornucopia.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static final CreativeTabs tabBees = new CornucopiaTabs().new BeesCreativeTab();
    public static final CreativeTabs tabCookery = new CornucopiaTabs().new CookeryCreativeTab();
    public static final CreativeTabs tabCuisine = new CornucopiaTabs().new CuisineCreativeTab();
    public static final CreativeTabs tabFruit = new CornucopiaTabs().new FruitCreativeTab();
    public static final CreativeTabs tabVeggies = new CornucopiaTabs().new VeggieCreativeTab();

    @EventHandler
    public void preInit(final FMLPreInitializationEvent e) {
        CornuCopia.proxy.preInit(e);
    }

    @EventHandler
    public void init(final FMLInitializationEvent e) {
        CornuCopia.proxy.init(e);
        GuiHandler.init(this);
    }

    @EventHandler
    public void PostInit(final FMLPostInitializationEvent e) {
        CornuCopia.proxy.postInit(e);
    }

    @EventHandler
    public void serverLoad(final FMLServerStartingEvent event) {
    }
}