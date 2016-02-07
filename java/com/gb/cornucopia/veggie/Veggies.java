package com.gb.cornucopia.veggie;

import java.util.HashMap;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.veggie.block.BlockVeggieCropTall;
import com.gb.cornucopia.veggie.block.BlockVeggieCrop;
import com.gb.cornucopia.veggie.block.BlockVeggieWild;
import com.gb.cornucopia.veggie.item.ItemVeggieRaw;
import com.gb.cornucopia.veggie.item.ItemVeggieSeed;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Veggies {
	//region public fields
	public static Veggie artichoke;
	public static Veggie asparagus;
	public static Veggie barley;
	public static Veggie beet;
	public static Veggie blackberry;
	public static Veggie blueberry;
	public static Veggie cabbage;
	public static Veggie corn;
	public static Veggie lettuce;
	public static Veggie onion;
	public static Veggie pineapple;
	public static Veggie raspberry;
	public static Veggie strawberry;
	public static Veggie tomato;
	public static Veggie turnip;
	
	// endregion
	
	protected static final HashMap<String, Veggie> vegMap = new HashMap<String, Veggie>();

	public static Veggie createTallVeggie(String name){
		return createVeggie(name,
				new ItemVeggieRaw(name),
				new ItemVeggieSeed(name),
				new BlockVeggieCropTall(name),
				new BlockVeggieWild(name, EnumPlantType.Plains)
				);
	}
		
	public static Veggie createVeggie(String name){
		return createVeggie(name,
				new ItemVeggieRaw(name),
				new ItemVeggieSeed(name),
				new BlockVeggieCrop(name),
				new BlockVeggieWild(name, EnumPlantType.Plains)
				);
	}
	
	public static Veggie createVeggie(String name, ItemVeggieRaw raw,  ItemVeggieSeed seed, BlockVeggieCrop crop, BlockVeggieWild wild){
		// take fresh instances and save them in static fields
		Veggie vs = new Veggie(raw, seed, crop, wild);
		vegMap.put(name, vs);
		return vs;

	}

	public static void preInit(){
		artichoke = createVeggie("artichoke");
		asparagus = createVeggie("asparagus");
		barley = createVeggie("barley");
		beet = createVeggie("beet");
		blueberry = createVeggie("blueberry");
		blackberry = createVeggie("blackberry");
		cabbage = createVeggie("cabbage");
		corn = createTallVeggie("corn");
		lettuce = createVeggie("lettuce");
		onion = createVeggie("onion");
		pineapple = createVeggie("pineapple");
		raspberry = createVeggie("raspberry");
		strawberry = createVeggie("strawberry");
		tomato = createVeggie("tomato");
		turnip = createVeggie("turnip");
	}

	public static void init(){
		addRawToSeedRecipes();
	}


	private static void addRawToSeedRecipes() {
		for (Veggie v : vegMap.values()) {
			// 1 veggie = 2 seeds. 
			GameRegistry.addShapelessRecipe(new ItemStack(v.seed, 2), v.raw);
			
		}
	}
}
