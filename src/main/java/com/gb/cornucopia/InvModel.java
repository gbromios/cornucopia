package com.gb.cornucopia;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class InvModel {
	private static final ArrayList<InvModel> models = new ArrayList<>();

	private final Item item;
	private final Block block;
	private final int meta;
	private final ResourceLocation name;

	private InvModel(Item item, Block block, int meta, String modid) {
		// used to collect the various blocks and items that need to have inventory models
		// registered all at once in the ClientProxy
		this.item = item;
		this.block = block;
		this.meta = meta;
		this.name = (modid == null) ? item.getRegistryName()
				: new ResourceLocation(String.format("%s:%s", modid, (item == null ? block : item).getRegistryName().getResourcePath()));
	}

	private static void add(Item item, Block block, int meta, String modid) {
		models.add(new InvModel(item, block, meta, modid));
	}

	public static void add(Item item) {
		add(item, null, 0, null);
	}

	public static void add(Block block, String modid) {
		add(null, block, 0, modid);
	}

	public static void add(Block block) {
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		add(itemBlock, block, 0, null);
	}

	public static void registerItems(IForgeRegistry<Item> registry) {
		for (InvModel model : models) {
			if (model.item != null) registry.register(model.item);
		}
	}

	public static void registerItemModels() {
		for (InvModel model : models) {
			if (model.item != null) CornuCopia.proxy.registerItemRenderer(model.item, model.meta, model.name);
		}
	}

	public static void registerBlocks(IForgeRegistry<Block> registry) {
		for (InvModel model : models) {
			if (model.block != null) registry.register(model.block);
		}
	}
}