package com.gb.cornucopia.veggie;

import java.util.HashMap;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.veggie.block.BlockCropTallVeggie;
import com.gb.cornucopia.veggie.block.BlockCropVeggie;
import com.gb.cornucopia.veggie.block.BlockWildVeggie;
import com.gb.cornucopia.veggie.item.ItemRawVeggie;
import com.gb.cornucopia.veggie.item.ItemSeedVeggie;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Veggies {
	//region public fields
	public static Veggie blueberry;
	public static Veggie corn;

	
	// endregion	
	protected static final HashMap<String, Veggie> vegMap = new HashMap<String, Veggie>();

	public static Veggie createTallVeggie(String name){
		return createVeggie(name,
				new ItemRawVeggie(name),
				new ItemSeedVeggie(name),
				new BlockCropTallVeggie(name),
				new BlockWildVeggie(name, EnumPlantType.Plains)
				);
		
	}
		
	public static Veggie createVeggie(String name){
		return createVeggie(name,
				new ItemRawVeggie(name),
				new ItemSeedVeggie(name),
				new BlockCropVeggie(name),
				new BlockWildVeggie(name, EnumPlantType.Plains)
				);
	}
	
	public static Veggie createVeggie(String name, ItemRawVeggie raw,  ItemSeedVeggie seed, BlockCropVeggie crop, BlockWildVeggie wild){
		// take fresh instances and save them in static fields
		Veggie vs = new Veggie(raw, seed, crop, wild);
		vegMap.put(name, vs);
		return vs;

	}

	public static void preInit(){
		// populate veggie instances one by one. guess this is as good a place as any to define this crap :I
		blueberry = createVeggie("blueberry");
		corn = createTallVeggie("corn");
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
					new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, v.raw.name), "inventory") 
				);
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
					v.seed,
					0,
					new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, v.seed.name), "inventory")
				);
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
					Item.getItemFromBlock(v.crop),
					0,
					new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, v.crop.name), "inventory")
				);
		}
	}


}
