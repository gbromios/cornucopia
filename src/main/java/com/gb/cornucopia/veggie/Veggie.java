package com.gb.cornucopia.veggie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.util.WeightedArray;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
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

	// instance fields/methods
	public final String name;
	public final ItemVeggieRaw raw;
	public final ItemVeggieSeed seed;
	public final BlockVeggieCrop crop;
	public final BlockVeggieWild wild;

	public Veggie(final String name, final ItemVeggieRaw raw, final ItemVeggieSeed seed, final BlockVeggieCrop crop, final BlockVeggieWild wild){
		this.name = name;
		this.raw = raw;
		this.seed = seed;
		this.crop = crop;
		this.wild = wild;

		// hook up what needs hookin up
		seed.setCrop(crop);
		crop.setDrops(raw, seed);
		wild.setDrop(raw, seed);
		
		
		// add raw veggie => seed recipe for everyone
		//GameRegistry.addShapelessRecipe(new ItemStack(seed, 2), raw);

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

	public static void init() { }
	
	private static final WeightedArray<Veggie> jungleVeggies = new WeightedArray<>();
	private static final WeightedArray<Veggie> coldVeggies = new WeightedArray<>();
	private static final WeightedArray<Veggie> forestVeggies = new WeightedArray<>();
	private static final WeightedArray<Veggie> mountainVeggies = new WeightedArray<>();
	private static final WeightedArray<Veggie> plainsVeggies = new WeightedArray<>();
	private static final WeightedArray<Veggie> dryVeggies = new WeightedArray<>();

	public static void postInit() {
		// map veggies to biomes
		jungleVeggies
		.add(bell_pepper, 10)
		.add(herb, 10)
		.add(peanut, 10)
		.add(pineapple, 10)
		.add(spice, 10)
		.add(tea, 10)
		.add(tomato, 10)
		;
		
		coldVeggies
        .add(barley, 10)
        .add(beet, 10)
        .add(cabbage, 10)
        .add(onion, 10)
        .add(turnip, 10)
        .add(garlic, 10)
        .add(bean, 10)
        .add(hops, 10)
		;
		
		mountainVeggies
        .add(barley, 20)
        .add(blackberry, 10)
        .add(raspberry, 10)
        .add(lentil, 20)
		;
		
		
		forestVeggies
        .add(artichoke, 10)
        .add(asparagus, 10)
        .add(blackberry, 10)
        .add(blueberry, 10)
        .add(raspberry, 10)
        .add(strawberry, 10)
        .add(herb, 10)
        .add(garlic, 10)
        .add(eggplant, 10)
		;
		
		plainsVeggies
        .add(artichoke, 10)
        .add(asparagus, 10)
        .add(barley, 10)
        .add(beet, 10)
        .add(broccoli, 10)
        .add(cabbage, 10)
        .add(celery, 10)
        .add(lettuce, 10)
        .add(onion, 10)
        .add(bean, 10)
        .add(pea, 10)
        .add(stringbean, 10)
        .add(grape, 10)
        .add(soy, 10)
        .add(cucumber, 10)
        .add(eggplant, 10)
        .add(zucchini, 10)
        .add(corn, 10)
		;
		
		dryVeggies
        .add(onion, 10)
        .add(garlic, 10)
        .add(bean, 15)
        .add(lentil, 10)
        .add(grape, 10)
        .add(spice, 15)
        ;
	}

	public static Veggie getAny(Random r){
	    int i = (int) (r.nextInt(vegMap.size()));
	    for(Veggie v: vegMap.values()) { if (--i < 0) return v; }
	    throw new RuntimeException();
	}
	public static Veggie getForBiome(Random r, BiomeGenBase b){
		final String ts = Collections2.transform(Arrays.asList(BiomeDictionary.getTypesForBiome(b)), new Function<BiomeDictionary.Type, String>(){

	        @Override
	        public String apply(final BiomeDictionary.Type type){
	            return  type.name();
	        }

	    }).toString();
		
	//System.out.format(" > vb = %s @ %s => ", b.biomeName, ts);
		
		// cold, but only if forest; 1/4 chance
		if (BiomeDictionary.isBiomeOfType(b, Type.COLD)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(4) == 0 ) {
				return coldVeggies.getRandom(r);
			}
			return null;
			
		}
		
		// next mountains, 1/3 chance
		if (BiomeDictionary.isBiomeOfType(b, Type.MOUNTAIN)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(3) == 0 ) {
				return mountainVeggies.getRandom(r);
			}
			return null;
		}

		// jungle + swamp; could remove swamp out for something else if desired
		if (BiomeDictionary.isBiomeOfType(b, Type.WET)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.SWAMP) && r.nextInt(3) > 0 ) {
				return null;
			}
			return jungleVeggies.getRandom(r);
		}
		// regular forest; 50/50 chance
		if (BiomeDictionary.isBiomeOfType(b, Type.FOREST)) {
			if (r.nextInt(2) > 0 ) {
				return forestVeggies.getRandom(r);
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.HOT) || BiomeDictionary.isBiomeOfType(b, Type.MESA)) {
			if (r.nextInt(3) > 0 ) {
				return dryVeggies.getRandom(r);
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.PLAINS)) {
			return plainsVeggies.getRandom(r);
		}
		
		
		return null; // no veggies here :(
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
