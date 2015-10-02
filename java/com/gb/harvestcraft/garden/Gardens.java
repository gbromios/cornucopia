package com.gb.harvestcraft.garden;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.EnumPlantType;

import static net.minecraftforge.common.BiomeDictionary.Type;

import java.util.Arrays;

import com.gb.harvestcraft.HarvestCraft;
import com.gb.harvestcraft.garden.block.BlockGarden;
import com.gb.harvestcraft.veggie.Veggies;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;


public final class Gardens {

	// garden block static fields
	public static BlockGarden allium;
	public static BlockGarden berry;
	public static BlockGarden gourd;
	public static BlockGarden grass;
	public static BlockGarden ground;
	public static BlockGarden herb;
	public static BlockGarden leafy;
	public static BlockGarden stalk;
	public static BlockGarden textile;
	public static BlockGarden tropical;
	public static BlockGarden desert;
	public static BlockGarden mushroom;
	public static BlockGarden water;


	public static void preInit(){
		allium = new BlockGarden("allium", EnumPlantType.Plains)
				.addVeggie(Veggies.onion)
				.addVeggie(Veggies.garlic)
				.addVeggie(Veggies.parsnip)
				.addVeggie(Veggies.scallion)
				.addVeggie(Veggies.leek)
				;

		berry = new BlockGarden("berry", EnumPlantType.Plains)
				.addVeggie(Veggies.blueberry)
				.addVeggie(Veggies.blackberry)
				.addVeggie(Veggies.blueberry)
				.addVeggie(Veggies.candleberry)
				.addVeggie(Veggies.raspberry)
				.addVeggie(Veggies.strawberry)
				;

		gourd = new BlockGarden("gourd", EnumPlantType.Plains)
				.addVeggie(Veggies.cantaloupe)
				.addVeggie(Veggies.wintersquash)
				.addVeggie(Veggies.zucchini)
				.addVeggie(Veggies.bean)
				.addVeggie(Veggies.peanut)
				.addVeggie(Veggies.sweetpotato)
				;

		grass = new BlockGarden("grass", EnumPlantType.Plains)
				.addVeggie(Veggies.asparagus)
				.addVeggie(Veggies.barley)
				.addVeggie(Veggies.oats)
				.addVeggie(Veggies.rye)
				.addVeggie(Veggies.corn)
				;

		// clearly over represented...
		ground = new BlockGarden("ground", EnumPlantType.Plains)
				.addVeggie(Veggies.bean)
				.addVeggie(Veggies.brusselsprout)
				.addVeggie(Veggies.cabbage)
				.addVeggie(Veggies.beet)
				.addVeggie(Veggies.radish)
				.addVeggie(Veggies.rutabaga)
				.addVeggie(Veggies.turnip)
				;

		herb = new BlockGarden("herb", EnumPlantType.Plains)
				.addVeggie(Veggies.garlic)
				.addVeggie(Veggies.ginger)
				.addVeggie(Veggies.spiceleaf)
				.addVeggie(Veggies.tea)
				.addVeggie(Veggies.coffee)
				.addVeggie(Veggies.mustard)
				;

		leafy = new BlockGarden("leafy", EnumPlantType.Plains)
				.addVeggie(Veggies.broccoli)
				.addVeggie(Veggies.cauliflower)
				.addVeggie(Veggies.lettuce)
				.addVeggie(Veggies.artichoke)
				.addVeggie(Veggies.spinach)
				.addVeggie(Veggies.cucumber)
				.addVeggie(Veggies.celery)
				.addVeggie(Veggies.rhubarb)
				;

		stalk = new BlockGarden("stalk", EnumPlantType.Plains)
				.addVeggie(Veggies.bean)
				.addVeggie(Veggies.soybean)
				.addVeggie(Veggies.bellpepper)
				.addVeggie(Veggies.eggplant)
				.addVeggie(Veggies.okra)
				.addVeggie(Veggies.peas)
				.addVeggie(Veggies.tomato)
				.addVeggie(Veggies.chilipepper)
				;

		textile = new BlockGarden("textile", EnumPlantType.Plains)
				.addVeggie(Veggies.cotton)
				;

		tropical = new BlockGarden("tropical", EnumPlantType.Plains)
				.addVeggie(Veggies.pineapple)
				.addVeggie(Veggies.grape)
				.addVeggie(Veggies.kiwi)
				.addVeggie(Veggies.bambooshoot)
				.addVeggie(Veggies.chilipepper)
				;

		desert = new BlockGarden("desert", EnumPlantType.Desert)
				.addVeggie(Veggies.cactusfruit)
				;

		mushroom = new BlockGarden("mushroom", EnumPlantType.Cave)
				.addVeggie(Veggies.whitemushroom)
				;

		water = new BlockGarden("water", EnumPlantType.Water)
				.addVeggie(Veggies.cranberry)
				.addVeggie(Veggies.rice)
				.addVeggie(Veggies.seaweed)
				;


		/*
				.addVeggie(Veggies.get("blackberry"))
				.addVeggie(Veggies.get("blueberry"))
				.addVeggie(Veggies.get("candleberry"))
				.addVeggie(Veggies.get("raspberry"))
				.addVeggie(Veggies.get("strawberry"))
				;

		garden_gourd = new BlockGarden("garden_gourd", EnumPlantType.Plains)
				.addVeggie(Veggies.get("cantaloupe"))
				.addVeggie(Veggies.get("cucumber"))
				.addVeggie(Veggies.get("wintersquash"))
				.addVeggie(Veggies.get("zucchini"))
				;

		garden_grass = new BlockGarden("garden_grass", EnumPlantType.Plains)
				.addVeggie(Veggies.get("asparagus"))
				.addVeggie(Veggies.get("barley"))
				.addVeggie(Veggies.get("oats"))
				.addVeggie(Veggies.get("rye"))
				.addVeggie(Veggies.get("corn"))
				.addVeggie(Veggies.get("bambooshoot"))
				;

		garden_ground = new BlockGarden("garden_ground", EnumPlantType.Plains)
				.addVeggie(Veggies.get("beet"))
				.addVeggie(Veggies.get("onion"))
				.addVeggie(Veggies.get("parsnip"))
				.addVeggie(Veggies.get("peanut"))
				.addVeggie(Veggies.get("radish"))
				.addVeggie(Veggies.get("rutabaga"))
				.addVeggie(Veggies.get("sweetpotato"))
				.addVeggie(Veggies.get("turnip"))
				.addVeggie(Veggies.get("rhubarb"))
				;

		garden_herb = new BlockGarden("garden_herb", EnumPlantType.Plains)
				.addVeggie(Veggies.get("celery"))
				.addVeggie(Veggies.get("garlic"))
				.addVeggie(Veggies.get("ginger"))
				.addVeggie(Veggies.get("spiceleaf"))
				.addVeggie(Veggies.get("tea"))
				.addVeggie(Veggies.get("coffee"))
				.addVeggie(Veggies.get("mustard"))
				;

		garden_leafy = new BlockGarden("garden_leafy", EnumPlantType.Plains)
				.addVeggie(Veggies.get("broccoli"))
				.addVeggie(Veggies.get("cauliflower"))
				.addVeggie(Veggies.get("leek"))
				.addVeggie(Veggies.get("lettuce"))
				.addVeggie(Veggies.get("scallion"))
				.addVeggie(Veggies.get("artichoke"))
				.addVeggie(Veggies.get("brusselsprout"))
				.addVeggie(Veggies.get("cabbage"))
				.addVeggie(Veggies.get("spinach"))
				;

		garden_stalk = new BlockGarden("garden_stalk", EnumPlantType.Plains)
				.addVeggie(Veggies.get("bean"))
				.addVeggie(Veggies.get("soybean"))
				.addVeggie(Veggies.get("bellpepper"))
				.addVeggie(Veggies.get("chilipepper"))
				.addVeggie(Veggies.get("eggplant"))
				.addVeggie(Veggies.get("okra"))
				.addVeggie(Veggies.get("peas"))
				.addVeggie(Veggies.get("tomato"))
				;

		garden_textile = new BlockGarden("garden_textile", EnumPlantType.Plains)
				.addVeggie(Veggies.get("cotton"))
				;

		garden_tropical = new BlockGarden("garden_tropical", EnumPlantType.Plains)
				.addVeggie(Veggies.get("pineapple"))
				.addVeggie(Veggies.get("grape"))
				.addVeggie(Veggies.get("kiwi"))
				;


		garden_desert = new BlockGarden("garden_desert", EnumPlantType.Desert)
				.addVeggie(Veggies.get("cactusfruit"))
				;

		garden_mushroom = new BlockGarden("garden_mushroom", EnumPlantType.Cave)
				.addVeggie(Veggies.get("whitemushroom"))
				;

		garden_water = new BlockGarden("garden_water", EnumPlantType.Water)
				.addVeggie(Veggies.get("cranberry"))
				.addVeggie(Veggies.get("rice"))
				.addVeggie(Veggies.get("seaweed"))
				;
		 */
	}



	public static void init(){
		registerRenderer(berry);
		registerRenderer(gourd);
		registerRenderer(grass);
		registerRenderer(ground);
		registerRenderer(herb);
		registerRenderer(leafy);
		registerRenderer(stalk);
		registerRenderer(textile);
		registerRenderer(tropical);
		registerRenderer(desert);
		registerRenderer(mushroom);
		registerRenderer(water);

	}

	private static void registerRenderer(BlockGarden b){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(Item.getItemFromBlock(b), 0, new ModelResourceLocation(HarvestCraft.MODID + ':' + b.name, "inventory"));
	}


}
