package com.gb.cornucopia.cookery.block;

import java.util.HashMap;

import com.gb.cornucopia.cookery.crafting.DishRegistry;

import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;

public enum Vessel implements IStringSerializable {
	NONE("none", 0),
	POT("pot", 1),
	PAN("pan", 2),
	//SKILLET("skillet", 3),
	//KETTLE("kettle", 4),
	//E("E", 5),
	//F("F", 6),
	//G("G", 7); // max 7!
	;
	
	private final String name; // i'd make this public too but it needs to be IStringSerializable to work w/ blockstates so whatever
	public final int meta;
	
	private static final HashMap<Vessel, Item> vesselMap = new HashMap<>(); // what blockstate drops what item
	private static final HashMap<Item, Vessel> itemMap = new HashMap<>(); // what items make what blockstate.
	public static void register(Vessel v, Item i){
		// mappings must be one-to-one
		assert (!itemMap.containsValue(i));
		assert (!itemMap.containsKey(v));
		assert (v != NONE); // none is the enum value representing "no item in use", so don't let its register this.
		itemMap.put(i, v);
		vesselMap.put(v, i);
	}
	
	public static Vessel fromItem(Item i){
		return itemMap.getOrDefault(i, NONE);
	}
	
	public static Item toItem(Vessel v){
		// all vessels have a corresponding item. if you manage to fuck that up, it's on u
		return vesselMap.get(v);
	}
	
	public static Vessel byId(int id){
		return Vessel.values()[id & 7];
	} 
	
	public Item getItem(){
		return toItem(this);
	}
	
	public DishRegistry getDishes(){
		return DishRegistry.byID(this.meta);
	}
	
	private Vessel(String name, int meta){
		this.name = name;
		this.meta = meta;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name();
	}

}
