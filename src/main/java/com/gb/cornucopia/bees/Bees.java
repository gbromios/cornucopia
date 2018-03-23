package com.gb.cornucopia.bees;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.bees.apiary.BlockApiary;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Bees {
	public static BlockHive hive;
	public static BlockApiary apiary;
	public static Block royal_bloom;

	public static Item bee;
	public static Item queen;
	public static Item honeycomb;
	public static Item waxcomb;
	public static Item royal_jelly;
	public static Item tiny_crown;
	public static Item honey_raw;

	public static void preInit() {
		hive = new BlockHive();
		apiary = new BlockApiary();

		royal_bloom = new BlockBush() {
		}.setUnlocalizedName("bee_royal_bloom").setRegistryName("bee_royal_bloom").setCreativeTab(CornuCopia.tabBees);
		GameRegistry.register(royal_bloom);
		InvModel.add(royal_bloom);

		bee = new Item().setUnlocalizedName("bee_worker").setRegistryName("bee_worker").setCreativeTab(CornuCopia.tabBees);
		GameRegistry.register(bee);
		InvModel.add(bee);

		queen = new Item().setUnlocalizedName("bee_queen").setRegistryName("bee_queen").setCreativeTab(CornuCopia.tabBees).setMaxStackSize(1);
		GameRegistry.register(queen);
		InvModel.add(queen);

		honeycomb = new Item().setUnlocalizedName("bee_honeycomb").setRegistryName("bee_honeycomb").setCreativeTab(CornuCopia.tabBees);
		GameRegistry.register(honeycomb);
		InvModel.add(honeycomb);

		waxcomb = new Item().setUnlocalizedName("bee_waxcomb").setRegistryName("bee_waxcomb").setCreativeTab(CornuCopia.tabBees);
		GameRegistry.register(waxcomb);
		InvModel.add(waxcomb);

		royal_jelly = new Item().setUnlocalizedName("bee_royal_jelly").setRegistryName("bee_royal_jelly").setCreativeTab(CornuCopia.tabBees);
		GameRegistry.register(royal_jelly);
		InvModel.add(royal_jelly);

		tiny_crown = new Item().setUnlocalizedName("bee_tiny_crown").setRegistryName("bee_tiny_crown").setCreativeTab(CornuCopia.tabBees);
		GameRegistry.register(tiny_crown);
		InvModel.add(tiny_crown);

		honey_raw = new Item().setUnlocalizedName("bee_honey_raw").setRegistryName("bee_honey_raw").setCreativeTab(CornuCopia.tabBees);
		GameRegistry.register(honey_raw);
		InvModel.add(honey_raw);
	}

	public static void init() {
		GameRegistry.addShapedRecipe(new ItemStack(apiary), "BSB", "BSB", "BSB", 'B', Blocks.PLANKS, 'S', Blocks.WOODEN_SLAB);
		GameRegistry.addShapedRecipe(new ItemStack(tiny_crown), "RRR", "GGG", " R ", 'R', Items.REDSTONE, 'G', Items.GOLD_INGOT);
		GameRegistry.addShapedRecipe(new ItemStack(queen), "HCH", "HBH", "RRR", 'C', tiny_crown, 'B', bee, 'R', royal_jelly, 'H', honey_raw);
	}

	public static void postInit() {
	}
}