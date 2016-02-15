package com.gb.cornucopia.veggie;

import java.util.HashMap;

import com.gb.cornucopia.CornuCopia;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Veggie {
	// static fields/methods
	private static final HashMap<String, Veggie> vegMap = new HashMap<>();
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
	public static Veggie hops;
	public static Veggie corn;

	public static void preInit(){
		artichoke = new Veggie("artichoke");
		asparagus = new Veggie("asparagus");
		barley = new Veggie("barley");
		beet = new Veggie("beet");
		bean = new Veggie("bean");
		bell_pepper = new Veggie("bell_pepper");
		blackberry = new Veggie("blackberry");
		blueberry = new Veggie("blueberry");
		broccoli = new Veggie("broccoli");
		cabbage = new Veggie("cabbage");
		celery = new Veggie("celery");
		corn =  createTallVeggie("corn");
		eggplant = new Veggie("eggplant");
		garlic = new Veggie("garlic");
		cucumber = new Veggie("cucumber");
		lentil = new Veggie("lentil");
		grape = new Veggie("grape");
		herb = new Veggie("herb");
		hops = new Veggie("hops");
		lettuce = new Veggie("lettuce");
		onion = new Veggie("onion");
		pea = new Veggie("pea");
		peanut = new Veggie("peanut");
		pineapple = new Veggie("pineapple");	
		raspberry = new Veggie("raspberry");
		soy = new Veggie("soy");
		spice = new Veggie("spice");
		strawberry = new Veggie("strawberry");
		stringbean = new Veggie("stringbean");
		tea = new Veggie("tea");
		tomato = new Veggie("tomato");
		turnip = new Veggie("turnip");
		zucchini = new Veggie("zucchini");
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
		// add raw veggie => seed recipe for everyone
		for (Veggie v : vegMap.values()) {
			// 1 veggie = 2 seeds. 
			GameRegistry.addShapelessRecipe(new ItemStack(v.seed, 2), v.raw);

		}
	}

	// instance fields/methods
	public final ItemVeggieRaw raw;
	public final ItemVeggieSeed seed;
	public final BlockVeggieCrop crop;
	public final BlockVeggieWild wild;

	public Veggie(final String name, final ItemVeggieRaw raw, final ItemVeggieSeed seed, final BlockVeggieCrop crop, final BlockVeggieWild wild){
		this.raw = raw;
		this.seed = seed;
		this.crop = crop;
		this.wild = wild;

		// hook up what needs hookin up
		seed.setCrop(crop);
		crop.setDrops(raw, seed);
		wild.setDrop(raw, seed);

		vegMap.put(name, this);
	}

	public Veggie(final String name){
		this(name,
			new ItemVeggieRaw(name),
			new ItemVeggieSeed(name),
			new BlockVeggieCrop(name),
			new BlockVeggieWild(name, EnumPlantType.Plains)
			);
	}

	public static Veggie createTallVeggie(final String name){
		return new Veggie(name,
			new ItemVeggieRaw(name),
			new ItemVeggieSeed(name),
			new BlockVeggieCropTall(name),
			new BlockVeggieWild(name, EnumPlantType.Plains)
			);
	}

}
