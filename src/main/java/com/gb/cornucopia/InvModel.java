package com.gb.cornucopia;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class InvModel {
	private static final ArrayList<InvModel> models = new ArrayList<>();

	public static void add(Item item, int meta, String modid) {
		models.add(new InvModel(item, meta, modid));

	}

	public static void add(final Item item, final String modid) {
		add(item, 0, modid);
	}

	public static void add(final Item item) {
		add(item, CornuCopia.MODID);
	}

	public static void add(final Block block, final String modid) {
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(String.format("%s:%s", modid, block.getRegistryName().getResourcePath()));
		GameRegistry.register(itemBlock);
		add(itemBlock, 0, modid);
	}

	public static void add(final Block block) {
		add(block, CornuCopia.MODID);
	}

	private final Item item;
	private final int meta;
	private final String location;

	public InvModel(Item item, int meta, String modid) {
		// used to collect the various blocks and items that need to have inventory models
		// registered all at once in the ClientProxy
		this.item = item;
		this.meta = meta;
		this.location = String.format("%s:%s", modid, item.getRegistryName().getResourcePath());
	}

	public static void register() {
		for (InvModel model : models) {
			CornuCopia.proxy.registerItemRenderer(model.item, model.meta, model.location);
		}

	}
}