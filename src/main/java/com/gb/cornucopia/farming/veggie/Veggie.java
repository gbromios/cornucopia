package com.gb.cornucopia.farming.veggie;

import com.gb.cornucopia.CornuCopia;
import com.gb.util.WeightedArray;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.EnumPlantType;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Veggie {
    enum Veggies {
        artichoke, asparagus, barley, bean, beet, bell_pepper, blackberry, blueberry, broccoli, cabbage, celery, cucumber,
        eggplant, garlic, grape, herb, hops, lentil, lettuce, onion, pea, peanut, pineapple, raspberry, soy, spice,
        strawberry, tomato, turnip, tea, zucchini
    }
    enum TallVeggies {
        corn
    }

    private static final HashMap<String, BlockVeggieCrop> cropMap = new HashMap<>();
    private static final HashMap<String, BlockVeggieWild> wildMap = new HashMap<>();
    private static final HashMap<String, ItemVeggieRaw> rawMap = new HashMap<>();
    private static final HashMap<String, ItemVeggieSeed> seedMap = new HashMap<>();

    public static void preInit() {
        for (Veggies veggie : Veggies.values()) registerCrop(veggie.name());
        for (TallVeggies tallVeggie : TallVeggies.values()) registerTallCrop(tallVeggie.name());

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

    public static void init() {}

    private static void registerCrop(String name, BlockVeggieCrop crop) {
        String wildName = String.format("veggie_%s_wild", name);
        String seedName = String.format("veggie_%s_seed", name);
        String rawName = String.format("veggie_%s_raw", name);

        BlockVeggieWild wild = new BlockVeggieWild(wildName, EnumPlantType.Plains);
        ItemVeggieRaw raw = new ItemVeggieRaw(rawName);
        ItemVeggieSeed seed = new ItemVeggieSeed(seedName, crop);
        crop.setDrops(raw, seed);

        crop.setCreativeTab(CornuCopia.tabVeggies);
        raw.setCreativeTab(CornuCopia.tabVeggies);
        seed.setCreativeTab(CornuCopia.tabVeggies);

        cropMap.put(name, crop);
        wildMap.put(name, wild);
        rawMap.put(name, raw);
        seedMap.put(name, seed);

    }

    private static void registerCrop(String name) {
        registerCrop(name, new BlockVeggieCrop(String.format("veggie_%s_crop", name)));
    }

    private static void registerTallCrop(String name) {
        registerCrop(name, new BlockVeggieCropTall(String.format("veggie_%s_crop", name)));
    }

    private static final WeightedArray<Enum> jungleVeggies = new WeightedArray<>();
    private static final WeightedArray<Enum> coldVeggies = new WeightedArray<>();
    private static final WeightedArray<Enum> forestVeggies = new WeightedArray<>();
    private static final WeightedArray<Enum> mountainVeggies = new WeightedArray<>();
    private static final WeightedArray<Enum> plainsVeggies = new WeightedArray<>();
    private static final WeightedArray<Enum> dryVeggies = new WeightedArray<>();

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
                .add(TallVeggies.corn, 10)
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
        Enum[] disabledVeggies = new Enum[] {
                Veggies.celery, Veggies.cucumber, Veggies.pea, Veggies.tea, Veggies.zucchini, Veggies.pineapple,
                Veggies.broccoli, Veggies.soy, Veggies.eggplant, Veggies.bean
        };
        for(Enum veggie : disabledVeggies) {
            seedMap.get(veggie.name()).setCreativeTab(null);
            wildMap.get(veggie.name()).setCreativeTab(null);
        }
    }

    public static BlockVeggieCrop getAny(Random r) {
        int i = (int) (r.nextInt(cropMap.size()));
        for (BlockVeggieCrop v : cropMap.values()) {
            if (--i < 0) return v;
        }
        throw new RuntimeException();
    }

    public static BlockVeggieWild getForBiome(Random r, Biome b) {
        final String ts = Collections2.transform(Arrays.asList(BiomeDictionary.getTypesForBiome(b)), new Function<BiomeDictionary.Type, String>() {
            @Override
            public String apply(final BiomeDictionary.Type type) {
                return type.name();
            }
        }).toString();

        //System.out.format(" > vb = %s @ %s => ", b.biomeName, ts);

        // cold, but only if forest; 1/4 chance
        if (BiomeDictionary.isBiomeOfType(b, Type.COLD)) {
            if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(4) == 0) {
                return wildMap.get(coldVeggies.getRandom(r).name());
            }
            return null;
        }

        // next mountains, 1/3 chance
        if (BiomeDictionary.isBiomeOfType(b, Type.MOUNTAIN)) {
            if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(3) == 0) {
                return wildMap.get(mountainVeggies.getRandom(r).name());
            }
            return null;
        }

        // jungle + swamp; could remove swamp out for something else if desired
        if (BiomeDictionary.isBiomeOfType(b, Type.WET)) {
            if (BiomeDictionary.isBiomeOfType(b, Type.SWAMP) && r.nextInt(3) > 0) {
                return null;
            }
            return wildMap.get(jungleVeggies.getRandom(r).name());
        }
        // regular forest; 50/50 chance
        if (BiomeDictionary.isBiomeOfType(b, Type.FOREST)) {
            if (r.nextInt(2) > 0) {
                return wildMap.get(forestVeggies.getRandom(r).name());
            }
            return null;
        }

        if (BiomeDictionary.isBiomeOfType(b, Type.HOT) || BiomeDictionary.isBiomeOfType(b, Type.MESA)) {
            if (r.nextInt(3) > 0) {
                return wildMap.get(dryVeggies.getRandom(r).name());
            }
            return null;
        }

        if (BiomeDictionary.isBiomeOfType(b, Type.PLAINS)) {
            return wildMap.get(plainsVeggies.getRandom(r).name());
        }


        return null; // no veggies here :(
    }


}
