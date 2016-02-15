package com.gb.cornucopia.cuisine;

import com.gb.util.WeightedArray;

import net.minecraft.item.Item;

public class Cuisine {

	// intermediate food items
	public static ItemCuisine flour;
	public static ItemCuisine soda;
	public static ItemCuisine vinegar;	
	public static ItemCuisine mirepoix;
	public static ItemCuisine peanut_butter;
	//public static ItemCuisine culture;
	public static ItemCuisine mash;
	
	
	// some processed foods
	public static ItemCuisine oil_olive;
	public static ItemCuisine grape_juice;
	
	// can't do anything special w/ these.... yet!
	public static ItemCuisine roast_coffee;
	public static ItemCuisine dried_tea;
	
	// will probably make distinct types for these guys later: 
	// some spices:
	public static ItemCuisine black_pepper;
	public static ItemCuisine cinnamon;
	public static ItemCuisine chili_powder;
	public static ItemCuisine curry_powder;
	public static ItemCuisine mustard;
	// some herbs:
	public static ItemCuisine mint;
	public static ItemCuisine cilantro;
	public static ItemCuisine basil;
	public static ItemCuisine rosemary;
	public static ItemCuisine oregano;
	
	public static final WeightedArray<Item> spice_drops = new WeightedArray<>();
	public static final WeightedArray<Item> herb_drops = new WeightedArray<>();

	
	// i.e. placeholders
	private static ItemCuisine _generic(String name){ return new ItemCuisine(name, 0, 0F); }
		
	public static void preInit(){		
		oil_olive = new ItemCuisine("oil_olive", 1, 0.5F);
		grape_juice = new ItemCuisine("grape_juice", 1, 0.25F);
		
		
		flour = new ItemCuisine("flour", 1, 0.05F);
		
		mirepoix = new ItemCuisine("mirepoix", 1, 0.2F);
		peanut_butter = new ItemCuisine("peanut_butter", 2, 0.5F);
		
		roast_coffee = _generic("roast_coffee");
		dried_tea    = _generic("dried_tea");
		
		black_pepper = _generic("black_pepper");
		cinnamon     = _generic("cinnamon");
		chili_powder = _generic("chili_powder");
		curry_powder = _generic("curry_powder");
		mustard      = _generic ("mustard");
		
		spice_drops
		.add(black_pepper, 40)
		.add(cinnamon, 10)
		.add(chili_powder, 10)
		.add(curry_powder, 10)
		.add(mustard, 20)
		;
		
		basil        = _generic("basil");
		cilantro     = _generic("cilantro");
		mint         = _generic("mint");
		oregano      = _generic("oregano");
		rosemary     = _generic("rosemary");
		
		herb_drops
		.add(basil, 40)
		.add(cilantro, 10)
		.add(mint, 10)
		.add(oregano, 10)
		.add(rosemary, 10)
		;
	}
}
