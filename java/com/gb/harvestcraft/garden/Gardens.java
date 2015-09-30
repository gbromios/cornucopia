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
		berry = new BlockGarden("garden_berry", EnumPlantType.Plains, new BiomeGenBase[]{})
				.addVeggie(Veggies.blueberry);
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
//		registerRenderer(garden_gourd);
//		registerRenderer(garden_grass);
//		registerRenderer(garden_ground);
//		registerRenderer(garden_herb);
//		registerRenderer(garden_leafy);
//		registerRenderer(garden_stalk);
//		registerRenderer(garden_textile);
//		registerRenderer(garden_tropical);
//		registerRenderer(garden_desert);
//		registerRenderer(garden_mushroom);
//		registerRenderer(garden_water);

	}

	private static void registerRenderer(Block b){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(Item.getItemFromBlock(b), 0, new ModelResourceLocation(HarvestCraft.MODID + ':' + b.getUnlocalizedName().substring(5), "inventory"));
	}


}
