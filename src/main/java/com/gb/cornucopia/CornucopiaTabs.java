package com.gb.cornucopia;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cuisine.Cuisine;
import com.gb.cornucopia.fruit.Fruit;
import com.gb.cornucopia.veggie.Veggie;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CornucopiaTabs {

	public class BeesCreativeTab extends CreativeTabs {
		public BeesCreativeTab() {
			super(CornuCopia.MODID + "_bees");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Bees.queen);
		}
	}

	public class CookeryCreativeTab extends CreativeTabs {
		public CookeryCreativeTab() {
			super(CornuCopia.MODID + "_cookery");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(Cookery.cutting_board));
		}
	}

	public class CuisineCreativeTab extends CreativeTabs {
		public CuisineCreativeTab() {
			super(CornuCopia.MODID + "_cuisine");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Cuisine.bread_dough);
		}
	}

	public class FruitCreativeTab extends CreativeTabs {
		public FruitCreativeTab() {
			super(CornuCopia.MODID + "_fruit");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Fruit.peach.raw);
		}
	}

	public class VeggieCreativeTab extends CreativeTabs {
		public VeggieCreativeTab() {
			super(CornuCopia.MODID + "_veggies");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Veggie.asparagus.raw);
		}
	}
}