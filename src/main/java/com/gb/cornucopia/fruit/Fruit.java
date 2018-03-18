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
    public enum Fruits {
        almond("SPRUCE"), avocado("SPRUCE"), banana("JUNGLE"), cherry("OAK"), coconut("JUNGLE"), coffee("DARK_OAK"),
        date("ACACIA"), fig("ACACIA"), grapefruit("SPRUCE"), kiwi("DARK_OAK"), lemon("BIRCH"), lime("BIRCH"),
        olive("ACACIA"), orange("JUNGLE"), peach("OAK"), pear("OAK"), plum("BIRCH"), pomegranate("DARK_OAK");

        private final BlockPlanks.EnumType wood;
        Fruits(String type) {
            this.wood = BlockPlanks.EnumType.valueOf(type.toUpperCase());
        }
    }

    // static fields
    public static final HashMap<String, BlockFruitCrop> cropMap = new HashMap<>();
    public static final HashMap<String, BlockFruitLeaf> leafMap = new HashMap<>();
    public static final HashMap<String, BlockFruitSapling> saplingMap = new HashMap<>();
    public static final HashMap<String, ItemFruitRaw> rawMap = new HashMap<>();

    public static void preInit() {
        for (Fruits fruit : Fruits.values()) registerFruit(fruit.name(), fruit.wood);

        Items.APPLE.setCreativeTab(CornuCopia.tabFruit);
    }

    public static void registerFruit(final String name, final BlockPlanks.EnumType wood) {
        BlockFruitCrop crop = new BlockFruitCrop(name);
        BlockFruitLeaf leaf = new BlockFruitLeaf(name);
        BlockFruitSapling sapling = new BlockFruitSapling(name);
        ItemFruitRaw raw = new ItemFruitRaw(name);

        // hook up needfuls
        sapling.setTreeStates(wood, leaf.getDefaultState());
        leaf.setGrows(crop);
        crop.setLeaf(leaf).setDrops(raw, sapling);

        cropMap.put(name, crop);
        leafMap.put(name, leaf);
        saplingMap.put(name, sapling);
        rawMap.put(name, raw);
    }

    public static void init() {
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

    public static BlockFruitCrop getAny(Random r) {
        int i = (int) (r.nextInt(cropMap.size()));
        for (BlockFruitCrop fruit : cropMap.values()) {
            if (--i < 0) return fruit;
        }
        throw new RuntimeException();
    }

    public static BlockFruitCrop getForBiome(Random r, Biome b) {
        if (BiomeDictionary.isBiomeOfType(b, Type.COLD)) {
            if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(8) == 0) {
                return cropMap.get(coldFruits.getRandom(r).name());
            }
            return null;

        }

        if (BiomeDictionary.isBiomeOfType(b, Type.MOUNTAIN)) {
            if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(4) == 0) {
                return cropMap.get(mountainFruits.getRandom(r).name());
            }
            return null;
        }

        if (BiomeDictionary.isBiomeOfType(b, Type.WET)) {
            if (BiomeDictionary.isBiomeOfType(b, Type.SWAMP) && r.nextInt(8) > 0) {
                return null;
            }
            return cropMap.get(jungleFruits.getRandom(r).name());
        }

        if (BiomeDictionary.isBiomeOfType(b, Type.FOREST)) {
            return cropMap.get(forestFruits.getRandom(r).name());
        }

        if (BiomeDictionary.isBiomeOfType(b, Type.HOT) || BiomeDictionary.isBiomeOfType(b, Type.MESA)) {
            if (r.nextInt(2) == 0) {
                return cropMap.get(dryFruits.getRandom(r).name());
            }
            return null;
        }

        if (BiomeDictionary.isBiomeOfType(b, Type.PLAINS)) {
            if (r.nextInt(2) == 0) {
                return cropMap.get(plainsFruits.getRandom(r).name());
            }
        }

        return null;
    }
}