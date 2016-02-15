package com.gb.cornucopia;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggie;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;


@Mod(modid = CornuCopia.MODID, version = CornuCopia.VERSION)
public class CornuCopia
{
	public static final String NAME = "CornuCopia";
	public static final String MODID = "cornucopia";
	public static final String VERSION = "1.0.3";

	@Instance(value = CornuCopia.MODID)
	public static CornuCopia instance;
	
	@SidedProxy(clientSide="com.gb.cornucopia.ClientProxy", serverSide="com.gb.cornucopia.ServerProxy")
	public static ServerProxy proxy;

	//region creative tabs
	public static final CreativeTabs tabBees = new CreativeTabs("cc_bees"){
		@Override
		public Item getTabIconItem() {
			return Bees.queen;
		}    	
	};
	public static final CreativeTabs tabVeggies = new CreativeTabs("cc_veggies"){
		@Override
		public Item getTabIconItem() {
			return Veggie.asparagus.raw;
		}
	};
	public static final CreativeTabs tabFruit = new CreativeTabs("cc_fruit"){
		@Override
		public Item getTabIconItem() {
			return Fruit.peach.raw;
		}
	};    
	public static final CreativeTabs tabCookery = new CreativeTabs("cc_cookery"){
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Cookery.cutting_board);
		}
	};
	public static final CreativeTabs tabCuisine = new CreativeTabs("cc_cuisine"){
		@Override
		public Item getTabIconItem() {
			return Bees.honey_raw; // TODO: fix icon
		}
	};

	//endregion

	@EventHandler
	public void preInit(final FMLPreInitializationEvent e)
	{
		CornuCopia.proxy.preInit(e);
	}

	@EventHandler
	public void init(final FMLInitializationEvent e)
	{
		CornuCopia.proxy.init(e);
	}

	@EventHandler
	public void PostInit(final FMLPostInitializationEvent e)
	{
		CornuCopia.proxy.postInit(e);
	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CMDReplant());
	}
}
