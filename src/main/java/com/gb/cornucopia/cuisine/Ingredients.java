package com.gb.cornucopia.cuisine;

import com.gb.cornucopia.veggie.Veggies;

import net.minecraft.init.Items;

public class Ingredients {
	public static final Ingredient mirepoix_part = new Ingredient("mirepoix_part");

	public static void init(){
		// set up ingredients so we can use them in dishes
		mirepoix_part
		.add(Veggies.onion.raw)
		.add(Veggies.celery.raw)
		.add(Veggies.bell_pepper.raw)
		.add(Items.carrot)
		;		
	}

}
