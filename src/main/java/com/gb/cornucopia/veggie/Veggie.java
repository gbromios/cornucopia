package com.gb.cornucopia.veggie;

import com.gb.cornucopia.CornuCopia;
import com.gb.util.WeightedArray;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.EnumPlantType;

import java.util.HashMap;
import java.util.Random;


// This class is actually, despite the name, the class for *all* planted crops.
// So long as the crop does not grow on a tree, it should be added here.
public class Veggie {
	private static final int MIN_HEIGHT = 1;
	private static final int MAX_HEIGHT = 2;

	private static final HashMap<String, Veggie> vegMap = new HashMap<>();

	public final String name;
	public final BlockVeggieCrop crop;
	public final BlockVeggieWild wild;
	public final ItemVeggieRaw raw;
	public final ItemVeggieSeed seed;

	private enum Veggies {
		artichoke(1),
		asparagus(1),
		barley(1),
		bean(1),
		beet(1),
		bell_pepper(1),
		blackberry(1),
		blueberry(1),
		broccoli(1),
		cabbage(1),
		celery(1),
		corn(2),
		cucumber(1),
		eggplant(1),
		garlic(1),
		grape(1),
		herb(1),
		hops(1),
		lentil(1),
		lettuce(1),
		onion(1),
		pea(1),
		peanut(1),
		pineapple(1),
		raspberry(1),
		soy(1),
		spice(1),
		strawberry(1),
		tomato(1),
		turnip(1),
		tea(1),
		zucchini(1);

		private final int height;

		// if height is not between min & max, set it to 1
		Veggies(int height) {
			this.height = (height >= MIN_HEIGHT && height <= MAX_HEIGHT) ? height : 1;
		}
	}

	public Veggie(final String name, final BlockVeggieCrop crop, final BlockVeggieWild wild, final ItemVeggieRaw raw, final ItemVeggieSeed seed) {
		this.name = name;
		this.crop = crop;
		this.wild = wild;
		this.raw = raw;
		this.seed = seed;

		this.crop.setDrops(this.raw, this.seed);
		this.wild.setDrops(this.raw, this.seed);
		vegMap.put(name, this);
	}

	private Veggie(final String name, final BlockVeggieCrop crop, final BlockVeggieWild wild, final ItemVeggieRaw raw) {
		this(name, crop, wild, raw, new ItemVeggieSeed(name, crop));
	}

	public Veggie(final Veggies veggie) {
		this(veggie.name(),
				veggie.height == 1 ? new BlockVeggieCrop(veggie.name())
						: new BlockVeggieCropTall(veggie.name()),
				new BlockVeggieWild(veggie.name(), EnumPlantType.Plains),
				new ItemVeggieRaw(veggie.name()));
	}

	public static Veggie getVeggie(final String name) {
		return vegMap.get(name);
	}

	public static BlockVeggieCrop getCrop(final String name) {
		return vegMap.get(name).crop;
	}

	public static BlockVeggieWild getWild(final String name) {
		return vegMap.get(name).wild;
	}

	public static ItemVeggieRaw getRaw(final String name) {
		return vegMap.get(name).raw;
	}

	public static ItemVeggieSeed getSeed(final String name) {
		return vegMap.get(name).seed;
	}

	public static void preInit() {
		for (Veggies veggie : Veggies.values()) new Veggie(veggie);

		// move vanilla food to this tab!
		Items.CARROT.setCreativeTab(CornuCopia.tabVeggies);
		Items.MELON.setCreativeTab(CornuCopia.tabVeggies);
		Items.MELON_SEEDS.setCreativeTab(CornuCopia.tabVeggies);
		Blocks.PUMPKIN.setCreativeTab(CornuCopia.tabVeggies);
		Items.PUMPKIN_SEEDS.setCreativeTab(CornuCopia.tabVeggies);
		Items.POTATO.setCreativeTab(CornuCopia.tabVeggies);
		Items.WHEAT.setCreativeTab(CornuCopia.tabVeggies);
		Items.WHEAT_SEEDS.setCreativeTab(CornuCopia.tabVeggies);
	}

	public static void init() {
	}

	private static final WeightedArray<Veggies> jungleVeggies = new WeightedArray<>();
	private static final WeightedArray<Veggies> coldVeggies = new WeightedArray<>();
	private static final WeightedArray<Veggies> forestVeggies = new WeightedArray<>();
	private static final WeightedArray<Veggies> mountainVeggies = new WeightedArray<>();
	private static final WeightedArray<Veggies> plainsVeggies = new WeightedArray<>();
	private static final WeightedArray<Veggies> dryVeggies = new WeightedArray<>();

