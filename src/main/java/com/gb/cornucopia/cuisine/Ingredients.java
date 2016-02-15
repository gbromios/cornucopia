package com.gb.cornucopia.cuisine;

import com.gb.cornucopia.veggie.Veggie;

import net.minecraft.init.Items;

public class Ingredients {
	public static final Ingredient mirepoix_part = new Ingredient("mirepoix_part");

	public static void init(){
		// set up ingredients so we can use them in dishes
		mirepoix_part
		.add(Veggie.onion.raw)
		.add(Veggie.celery.raw)
		.add(Veggie.bell_pepper.raw)
		.add(Items.carrot)
		;		
	}

}
