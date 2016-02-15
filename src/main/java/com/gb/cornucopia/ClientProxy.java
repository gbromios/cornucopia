package com.gb.cornucopia;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggie;

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

		Veggie.init();
		Fruit.init();
		Bees.init();
	}

	@Override
	public void postInit(final FMLPostInitializationEvent e) {
		super.postInit(e);
		
		InvModel.register();
	}
}