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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Veggie {
    private static final ArrayList<String> veggieList = new ArrayList<>(Arrays.asList(
            "artichoke", "asparagus", "barley", "bean", "beet", "bell_pepper", "blackberry", "blueberry", "broccoli",
            "cabbage", "celery", "cucumber", "eggplant", "garlic", "grape", "herb", "hops", "lentil", "lettuce", "onion",
            "pea", "peanut", "pineapple", "raspberry", "soy", "spice", "strawberry", "tea", "tomato", "turnip", "zucchini"
    ));
    private static final ArrayList<String> tallVeggieList = new ArrayList<>(Arrays.asList(
            "corn"
    ));

    private static final HashMap<String, BlockVeggieCrop> cropMap = new HashMap<>();
    private static final HashMap<String, ItemVeggieRaw> rawMap = new HashMap<>();
    private static final HashMap<String, ItemVeggieSeed> seedMap = new HashMap<>();

    public static void preInit() {
        for (String veggie : veggieList) registerCrop(veggie);
        for (String tallVeggie : tallVeggieList) registerTallCrop(tallVeggie);

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

    private static void registerCrop(String name, BlockVeggieCrop crop) {
        String cropName = String.format("veggie_%s_crop", name);
        String seedName = String.format("veggie_%s_seed", name);
        String rawName = String.format("veggie_%s_raw", name);

        ItemVeggieRaw raw = new ItemVeggieRaw(rawName);
        ItemVeggieSeed seed = new ItemVeggieSeed(seedName, crop);
        cropMap.put(cropName, crop);
        rawMap.put(rawName, raw);
        seedMap.put(seedName, seed);

    }

    private static void registerCrop(String name) {
        registerCrop(name, new BlockVeggieCrop(String.format("veggie_%s_crop", name)));
    }

    private static void registerTallCrop(String name) {
        registerCrop(name, new BlockVeggieCropTall(String.format("veggie_%s_crop", name)));
    }

    public static void init() {
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

        // remove "unready" veggies from creative tab pls
        celery.seed.setCreativeTab(null);
        celery.wild.setCreativeTab(null);
        cucumber.seed.setCreativeTab(null);
        cucumber.wild.setCreativeTab(null);
        pea.seed.setCreativeTab(null);
        pea.wild.setCreativeTab(null);
        tea.seed.setCreativeTab(null);
        tea.wild.setCreativeTab(null);
        zucchini.seed.setCreativeTab(null);
        zucchini.wild.setCreativeTab(null);
        pineapple.seed.setCreativeTab(null);
        pineapple.wild.setCreativeTab(null);
        broccoli.seed.setCreativeTab(null);
        broccoli.wild.setCreativeTab(null);
        soy.seed.setCreativeTab(null);
        soy.wild.setCreativeTab(null);
        eggplant.seed.setCreativeTab(null);
        eggplant.wild.setCreativeTab(null);
        bean.seed.setCreativeTab(null);
        bean.wild.setCreativeTab(null);

    }

    public static Veggie getAny(Random r) {
        int i = (int) (r.nextInt(cropMap.size()));
        for (Veggie v : cropMap.values()) {
            if (--i < 0) return v;
        }
        throw new RuntimeException();
    }

    public static Veggie getForBiome(Random r, Biome b) {
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
                return coldVeggies.getRandom(r);
            }
            return null;

        }

        // next mountains, 1/3 chance
        if (BiomeDictionary.isBiomeOfType(b, Type.MOUNTAIN)) {
            if (BiomeDictionary.isBiomeOfType(b, Type.FOREST) && r.nextInt(3) == 0) {
                return mountainVeggies.getRandom(r);
            }
            return null;
        }

        // jungle + swamp; could remove swamp out for something else if desired
        if (BiomeDictionary.isBiomeOfType(b, Type.WET)) {
            if (BiomeDictionary.isBiomeOfType(b, Type.SWAMP) && r.nextInt(3) > 0) {
                return null;
            }
            return jungleVeggies.getRandom(r);
        }
        // regular forest; 50/50 chance
        if (BiomeDictionary.isBiomeOfType(b, Type.FOREST)) {
            if (r.nextInt(2) > 0) {
                return forestVeggies.getRandom(r);
            }
            return null;
        }

        if (BiomeDictionary.isBiomeOfType(b, Type.HOT) || BiomeDictionary.isBiomeOfType(b, Type.MESA)) {
            if (r.nextInt(3) > 0) {
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
