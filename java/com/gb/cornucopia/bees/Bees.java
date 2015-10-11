package com.gb.cornucopia.bees;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.bees.block.BlockApiary;
import com.gb.cornucopia.bees.block.BlockHive;
import com.gb.cornucopia.bees.item.ItemBee;
import com.gb.cornucopia.bees.item.ItemQueenBee;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Bees {
	public static ItemBee bee;
	public static ItemQueenBee queen;
	
	public static Item honeycomb;
	public static Item waxcomb;
	public static Item royal_jelly;
	public static Item tiny_crown;
	
	
	public static BlockHive hive;
	public static BlockApiary apiary;
	public static BlockBush royal_bloom;
	
	public static void preInit(){
		bee = new ItemBee("worker");
		queen = new ItemQueenBee("queen");
		
		// genericass items for now, worry aobut real classes later
		honeycomb = new Item().setUnlocalizedName("bee_honeycomb").setCreativeTab(CornuCopia.tabBees);
		waxcomb = new Item().setUnlocalizedName("bee_waxcomb").setCreativeTab(CornuCopia.tabBees);
		royal_jelly = new Item().setUnlocalizedName("bee_royal_jelly").setCreativeTab(CornuCopia.tabBees);
		tiny_crown = new Item().setUnlocalizedName("bee_tiny_crown").setCreativeTab(CornuCopia.tabBees);
		
		GameRegistry.registerItem(honeycomb, "bee_honeycomb");
		GameRegistry.registerItem(waxcomb, "bee_waxcomb");
		GameRegistry.registerItem(royal_jelly, "bee_royal_jelly");
		GameRegistry.registerItem(tiny_crown, "bee_tiny_crown");
		
		hive = new BlockHive();
		apiary = new BlockApiary();
		royal_bloom = new BlockBush(){};

		GameRegistry.registerBlock(royal_bloom, "bee_royal_bloom").setUnlocalizedName("bee_royal_bloom");
		royal_bloom.setCreativeTab(CornuCopia.tabBees);


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
				honeycomb,
				0,
				new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, "bee_honeycomb"), "inventory") 
			);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				waxcomb,
				0,
				new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, "bee_waxcomb"), "inventory") 
			);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				royal_jelly,
				0,
				new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, "bee_royal_jelly"), "inventory") 
			);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				tiny_crown,
				0,
				new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, "bee_tiny_crown"), "inventory") 
			);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				Item.getItemFromBlock(hive),
				0,
				new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, "bee_hive"), "inventory") 
			);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				Item.getItemFromBlock(apiary),
				0,
				new ModelResourceLocation("minecraft:oak_planks", "inventory") 
			);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				Item.getItemFromBlock(royal_bloom),
				0,
				new ModelResourceLocation(String.format("%s:%s", CornuCopia.MODID, "bee_royal_bloom"), "inventory") 
			);
		
		// i think these can go here?
		GameRegistry.addShapedRecipe(new ItemStack(apiary), "BSB", "BSB", "BSB", 'B', Blocks.planks, 'S', Blocks.wooden_slab);
		GameRegistry.addShapedRecipe(new ItemStack(tiny_crown), "RRR", "GGG", 'R', Items.redstone, 'G', Items.gold_ingot);
		GameRegistry.addShapedRecipe(new ItemStack(queen), " C ", " B ", "RRR", 'C', tiny_crown, 'B', bee, 'R', royal_jelly);
		
	}
}