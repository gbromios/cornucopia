package com.gb.cornucopia.brewing;

import com.gb.cornucopia.fruit.BlockFruitCrop;
import com.gb.cornucopia.fruit.BlockFruitLeaf;
import com.gb.cornucopia.fruit.BlockFruitSapling;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.fruit.ItemFruitRaw;

import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class Brewing {

	public static Brew wine;
	
	public static Brew createBrew(final String name){
		return new Brew(
				new BlockBarrel(name),
				new ItemDrink(name),
				Items.gold_ingot
				); 
	}

	public static void preInit(){
		wine = createBrew("wine");
	}
	
}
