package com.gb.cornucopia.brewing;


import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
// this will be a pretty simple implementation, definitely subject to depth added down the line
public class Brew {
	public final BlockBarrel barrel;
	public final ItemDrink drink;
	public final Item source; // items needed to make the barrel

	public Brew(BlockBarrel barrel, ItemDrink drink, Item source){
		this.barrel = barrel;
		this.drink = drink;
		this.source = source;

		GameRegistry.addShapedRecipe(new ItemStack(this.barrel),
				"IWI","WSW","WWW",
				'I', Items.iron_ingot,
				'W', Blocks.planks,
				'S', source
				);
		
	}
}
