package com.gb.cornucopia;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class InvModel {
	private static final ArrayList<InvModel> models = new ArrayList<>();

	public static void add(final Item i, final String name, final String modid){
		models.add(new InvModel(i, name, modid));
	}
	public static void add(final Item i, final String name){
		add(i, name, CornuCopia.MODID);
	}
	public static void add(final Block b, final String name, final String modid){
		add(Item.getItemFromBlock(b), name, modid);
	}
	public static void add(final Block b, final String name){
		add(b, name, CornuCopia.MODID);
	}

	private final Item item;
	private final String location;

	public InvModel(final Item item, final String name, final String modid){
		// used to collect the various blocks and items that need to have inventory models
		// registered all at once in the ClientProxy
		this.item = item;
		this.location = modid + ":" + name;

	}

	public static void register(){
		final ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (InvModel m : models){
			mesher.register(m.item, 0, new net.minecraft.client.renderer.block.model.ModelResourceLocation(m.location, "inventory"));
		}

	}
}