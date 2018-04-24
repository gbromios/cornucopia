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
		InvModel.add(royal_bloom);

		bee = new Item().setUnlocalizedName("bee_worker").setRegistryName("bee_worker").setCreativeTab(CornuCopia.tabBees);
		InvModel.add(bee);

		queen = new Item().setUnlocalizedName("bee_queen").setRegistryName("bee_queen").setCreativeTab(CornuCopia.tabBees).setMaxStackSize(1);
		InvModel.add(queen);

		honeycomb = new Item().setUnlocalizedName("bee_honeycomb").setRegistryName("bee_honeycomb").setCreativeTab(CornuCopia.tabBees);
		InvModel.add(honeycomb);

		waxcomb = new Item().setUnlocalizedName("bee_waxcomb").setRegistryName("bee_waxcomb").setCreativeTab(CornuCopia.tabBees);
		InvModel.add(waxcomb);

		royal_jelly = new Item().setUnlocalizedName("bee_royal_jelly").setRegistryName("bee_royal_jelly").setCreativeTab(CornuCopia.tabBees);
		InvModel.add(royal_jelly);

		tiny_crown = new Item().setUnlocalizedName("bee_tiny_crown").setRegistryName("bee_tiny_crown").setCreativeTab(CornuCopia.tabBees);
		InvModel.add(tiny_crown);

		honey_raw = new Item().setUnlocalizedName("bee_honey_raw").setRegistryName("bee_honey_raw").setCreativeTab(CornuCopia.tabBees);
		InvModel.add(honey_raw);
	}

	public static void init() {
	}

	public static void postInit() {
	}
}