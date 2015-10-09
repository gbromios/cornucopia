package com.gb.cornucopia.bees;

import com.gb.cornucopia.CornuCopia;
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
	
	public static void preInit(){
		bee = new ItemBee();
		queen = new ItemQueenBee();
		hive = new BlockHive();
		
		GameRegistry.registerItem(bee, "bee");
		GameRegistry.registerItem(queen,"bee_queen");
		GameRegistry.registerBlock(hive, "bee_hive");
	}
	
	public static void init(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				bee,
				0,
				new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, "bee"), "inventory") 
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
	}
}
