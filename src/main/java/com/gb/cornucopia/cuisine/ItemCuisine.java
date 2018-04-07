package com.gb.cornucopia.cuisine;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCuisine extends ItemFood {
	public final String name;
	public final int meal;

	public ItemCuisine(final String name, final int amount, final float saturation) {
		this(name, amount, saturation, 0);
	}

	public ItemCuisine(final String name, final int amount, final float saturation, final int meal) {
		this(name, amount, saturation, meal, false);
	}

	public ItemCuisine(final String name, final int amount, final float saturation, final int meal, final boolean for_wolf) {
		super(amount, saturation, for_wolf);
		this.name = String.format("cuisine_%s", name);
		this.meal = meal;
		if (meal > 0) {
			this.setAlwaysEdible();
		}
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setCreativeTab(CornuCopia.tabCuisine);
		InvModel.add(this);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		if (this.meal > 0) {
			// placeholder that broke in 1.10 update. revisit later~
			// player.addPotionEffect(new PotionEffect(31, 100 * 20, 1));
		}
	}

	public ItemCuisine setMaxStackSize(int i) {
		super.setMaxStackSize(i);
		return this;
	}

	public ItemCuisine setContainerItem(Item i) {
		super.setContainerItem(i);
		return this;
	}

}
