package com.gb.harvestcraft.veggie;

import java.util.HashMap;

import com.gb.harvestcraft.HarvestCraft;
import com.gb.harvestcraft.veggie.block.BlockCropVeggie;
import com.gb.harvestcraft.veggie.item.ItemRawVeggie;
import com.gb.harvestcraft.veggie.item.ItemSeedVeggie;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Veggies {
	/* every Veggie needs:
	 * 
	 *   1.  Veggies.vegMap.put("[name]", new Veggie("[name]"); - instantiation in Veggies.init() (subject to change, this could be done dynamically from config data...)
	 *   
	 *   2.  public static Veggie [name]; - a static field definition in Veggies (subject to change, would like to use a hashmap instead)
	 *   
	 *   3.  Veggies.[name] = Veggies.get("name"); - assign the instance to the aforementioned field. Stupid, yes, but I'm hedging my bets here. easily removed if I confirm it's plausible.
	 *   
	 *   4.  assets.harvestcraft.textures.items.raw_[name].png - texture for raw veggie item
	 *    
	 *   5.  assets.harvestcraft.models.item.raw_[name].json - model for raw veggie item
	 *   
	 *   6.  assets.harvestcraft.textures.items.seed_[name].png - model for this veggie's seed item
	 *   
	 *   7.  assets.harvestcraft.models.item.seed_[name].json - model for this veggie's seed item
	 *   
	 *   8.  assets.harvestcraft.blockstates.crop_[name].json - blocksstate definition specify all 4 crop block models for
	 *   
	 *   9.  assets.harvestcraft.models.block.crop_[name]_[0..3].json - model for each crop block variant
	 *   
	 *   10. assets.harvestcraft.models.item.crop_[name].json - model for cropBlock's item. this is only here so the (fully grown) crops can be selected in creative mode.
	 *   
	 *   11. assets.harvestcraft.textures.blocks._crop_[name]_[0..3].png- 4 texture file for each stage of crop growth 
	 *      
	 * */

	//region public fields
	public static Veggie artichoke;
	public static Veggie asparagus;
	public static Veggie bambooshoot;
	public static Veggie barley;
	public static Veggie bean;
	public static Veggie beet;
	public static Veggie bellpepper;
	public static Veggie blackberry;
	public static Veggie blueberry;
	public static Veggie broccoli;
	public static Veggie brusselsprout;
	public static Veggie cabbage;
	public static Veggie cactusfruit;
	public static Veggie candleberry;
	public static Veggie cantaloupe;
	public static Veggie cauliflower;
	public static Veggie celery;
	public static Veggie chilipepper;
	public static Veggie coffee;
	public static Veggie corn;
	public static Veggie cotton;
	public static Veggie cranberry;
	public static Veggie cucumber;
	public static Veggie edibleroot;
	public static Veggie eggplant;
	public static Veggie garlic;
	public static Veggie ginger;
	public static Veggie grape;
	public static Veggie kiwi;
	public static Veggie leek;
	public static Veggie lettuce;
	public static Veggie mustard;
	public static Veggie oats;
	public static Veggie okra;
	public static Veggie onion;
	public static Veggie parsnip;
	public static Veggie peanut;
	public static Veggie peas;
	public static Veggie pineapple;
	public static Veggie radish;
	public static Veggie raspberry;
	public static Veggie rhubarb;
	public static Veggie rice;
	public static Veggie rutabaga;
	public static Veggie rye;
	public static Veggie scallion;
	public static Veggie seaweed;
	public static Veggie soybean;
	public static Veggie spiceleaf;
	public static Veggie spinach;
	public static Veggie strawberry;
	public static Veggie sweetpotato;
	public static Veggie tea;
	public static Veggie tomato;
	public static Veggie turnip;
	public static Veggie whitemushroom;
	public static Veggie wintersquash;
	public static Veggie zucchini;


	
	// endregion
	
	protected static final HashMap<String, Veggie> vegMap = new HashMap<String, Veggie>();

	public static Veggie createVeggie(String name, ItemRawVeggie iRaw,  ItemSeedVeggie iSeed, BlockCropVeggie bCrop){
		// take fresh instances and save them in static fields
		Veggie vs = new Veggie(iRaw, iSeed, bCrop);
		vegMap.put(name, vs);
		return vs;

	}
	
	public static Veggie createVeggie(String name){
		return createVeggie(name,
				new ItemRawVeggie(name),
				new ItemSeedVeggie(name),
				new BlockCropVeggie(name)
				);
	}

	public static void preInit(){
		// populate veggie instances one by one. guess this is as good a place as any to define this crap :I
		artichoke = createVeggie("artichoke");
		asparagus = createVeggie("asparagus");
		bambooshoot = createVeggie("bambooshoot");
		barley = createVeggie("barley");
		bean = createVeggie("bean");
		beet = createVeggie("beet");
		bellpepper = createVeggie("bellpepper");
		blackberry = createVeggie("blackberry");
		blueberry = createVeggie("blueberry");
		broccoli = createVeggie("broccoli");
		brusselsprout = createVeggie("brusselsprout");
		cabbage = createVeggie("cabbage");
		cactusfruit = createVeggie("cactusfruit");
		candleberry = createVeggie("candleberry");
		cantaloupe = createVeggie("cantaloupe");
		cauliflower = createVeggie("cauliflower");
		celery = createVeggie("celery");
		chilipepper = createVeggie("chilipepper");
		coffee = createVeggie("coffee");
		corn = createVeggie("corn");
		cotton = createVeggie("cotton");
		cranberry = createVeggie("cranberry");
		cucumber = createVeggie("cucumber");
		eggplant = createVeggie("eggplant");
		garlic = createVeggie("garlic");
		ginger = createVeggie("ginger");
		grape = createVeggie("grape");
		kiwi = createVeggie("kiwi");
		leek = createVeggie("leek");
		lettuce = createVeggie("lettuce");
		mustard = createVeggie("mustard");
		oats = createVeggie("oats");
		okra = createVeggie("okra");
		onion = createVeggie("onion");
		parsnip = createVeggie("parsnip");
		peanut = createVeggie("peanut");
		peas = createVeggie("peas");
		pineapple = createVeggie("pineapple");
		radish = createVeggie("radish");
		raspberry = createVeggie("raspberry");
		rhubarb = createVeggie("rhubarb");
		rice = createVeggie("rice");
		rutabaga = createVeggie("rutabaga");
		rye = createVeggie("rye");
		scallion = createVeggie("scallion");
		seaweed = createVeggie("seaweed");
		soybean = createVeggie("soybean");
		spiceleaf = createVeggie("spiceleaf");
		spinach = createVeggie("spinach");
		strawberry = createVeggie("strawberry");
		sweetpotato = createVeggie("sweetpotato");
		tea = createVeggie("tea");
		tomato = createVeggie("tomato");
		turnip = createVeggie("turnip");
		whitemushroom = createVeggie("whitemushroom");
		wintersquash = createVeggie("wintersquash");
		zucchini = createVeggie("zucchini");


	}

	public static void init(){
		createModels();
		addRawToSeedRecipes();
	}


	private static void addRawToSeedRecipes() {
		for (Veggie v : vegMap.values()) {
			// 1 veggie = 2 seeds. 
			GameRegistry.addShapelessRecipe(new ItemStack(v.seed, 2), v.raw);
			
		}
	}

	private static void createModels(){
		for (Veggie v : vegMap.values()) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
					v.raw,
					0,
					new ModelResourceLocation(String.format("%s:%s", HarvestCraft.MODID, v.raw.name), "inventory") 
				);
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
					v.seed,
					0,
					new ModelResourceLocation(String.format("%s:%s", HarvestCraft.MODID, v.seed.name), "inventory")
				);
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
					Item.getItemFromBlock(v.crop),
					0,
					new ModelResourceLocation(String.format("%s:%s", HarvestCraft.MODID, v.crop.name), "inventory")
				);
		}
	}


}
