package com.gb.cornucopia.bees;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.bees.block.BlockApiary;
import com.gb.cornucopia.bees.block.BlockHive;
import com.gb.cornucopia.bees.item.ItemBee;
import com.gb.cornucopia.bees.item.ItemQueenBee;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Bees {
	public static ItemBee bee;
	public static ItemQueenBee queen;
	public static BlockHive hive;
	public static BlockApiary apiary;
	
	public static void preInit(){
		bee = new ItemBee("worker");
		queen = new ItemQueenBee("queen");
		hive = new BlockHive();
		apiary = new BlockApiary();
		
		


	}
	
	public static void init(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				bee,
				0,
				new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, "bee_worker"), "inventory") 
			);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				queen,
				0,
				new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, "bee_queen"), "inventory") 
			);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				Item.getItemFromBlock(hive),
				0,
				new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, "bee_hive"), "inventory") 
			);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				Item.getItemFromBlock(apiary),
				0,
				new ModelResourceLocation(String.format("minecraft:oak_planks", "bee_hive"), "inventory") // placeholder 
			);
	}
}
