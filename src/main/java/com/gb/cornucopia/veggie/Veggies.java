package com.gb.cornucopia.veggie;

import java.util.HashMap;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.veggie.block.BlockVeggieCrop;
import com.gb.cornucopia.veggie.block.BlockVeggieCropTall;
import com.gb.cornucopia.veggie.block.BlockVeggieWild;
import com.gb.cornucopia.veggie.item.ItemVeggieRaw;
import com.gb.cornucopia.veggie.item.ItemVeggieSeed;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Veggies {
	//region public fields
	public static Veggie artichoke;
	public static Veggie asparagus;
	public static Veggie barley;
	public static Veggie beet;
	public static Veggie bell_pepper;
	public static Veggie blackberry;
	public static Veggie blueberry;
	public static Veggie broccoli;
	public static Veggie cabbage;
	public static Veggie celery;
	public static Veggie lettuce;
	public static Veggie onion;
	public static Veggie pineapple;
	public static Veggie raspberry;
	public static Veggie strawberry;
	public static Veggie tomato;
	public static Veggie turnip;
	public static Veggie garlic;
	public static Veggie peanut;
	public static Veggie bean;
	public static Veggie lentil;
	public static Veggie pea;
	public static Veggie stringbean;

	public static Veggie grape;
	public static Veggie spice;
	public static Veggie herb;
	public static Veggie tea;
	public static Veggie soy;
	public static Veggie cucumber;
	public static Veggie eggplant;
	public static Veggie zucchini;
	
	public static Veggie corn;
	
	public static Veggie hops;
	

	// endregion

	protected static final HashMap<String, Veggie> vegMap = new HashMap<>();

	public static Veggie createTallVeggie(final String name){
		return createVeggie(name,
				new ItemVeggieRaw(name),
				new ItemVeggieSeed(name),
				new BlockVeggieCropTall(name),
				new BlockVeggieWild(name, EnumPlantType.Plains)
				);
	}

	public static Veggie createVeggie(final String name){
		return createVeggie(name,
				new ItemVeggieRaw(name),
				new ItemVeggieSeed(name),
				new BlockVeggieCrop(name),
				new BlockVeggieWild(name, EnumPlantType.Plains)
				);
	}

	public static Veggie createVeggie(final String name, final ItemVeggieRaw raw,  final ItemVeggieSeed seed, final BlockVeggieCrop crop, final BlockVeggieWild wild){
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
		bean = createVeggie("bean");
		bell_pepper = createVeggie("bell_pepper");
		blackberry = createVeggie("blackberry");
		blueberry = createVeggie("blueberry");
		broccoli = createVeggie("broccoli");
		cabbage = createVeggie("cabbage");
		celery = createVeggie("celery");
		corn = createTallVeggie("corn");
		eggplant = createVeggie("eggplant");
		garlic = createVeggie("garlic");
		cucumber = createVeggie("cucumber");
		lentil = createVeggie("lentil");
		grape = createVeggie("grape");
		herb = createVeggie("herb");
		hops = createVeggie("hops");
		lettuce = createVeggie("lettuce");
		onion = createVeggie("onion");
		pea = createVeggie("pea");
		peanut = createVeggie("peanut");
		pineapple = createVeggie("pineapple");	
		raspberry = createVeggie("raspberry");
		soy = createVeggie("soy");
		spice = createVeggie("spice");
		strawberry = createVeggie("strawberry");
		stringbean = createVeggie("stringbean");
		tea = createVeggie("tea");
		tomato = createVeggie("tomato");
		turnip = createVeggie("turnip");
		zucchini = createVeggie("zucchini");
		// move vanilla food to this tab!
		Items.carrot.setCreativeTab(CornuCopia.tabVeggies);
		Items.melon.setCreativeTab(CornuCopia.tabVeggies);
		Items.melon_seeds.setCreativeTab(CornuCopia.tabVeggies);
		Blocks.pumpkin.setCreativeTab(CornuCopia.tabVeggies);
		Items.pumpkin_seeds.setCreativeTab(CornuCopia.tabVeggies);
		Items.potato.setCreativeTab(CornuCopia.tabVeggies);
		Items.wheat.setCreativeTab(CornuCopia.tabVeggies);
		Items.wheat_seeds.setCreativeTab(CornuCopia.tabVeggies);
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