	public static void postInit() {
		// map veggies to biomes
		jungleVeggies
				.add(Veggies.bell_pepper, 10)
				.add(Veggies.herb, 10)
				.add(Veggies.peanut, 10)
				//.add(Veggies.pineapple, 10)
				.add(Veggies.spice, 10)
				//.add(Veggies.tea, 10)
				.add(Veggies.tomato, 10)
		;

		coldVeggies
				.add(Veggies.barley, 10)
				.add(Veggies.beet, 10)
				.add(Veggies.cabbage, 10)
				.add(Veggies.onion, 10)
				.add(Veggies.turnip, 10)
				.add(Veggies.garlic, 10)
				//.add(Veggies.bean, 10)
				.add(Veggies.hops, 10)
		;

		mountainVeggies
				.add(Veggies.barley, 20)
				.add(Veggies.blackberry, 10)
				.add(Veggies.raspberry, 10)
				.add(Veggies.lentil, 20)
		;


		forestVeggies
				.add(Veggies.artichoke, 10)
				.add(Veggies.asparagus, 10)
				.add(Veggies.blackberry, 10)
				.add(Veggies.blueberry, 10)
				.add(Veggies.raspberry, 10)
				.add(Veggies.strawberry, 10)
				.add(Veggies.herb, 10)
				.add(Veggies.garlic, 10)
		//.add(Veggies.eggplant, 10)
		;

		plainsVeggies
				.add(Veggies.artichoke, 10)
				.add(Veggies.asparagus, 10)
				.add(Veggies.barley, 10)
				.add(Veggies.beet, 10)
				//.add(Veggies.broccoli, 10)
				.add(Veggies.cabbage, 10)
				//.add(Veggies.celery, 10)
				.add(Veggies.lettuce, 10)
				.add(Veggies.onion, 10)
				//.add(Veggies.bean, 10)
				//.add(Veggies.pea, 10)
				.add(Veggies.grape, 10)
				//.add(Veggies.soy, 10)
				//.add(Veggies.cucumber, 10)
				//.add(Veggies.eggplant, 10)
				//.add(Veggies.zucchini, 10)
				.add(Veggies.corn, 10)
		;

		dryVeggies
				.add(Veggies.onion, 10)
				.add(Veggies.garlic, 10)
				//.add(Veggies.bean, 15)
				.add(Veggies.lentil, 10)
				.add(Veggies.grape, 10)
				.add(Veggies.spice, 15)
		;

		// remove "unready" veggies from creative tab pls
		Enum[] disabledVeggies = new Enum[]{
				Veggies.celery, Veggies.cucumber, Veggies.pea, Veggies.tea, Veggies.zucchini, Veggies.pineapple,
				Veggies.broccoli, Veggies.soy, Veggies.eggplant, Veggies.bean
		};
		for (Enum disabled : disabledVeggies) {
			Veggie veggie = vegMap.get(disabled.name());
			veggie.raw.setCreativeTab(null);
			veggie.wild.setCreativeTab(null);
		}
	}

	public static Veggie getRandomVeggie(Random r) {
		int i = (int) (r.nextInt(vegMap.size()));
		for (Veggie veggie : vegMap.values()) {
			if (--i < 0) return veggie;
		}
		throw new RuntimeException();
	}

	public static Veggie getForBiome(Random r, Biome b) {
		if (BiomeDictionary.isBiomeOfType(b, Type.COLD)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(4) == 0) {
				return vegMap.get(coldVeggies.getRandom(r).name());
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.MOUNTAIN)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(3) == 0) {
				return vegMap.get(mountainVeggies.getRandom(r).name());
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.WET)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.SWAMP) && r.nextInt(3) > 0) {
				return null;
			}
			return vegMap.get(jungleVeggies.getRandom(r).name());
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.FOREST)) {
			if (r.nextInt(2) > 0) {
				return vegMap.get(forestVeggies.getRandom(r).name());
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.HOT) || BiomeDictionary.isBiomeOfType(b, Type.MESA)) {
			if (r.nextInt(3) > 0) {
				return vegMap.get(dryVeggies.getRandom(r).name());
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.PLAINS)) {
			return vegMap.get(plainsVeggies.getRandom(r).name());
		}

		return null;
	}
}