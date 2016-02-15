package com.gb.cornucopia.brewing;

import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cuisine.Cuisine;
import com.gb.cornucopia.fruit.BlockFruitCrop;
import com.gb.cornucopia.fruit.BlockFruitLeaf;
import com.gb.cornucopia.fruit.BlockFruitSapling;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.fruit.ItemFruitRaw;

import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Brewing {

	public static Item empty_barrel;
	
	public static ItemDrink wine;
	public static BlockBarrel wine_barrel;

	public static void preInit(){
		empty_barrel = new Item().setUnlocalizedName("brew_empty_barrel");
		GameRegistry.registerItem(empty_barrel, "brew_empty_barrel");
		GameRegistry.addShapedRecipe(new ItemStack(empty_barrel), "WWW", "III", "WWW", 'I', Items.iron_ingot, 'W', Blocks.planks);
		InvModel.add(empty_barrel, "brew_empty_barrel");
		
		wine = new ItemDrink("wine");
		wine_barrel = new BlockBarrel("wine", 1, new Item[]{wine}, new Item[]{Cuisine.grape_juice, Cuisine.grape_juice, Cuisine.grape_juice});
		
	}
	public static void postInit() {
		
	}
	
}
