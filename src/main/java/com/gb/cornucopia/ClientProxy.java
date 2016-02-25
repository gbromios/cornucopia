package com.gb.cornucopia;

import com.gb.cornucopia.fruit.Fruit;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {


	@Override
	public void preInit(final FMLPreInitializationEvent e) {
		super.preInit(e);
	}

	@Override
	public void init(final FMLInitializationEvent e) {
		super.init(e);
		Fruit.init();
		
		// finally some real client-side action
		
		
	}

	@Override
	public void postInit(final FMLPostInitializationEvent e) {
		super.postInit(e);
		InvModel.register();
	}
}