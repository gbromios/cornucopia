package com.gb.cornucopia;

import com.gb.cornucopia.network.PacketRequestUpdateStove;
import com.gb.cornucopia.network.PacketUpdateStove;
import com.gb.cornucopia.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


@Mod(name = CornuCopia.NAME, modid = CornuCopia.MODID, version = CornuCopia.VERSION)
public class CornuCopia {
	public static final String NAME = "CornuCopia";
	public static final String MODID = "cornucopia";
	public static final String VERSION = "1.2.6";

	@Mod.Instance(value = CornuCopia.MODID)
	public static CornuCopia instance;
	public static Settings config;

	public static SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(CornuCopia.MODID);

	@SidedProxy(clientSide = "com.gb.cornucopia.proxy.ClientProxy", serverSide = "com.gb.cornucopia.proxy.ServerProxy")
	public static CommonProxy proxy;

	public static final CreativeTabs tabBees = new CornucopiaTabs().new BeesCreativeTab();
	public static final CreativeTabs tabCookery = new CornucopiaTabs().new CookeryCreativeTab();
	public static final CreativeTabs tabCuisine = new CornucopiaTabs().new CuisineCreativeTab();
	public static final CreativeTabs tabFruit = new CornucopiaTabs().new FruitCreativeTab();
	public static final CreativeTabs tabVeggies = new CornucopiaTabs().new VeggieCreativeTab();

	private static byte packetId = 0;

	@EventHandler
	public void preInit(final FMLPreInitializationEvent e) {
		CornuCopia.proxy.preInit(e);

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		wrapper.registerMessage(new PacketUpdateStove.Handler(), PacketUpdateStove.class, packetId++, Side.CLIENT);
		wrapper.registerMessage(new PacketRequestUpdateStove.Handler(), PacketRequestUpdateStove.class, packetId++, Side.SERVER);
	}

	@EventHandler
	public void init(final FMLInitializationEvent e) {
		CornuCopia.proxy.init(e);
		/*GuiHandler.init(this);*/
	}

	@EventHandler
	public void PostInit(final FMLPostInitializationEvent e) {
		CornuCopia.proxy.postInit(e);
	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event) {
	}

	@Mod.EventBusSubscriber
	public static class RegistrationHandler {

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			InvModel.registerItems(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerItems(ModelRegistryEvent event) {
			InvModel.registerItemModels();
		}

		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			InvModel.registerBlocks(event.getRegistry());
		}
	}
}