package com.gb.cornucopia;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class InvModel {
	private static final ArrayList<InvModel> models = new ArrayList<>();
	
	public static void add(Item i, String name, String modid){
		models.add(new InvModel(i, name, modid));
	}
	public static void add(Item i, String name){
		add(i, name, CornuCopia.MODID);
	}
	public static void add(Block b, String name, String modid){
		add(Item.getItemFromBlock(b), name, modid);
	}
	public static void add(Block b, String name){
		add(b, name, CornuCopia.MODID);
	}
	
	private final Item item;
	private final ModelResourceLocation location;
	
	public InvModel(Item item, String name, String modid){
		// used to collect the various blocks and items that need to have inventory models
		// registered all at once in the ClientProxy
		this.item = item;
		this.location = new ModelResourceLocation(modid + ":" + name, "inventory");
		
	}
	
	public static void register(){
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (InvModel m : models){
			mesher.register(m.item, 0, m.location);
		}

	}
}