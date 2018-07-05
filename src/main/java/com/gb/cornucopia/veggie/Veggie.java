package com.gb.cornucopia.veggie;

import com.gb.cornucopia.CornuCopia;
import com.gb.util.WeightedArray;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Random;


// This class is actually, despite the name, the class for *all* planted crops.
// So long as the crop does not grow on a tree, it should be added here.
public class Veggie {
	private static final HashMap<String, Veggie> vegMap = new HashMap<>();
	public static Veggie artichoke;
	public static Veggie asparagus;
	public static Veggie barley;
	public static Veggie bean;
	public static Veggie beet;
	public static Veggie bell_pepper;
	public static Veggie blackberry;
	public static Veggie blueberry;
	public static Veggie broccoli;
	public static Veggie cabbage;
	public static Veggie celery;
	public static Veggie corn;
	public static Veggie cucumber;
	public static Veggie eggplant;
	public static Veggie garlic;
	public static Veggie grape;
	public static Veggie herb;
	public static Veggie hops;
	public static Veggie lentil;
	public static Veggie lettuce;
	public static Veggie onion;
	public static Veggie pea;
	public static Veggie peanut;
	public static Veggie pineapple;
	public static Veggie raspberry;
	public static Veggie soy;
	public static Veggie spice;
	public static Veggie strawberry;
	public static Veggie tea;
	public static Veggie tomato;
	public static Veggie turnip;
	public static Veggie zucchini;

	private static final int MIN_HEIGHT = 1;
	private static final int MAX_HEIGHT = 2;

	public final String name;
	public final BlockVeggieCrop crop;
	public final BlockVeggieWild wild;
	public final ItemVeggieRaw raw;
	public final ItemVeggieSeed seed;

	public Veggie(final String name, final ItemVeggieRaw raw, final ItemVeggieSeed seed, final BlockVeggieCrop crop, final BlockVeggieWild wild) {
		this.name = name;
		this.raw = raw;
		this.seed = seed;
		this.crop = crop;
		this.wild = wild;

		this.crop.setDrops(raw, seed);
		this.wild.setDrops(raw, seed);
		vegMap.put(name, this);

		GameRegistry.addShapelessRecipe(this.seed.getRegistryName(), null, new ItemStack(seed, 2), Ingredient.fromItem(raw));
	}

	private Veggie(final String name, final BlockVeggieCrop crop, final BlockVeggieWild wild, final ItemVeggieRaw raw) {
		this(name, raw, new ItemVeggieSeed(name, crop), crop, wild);
	}

	public Veggie(final String name, final int height) {
		this(name,
				height == 1 ? new BlockVeggieCrop(name)
						: new BlockVeggieCropTall(name),
				new BlockVeggieWild(name, EnumPlantType.Plains),
				new ItemVeggieRaw(name));
	}

	public Veggie(final String name) {
		this(name,
				new BlockVeggieCrop(name),
				new BlockVeggieWild(name, EnumPlantType.Plains),
				new ItemVeggieRaw(name));
	}

	public static void preInit() {
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
		corn = new Veggie("corn", 2);
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
		tea = new Veggie("tea");
		tomato = new Veggie("tomato");
		turnip = new Veggie("turnip");
		zucchini = new Veggie("zucchini");

		// move vanilla food to this tab!
		Items.CARROT.setCreativeTab(CornuCopia.tabVeggies);
		Items.MELON.setCreativeTab(CornuCopia.tabVeggies);
		Items.MELON_SEEDS.setCreativeTab(CornuCopia.tabVeggies);
		Blocks.PUMPKIN.setCreativeTab(CornuCopia.tabVeggies);
		Items.PUMPKIN_SEEDS.setCreativeTab(CornuCopia.tabVeggies);
		Items.POTATO.setCreativeTab(CornuCopia.tabVeggies);
		Items.WHEAT.setCreativeTab(CornuCopia.tabVeggies);
		Items.WHEAT_SEEDS.setCreativeTab(CornuCopia.tabVeggies);

		// remove "unready" veggies from creative tab pls
		Veggie[] disabledVeggies = new Veggie[]{
				celery, cucumber, pea, tea, zucchini, pineapple, broccoli, soy, eggplant, bean
		};

		for (Veggie disabled : disabledVeggies) {
			disabled.raw.setCreativeTab(null);
			disabled.wild.setCreativeTab(null);
			disabled.seed.setCreativeTab(null);
		}
	}

	public static void init() {
		for (Veggie v : vegMap.values()) {
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(v.wild.getWildColor(), v.wild);
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(v.crop.getCropColor(), v.crop);
		}
	}

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
				//.add(pineapple, 10)
				.add(spice, 10)
				//.add(tea, 10)
				.add(tomato, 10)
		;

		coldVeggies
				.add(barley, 10)
				.add(beet, 10)
				.add(cabbage, 10)
				.add(onion, 10)
				.add(turnip, 10)
				.add(garlic, 10)
				//.add(bean, 10)
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
		//.add(eggplant, 10)
		;

		plainsVeggies
				.add(artichoke, 10)
				.add(asparagus, 10)
				.add(barley, 10)
				.add(beet, 10)
				//.add(broccoli, 10)
				.add(cabbage, 10)
				//.add(celery, 10)
				.add(lettuce, 10)
				.add(onion, 10)
				//.add(bean, 10)
				//.add(pea, 10)
				.add(grape, 10)
				//.add(soy, 10)
				//.add(cucumber, 10)
				//.add(eggplant, 10)
				//.add(zucchini, 10)
				.add(corn, 10)
		;

		dryVeggies
				.add(onion, 10)
				.add(garlic, 10)
				//.add(bean, 15)
				.add(lentil, 10)
				.add(grape, 10)
				.add(spice, 15)
		;
	}

	public static Veggie getAnyVeggie(Random r) {
		int i = (int) (r.nextInt(vegMap.size()));
		for (Veggie veggie : vegMap.values()) {
			if (--i < 0) return veggie;
		}
		throw new RuntimeException();
	}

	public static Veggie getForBiome(Random r, Biome b) {
		if (BiomeDictionary.hasType(b, Type.COLD)) {
			if (BiomeDictionary.hasType(b, Type.FOREST) && r.nextInt(4) == 0) {
				return coldVeggies.getRandom(r);
			}
			return null;
		}

		if (BiomeDictionary.hasType(b, Type.MOUNTAIN)) {
			if (BiomeDictionary.hasType(b, Type.FOREST) && r.nextInt(3) == 0) {
				return mountainVeggies.getRandom(r);
			}
			return null;
		}

		if (BiomeDictionary.hasType(b, Type.WET)) {
			if (BiomeDictionary.hasType(b, Type.SWAMP) && r.nextInt(3) > 0) {
				return null;
			}
			return jungleVeggies.getRandom(r);
		}

		if (BiomeDictionary.hasType(b, Type.FOREST)) {
			if (r.nextInt(2) > 0) {
				return forestVeggies.getRandom(r);
			}
			return null;
		}

		if (BiomeDictionary.hasType(b, Type.HOT) || BiomeDictionary.hasType(b, Type.MESA)) {
			if (r.nextInt(3) > 0) {
				return dryVeggies.getRandom(r);
			}
			return null;
		}

		if (BiomeDictionary.hasType(b, Type.PLAINS)) {
			return plainsVeggies.getRandom(r);
		}

		return null;
	}
}