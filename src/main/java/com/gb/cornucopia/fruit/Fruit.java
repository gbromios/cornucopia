package com.gb.cornucopia.fruit;

import com.gb.cornucopia.CornuCopia;
import com.gb.util.WeightedArray;
import net.minecraft.block.BlockPlanks;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Random;

// This class is for all fruit-bearing trees. If your crop grows on a tree, add it here.
public class Fruit {
	private static final HashMap<String, Fruit> fruitMap = new HashMap<>();

	public static Fruit almond;
	public static Fruit avocado;
	public static Fruit banana;
	public static Fruit cherry;
	public static Fruit lemon;
	public static Fruit lime;
	public static Fruit orange;
	public static Fruit plum;
	public static Fruit olive;
	public static Fruit pear;
	public static Fruit date;
	public static Fruit coconut;
	public static Fruit coffee;
	public static Fruit fig;
	public static Fruit pomegranate;
	public static Fruit grapefruit;
	public static Fruit kiwi;
	public static Fruit peach;

	public final String name;
	public final BlockFruitCrop crop;
	public final BlockFruitLeaf leaf;
	public final BlockFruitSapling sapling;
	public final ItemFruitRaw raw;

	private Fruit(final String name, final BlockPlanks.EnumType wood, final BlockFruitCrop crop, final BlockFruitLeaf leaf, final BlockFruitSapling sapling, final ItemFruitRaw raw) {
		this.name = name;
		this.crop = crop;
		this.leaf = leaf;
		this.sapling = sapling;
		this.raw = raw;

		this.sapling.setTreeStates(wood, this.leaf.getDefaultState());
		this.leaf.setGrows(this.crop);
		this.crop.setLeaf(this.leaf).setDrops(this.raw, this.sapling);
		fruitMap.put(name, this);
	}

	public Fruit(final String name, final BlockPlanks.EnumType wood) {
		this(name, wood,
				new BlockFruitCrop(name),
				new BlockFruitLeaf(name),
				new BlockFruitSapling(name),
				new ItemFruitRaw(name));
	}

	public Fruit(final String name) {
		this(name,
				BlockPlanks.EnumType.OAK,
				new BlockFruitCrop(name),
				new BlockFruitLeaf(name),
				new BlockFruitSapling(name),
				new ItemFruitRaw(name));
	}

	public static void preInit() {
		almond = new Fruit("almond", BlockPlanks.EnumType.SPRUCE);
		avocado = new Fruit("avocado", BlockPlanks.EnumType.SPRUCE);
		banana = new Fruit("banana", BlockPlanks.EnumType.JUNGLE);
		cherry = new Fruit("cherry");
		coconut = new Fruit("coconut", BlockPlanks.EnumType.JUNGLE);
		coffee = new Fruit("coffee", BlockPlanks.EnumType.DARK_OAK);
		date = new Fruit("date", BlockPlanks.EnumType.ACACIA);
		fig = new Fruit("fig", BlockPlanks.EnumType.ACACIA);
		grapefruit = new Fruit("grapefruit", BlockPlanks.EnumType.SPRUCE);
		kiwi = new Fruit("kiwi", BlockPlanks.EnumType.DARK_OAK);
		lemon = new Fruit("lemon", BlockPlanks.EnumType.BIRCH);
		lime = new Fruit("lime", BlockPlanks.EnumType.BIRCH);
		olive = new Fruit("olive", BlockPlanks.EnumType.ACACIA);
		orange = new Fruit("orange", BlockPlanks.EnumType.JUNGLE);
		peach = new Fruit("peach");
		pear = new Fruit("pear");
		plum = new Fruit("plum", BlockPlanks.EnumType.BIRCH);
		pomegranate = new Fruit("pomegranate", BlockPlanks.EnumType.DARK_OAK);

		Items.APPLE.setCreativeTab(CornuCopia.tabFruit);
	}

	@SideOnly(Side.CLIENT)
	public static void init() {
		for (Fruit f : fruitMap.values()) {
			f.leaf.setGraphicsLevel(true);
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(f.leaf.getLeafColor(), f.leaf);
		}
	}

	// instance fields
	private static final WeightedArray<Fruit> jungleFruits = new WeightedArray<>();
	private static final WeightedArray<Fruit> coldFruits = new WeightedArray<>();
	private static final WeightedArray<Fruit> forestFruits = new WeightedArray<>();
	private static final WeightedArray<Fruit> mountainFruits = new WeightedArray<>();
	private static final WeightedArray<Fruit> plainsFruits = new WeightedArray<>();
	private static final WeightedArray<Fruit> dryFruits = new WeightedArray<>();

	public static void postInit() {
		jungleFruits
				.add(avocado, 10)
				.add(banana, 10)
				.add(coconut, 10)
				.add(coffee, 10)
				.add(date, 10)
				.add(fig, 10)
				.add(kiwi, 10)
				.add(lemon, 10)
				.add(lime, 10)
				.add(orange, 10)
				.add(pomegranate, 10)
		;
		coldFruits
				.add(almond, 10)
				.add(grapefruit, 10)
		;
		forestFruits
				.add(almond, 5)
				.add(cherry, 10)
				.add(peach, 20)
				.add(pear, 20)
				.add(plum, 20)
		;
		mountainFruits
				.add(almond, 10)
				.add(cherry, 10)
		;
		plainsFruits
				.add(lemon, 2)
				.add(lime, 2)
				.add(orange, 2)
				.add(peach, 15)
				.add(pear, 10)
				.add(plum, 10)
		;
		dryFruits
				.add(coffee, 10)
				.add(date, 10)
				.add(fig, 10)
				.add(grapefruit, 10)
				.add(lemon, 15)
				.add(lime, 15)
				.add(olive, 15)
				.add(orange, 10)
				.add(pomegranate, 10)
		;
	}

	public static Fruit getAny(Random r) {
		int i = (int) (r.nextInt(fruitMap.size()));
		for (Fruit fruit : fruitMap.values()) {
			if (--i < 0) return fruit;
		}
		throw new RuntimeException();
	}

	public static Fruit getForBiome(Random r, Biome b) {
		if (BiomeDictionary.isBiomeOfType(b, Type.COLD)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(8) == 0) {
				return coldFruits.getRandom(r);
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.MOUNTAIN)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(4) == 0) {
				return mountainFruits.getRandom(r);
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.WET)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.SWAMP) && r.nextInt(8) > 0) {
				return null;
			}
			return jungleFruits.getRandom(r);
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.FOREST)) {
			return forestFruits.getRandom(r);
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.HOT) || BiomeDictionary.isBiomeOfType(b, Type.MESA)) {
			if (r.nextInt(2) == 0) {
				return dryFruits.getRandom(r);
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.PLAINS)) {
			if (r.nextInt(2) == 0) {
				return plainsFruits.getRandom(r);
			}
		}

		return null;
	}
}