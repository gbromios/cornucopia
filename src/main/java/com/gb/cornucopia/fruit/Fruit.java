package com.gb.cornucopia.fruit;

import com.gb.cornucopia.CornuCopia;
import com.gb.util.WeightedArray;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Items;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.HashMap;
import java.util.Random;

// This class is for all fruit-bearing trees. If your crop grows on a tree, add it here.
public class Fruit {
	public static final HashMap<String, Fruit> fruitMap = new HashMap<>();

	public final String name;
	public final BlockFruitCrop crop;
	public final BlockFruitLeaf leaf;
	public final BlockFruitSapling sapling;
	public final ItemFruitRaw raw;

	public enum Fruits {
		almond(BlockPlanks.EnumType.SPRUCE),
		avocado(BlockPlanks.EnumType.SPRUCE),
		banana(BlockPlanks.EnumType.JUNGLE),
		cherry(BlockPlanks.EnumType.OAK),
		coconut(BlockPlanks.EnumType.JUNGLE),
		coffee(BlockPlanks.EnumType.DARK_OAK),
		date(BlockPlanks.EnumType.ACACIA),
		fig(BlockPlanks.EnumType.ACACIA),
		grapefruit(BlockPlanks.EnumType.SPRUCE),
		kiwi(BlockPlanks.EnumType.DARK_OAK),
		lemon(BlockPlanks.EnumType.BIRCH),
		lime(BlockPlanks.EnumType.BIRCH),
		olive(BlockPlanks.EnumType.ACACIA),
		orange(BlockPlanks.EnumType.JUNGLE),
		peach(BlockPlanks.EnumType.OAK),
		pear(BlockPlanks.EnumType.OAK),
		plum(BlockPlanks.EnumType.BIRCH),
		pomegranate(BlockPlanks.EnumType.DARK_OAK);

		private final BlockPlanks.EnumType wood;

		Fruits(BlockPlanks.EnumType wood) {
			this.wood = wood;
		}
	}

	public Fruit(final String name, final BlockPlanks.EnumType wood, final BlockFruitCrop crop, final BlockFruitLeaf leaf, final BlockFruitSapling sapling, final ItemFruitRaw raw) {
		this.name = name;
		this.crop = crop;
		this.leaf = leaf;
		this.sapling = sapling;
		this.raw = raw;

		this.crop.setLeaf(this.leaf).setDrops(this.raw, this.sapling);
		this.leaf.setGrows(this.crop);
		this.sapling.setTreeStates(wood, this.leaf.getDefaultState());
		fruitMap.put(name, this);
	}

	public Fruit(final Fruits fruit) {
		this(fruit.name(), fruit.wood,
				new BlockFruitCrop(fruit.name()),
				new BlockFruitLeaf(fruit.name()),
				new BlockFruitSapling(fruit.name()),
				new ItemFruitRaw(fruit.name()));
	}

	public static BlockFruitCrop getCrop(final String name) {
		return fruitMap.get(name).crop;
	}

	public static BlockFruitLeaf getLeaf(final String name) {
		return fruitMap.get(name).leaf;
	}

	public static BlockFruitSapling getSapling(final String name) {
		return fruitMap.get(name).sapling;
	}

	public static ItemFruitRaw getRaw(final String name) {
		return fruitMap.get(name).raw;
	}

	public static void preInit() {
		for (Fruits fruit : Fruits.values()) new Fruit(fruit);

		Items.APPLE.setCreativeTab(CornuCopia.tabFruit);
	}

	public static void init() {
		// TODO gbro what is this?
        /*for (BlockFruitCrop f : cropMap.values()) {
            f.leaf.setGraphicsLevel(true);
        }*/
	}

	// instance fields
	private static final WeightedArray<Fruits> jungleFruits = new WeightedArray<>();
	private static final WeightedArray<Fruits> coldFruits = new WeightedArray<>();
	private static final WeightedArray<Fruits> forestFruits = new WeightedArray<>();
	private static final WeightedArray<Fruits> mountainFruits = new WeightedArray<>();
	private static final WeightedArray<Fruits> plainsFruits = new WeightedArray<>();
	private static final WeightedArray<Fruits> dryFruits = new WeightedArray<>();

	public static void postInit() {
		jungleFruits
				.add(Fruits.avocado, 10)
				.add(Fruits.banana, 10)
				.add(Fruits.coconut, 10)
				.add(Fruits.coffee, 10)
				.add(Fruits.date, 10)
				.add(Fruits.fig, 10)
				.add(Fruits.kiwi, 10)
				.add(Fruits.lemon, 10)
				.add(Fruits.lime, 10)
				.add(Fruits.orange, 10)
				.add(Fruits.pomegranate, 10)
		;
		coldFruits
				.add(Fruits.almond, 10)
				.add(Fruits.grapefruit, 10)
		;
		forestFruits
				.add(Fruits.almond, 5)
				.add(Fruits.cherry, 10)
				.add(Fruits.peach, 20)
				.add(Fruits.pear, 20)
				.add(Fruits.plum, 20)
		;
		mountainFruits
				.add(Fruits.almond, 10)
				.add(Fruits.cherry, 10)
		;
		plainsFruits
				.add(Fruits.lemon, 2)
				.add(Fruits.lime, 2)
				.add(Fruits.orange, 2)
				.add(Fruits.peach, 15)
				.add(Fruits.pear, 10)
				.add(Fruits.plum, 10)
		;
		dryFruits
				.add(Fruits.coffee, 10)
				.add(Fruits.date, 10)
				.add(Fruits.fig, 10)
				.add(Fruits.grapefruit, 10)
				.add(Fruits.lemon, 15)
				.add(Fruits.lime, 15)
				.add(Fruits.olive, 15)
				.add(Fruits.orange, 10)
				.add(Fruits.pomegranate, 10)
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
				return fruitMap.get(coldFruits.getRandom(r).name());
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.MOUNTAIN)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(4) == 0) {
				return fruitMap.get(mountainFruits.getRandom(r).name());
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.WET)) {
			if (BiomeDictionary.isBiomeOfType(b, Type.SWAMP) && r.nextInt(8) > 0) {
				return null;
			}
			return fruitMap.get(jungleFruits.getRandom(r).name());
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.FOREST)) {
			return fruitMap.get(forestFruits.getRandom(r).name());
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.HOT) || BiomeDictionary.isBiomeOfType(b, Type.MESA)) {
			if (r.nextInt(2) == 0) {
				return fruitMap.get(dryFruits.getRandom(r).name());
			}
			return null;
		}

		if (BiomeDictionary.isBiomeOfType(b, Type.PLAINS)) {
			if (r.nextInt(2) == 0) {
				return fruitMap.get(plainsFruits.getRandom(r).name());
			}
		}

		return null;
	}
}