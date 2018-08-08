package com.gb.cornucopia.cuisine.dish;

import java.util.ArrayList;
import java.util.HashMap;

import com.gb.cornucopia.cuisine.Cuisine;
import com.gb.cornucopia.cuisine.Ingredient;
import com.gb.cornucopia.veggie.Veggie;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

/*
 * copied a lot from ShapelessOreRecipe here. it does what I want but not close enough to extend.
 * I don't really want to abuse the ore dictionary in this way although i totally could
 * 
 * */

public class Dish {

	public static DishRegistry grill;
	public static DishRegistry pan;
	public static DishRegistry pot;
	public static DishRegistry cutting_board;

	// recipes galore~! i probably will add some here for testing but it'll get more complex later :D
	// output, bowl, water, allow_dupes, [inputs], cook_time
	public static void init(){
		// REMEMBER BOWL, WATER, DUPES, ITEM1, ITEM2, ... ITEMN, COOK_TIME
		grill = new DishRegistry()
			//.add(new Dish(Cuisine.toast, false, false, false, Items.BREAD, 100))
			.add(new Dish(Items.BREAD, false, false, false, Cuisine.bread_dough, 200))
			.add(new Dish(Items.COOKED_BEEF, false, false, false, Items.BEEF, 200))
			.add(new Dish(Items.COOKED_PORKCHOP, false, false, false, Items.PORKCHOP, 200))
			.add(new Dish(Items.COOKED_CHICKEN, false, false, false, Items.CHICKEN, 200))
			.add(new Dish(Items.COOKED_MUTTON, false, false, false, Items.MUTTON, 200))
			.add(new Dish(Items.COOKED_FISH, false, false, false, Items.FISH, 200))
			.add(new Dish(Items.COOKED_RABBIT, false, false, false, Items.RABBIT, 200))
			.add(new Dish(Cuisine.kebab, false, false, true, Items.STICK, Ingredient.red_meat, Ingredient.red_meat, Ingredient.seasoning, Ingredient.kebab_veggie, Ingredient.kebab_veggie, 240))
			//.add(new Dish(Cuisine.mirepoix, Ingredient.mirepoix_part, Ingredient.mirepoix_part, Ingredient.mirepoix_part))
			;
		
		pot = new DishRegistry()
			//.add(new Dish(new ItemStack(Cuisine.mozzarella, 4), false, false, false, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Cuisine.lemon_juice ))
			.add(new Dish(Cuisine.mash, false, true, false, Veggie.barley.raw, Veggie.barley.raw, Veggie.barley.raw, Veggie.barley.raw, Veggie.hops.raw, 240))
			//.add(new Dish(Cuisine.ketchup, true, false, false, Cuisine.vinegar, Veggie.tomato.raw, Items.sugar, 200))
			//.add(new Dish(Cuisine.mayonnaise, false, false, true, Ingredient.fat, Ingredient.fat, Items.egg))
			//.add(new Dish(Cuisine.popcorn, true, false, false, Veggie.corn.seed, Veggie.corn.seed, Veggie.corn.seed, Ingredient.fat))
			//.add(new Dish(Cuisine.spaghetti_bolognese, false, false, false, Ingredient.red_meat, Cuisine.fresh_pasta, Cuisine.aged_cheese, Cuisine.red_sauce, Cuisine.wine, 360))
			//.add(new Dish(Cuisine.cheesy_noodles, false, true, false, Cuisine.fresh_pasta, Cuisine.cheese_sauce, Ingredient.casserole_veggie, 300))
			.add(new Dish(Cuisine.red_sauce, false, false, false, Veggie.tomato.raw, Veggie.tomato.raw, Ingredient.fat, Ingredient.seasoning, Ingredient.kebab_veggie, 240))
			//.add(new Dish(Cuisine.fish_and_chips, true, false, false, Items.fish, Items.potato, Cuisine.canola_oil, Cuisine.canola_oil, Ingredient.seasoning, Ingredient.dressing ))
			;
		
		
		pan = new DishRegistry()
			//.add(new Dish(Cuisine.cheese_sauce, true, false, false, Ingredient.seasoning, Ingredient.seasoning, Cuisine.flour, Cuisine.butter, Cuisine.fresh_cheese, Cuisine.fresh_cheese))
			//.add(new Dish(Cuisine.eggs_over_easy, false, false, false, Items.egg, Items.egg))
			.add(new Dish(Items.COOKED_BEEF, false, false, false, Items.BEEF, 200))
			.add(new Dish(Items.COOKED_PORKCHOP, false, false, false, Items.PORKCHOP, 200))
			.add(new Dish(Items.COOKED_CHICKEN, false, false, false, Items.CHICKEN, 200))
			.add(new Dish(Items.COOKED_MUTTON, false, false, false, Items.MUTTON, 200))
			.add(new Dish(Items.COOKED_FISH, false, false, false, Items.FISH, 200))
			.add(new Dish(Items.COOKED_RABBIT, false, false, false, Items.RABBIT, 200))
			;
		
		cutting_board = new DishRegistry()
			.add(new Dish(Cuisine.bread_dough, false, true, false, Cuisine.flour, Cuisine.flour, Cuisine.flour))
			.add(new Dish(Cuisine.batter, false, true, false, Cuisine.flour, Cuisine.flour, Items.EGG, Ingredient.fat, Ingredient.sweetener))
			.add(new Dish(Cuisine.tortilla_dough, false, true, false, Cuisine.corn_flour, Cuisine.corn_flour, Cuisine.soda))
			//.add(new Dish(Cuisine.pasta_dough, false, false, false, Cuisine.flour, Cuisine.flour, Items.egg))
			//.add(new Dish(Cuisine.pastry_dough, false, false, false, Cuisine.flour, Cuisine.flour, Ingredient.fat))
			//.add(new Dish(Cuisine.honey_mustard, false, false, true, Cuisine.mustard, Bees.honey_raw))
			//.add(new Dish(Cuisine.garden_salad, true, false, true, Veggie.lettuce.raw, Veggie.lettuce.raw, Ingredient.dressing, Ingredient.savory_salad, Ingredient.savory_salad))
			//.add(new Dish(Cuisine.caesar_salad, true, false, true, Veggie.lettuce.raw, Veggie.lettuce.raw, Cuisine.olive_oil, Cuisine.lemon_juice, Cuisine.salt, Cuisine.black_pepper, Cuisine.anchovy, Items.egg, Cuisine.toast))
			//.add(new Dish(Cuisine.chicken_caesar_salad, false, false, false, Cuisine.caesar_salad, Items.cooked_chicken))
			.add(new Dish(Cuisine.bloody_mary, false, false, false, Veggie.tomato.raw/*, Veggie.tomato.raw, Veggie.celery.raw, Cuisine.black_pepper, Cuisine.spirits*/))
			//.add(new Dish(Cuisine.bruscetta, false, false, false, Veggie.tomato.raw, Cuisine.olive_oil, Cuisine.basil, Cuisine.toast))
			.add(new Dish(Cuisine.smoothie, false, false, true, Ingredient.smoothie_base, Ingredient.smoothie_base, Ingredient.sweet_salad, Ingredient.sweet_salad, Ingredient.sweet_salad, Items.SNOWBALL))
			;

	}

	private final ItemStack result;
	private final ArrayList<ItemStack> items;
	private final ArrayList<Ingredient> ingredients;
	public final int cook_time;
	private final boolean allows_dupes;
	private final boolean requires_bowl;
	private final boolean requires_water;

	public static final int DEFAULT_COOK_TIME = 160; // 4/5 vanilla time
	public Dish(final Block result, final boolean requires_bowl, final boolean requires_water, final boolean allows_dupes, final Object... recipe){
		this(new ItemStack(result), requires_bowl, requires_water, allows_dupes, recipe);
	}
	public Dish(final Item  result, final boolean requires_bowl, final boolean requires_water, final boolean allows_dupes, final Object... recipe){
		this(new ItemStack(result), requires_bowl, requires_water, allows_dupes, recipe);
		
	}

	// idk if i like this but I am a savage
	public Dish(final ItemStack result, final boolean requires_bowl, final boolean requires_water, final boolean allows_dupes, final Object... recipe)
	{
		this.result = result.copy();
		this.items = new ArrayList<ItemStack>();
		this.ingredients = new ArrayList<Ingredient>();
		this.requires_bowl = requires_bowl;
		this.requires_water = requires_water;
		this.allows_dupes = allows_dupes;
		
		int cook_time_param = DEFAULT_COOK_TIME;

		for (Object i : recipe) {
			if (i instanceof ItemStack) {
				items.add((ItemStack)i);
			}
			else if (i instanceof Item) {
				items.add(new ItemStack((Item)i));
			}
			else if (i instanceof Block) {
				items.add(new ItemStack((Block)i));
			}
			else if (i instanceof Ingredient){
				ingredients.add((Ingredient)i);
			}
			else if (i instanceof Integer) {
				cook_time_param = (int)i;
			}
			else
			{
				String message = "Invalid Dish Recipe: ";
				for (Object a :  recipe)
				{
					message += a + ", ";
				}
				message += this.result;
				throw new RuntimeException(message);
			}
		}
		// TODO: possibly ensure that no ambiguous recipes are being created?
		
		// only set the cook time to default after we determined whether one was supplied
		this.cook_time = cook_time_param;
	}

	public ItemStack getItem(){ return result.copy(); }
	
	public boolean matches(final IItemHandler crafting, int bowlcount, final boolean has_bowl, final boolean has_water)
	{
		return this.matches(crafting, 1, 7, has_bowl, has_water);
	}
	

	public boolean matches(final IItemHandler crafting, final int min_slot, final int max_slot, final boolean has_bowl, final boolean has_water){
		// container will tell you if you're next to water
		if (this.requires_water && !has_water) {
			return false;
		}
		// could make this more like containerItem but I like the idea of making wooden bowls useful for once lol
		if (this.requiresBowl() && !has_bowl){
			return false;
		}

		final ArrayList<ItemStack> items_required = new ArrayList<>(this.items);
		final ArrayList<Ingredient> ingredients_required = new ArrayList<>(this.ingredients);
		// bit chufty this one...
		final HashMap<Item, ArrayList<Ingredient>> counted_as = new HashMap<Item, ArrayList<Ingredient>>();
		
		for (int x = min_slot; x <= max_slot; x++)
		{
			final ItemStack stack = crafting.getStackInSlot(x);

			if (!stack.isEmpty())
			{
				
				
				boolean satisfied_requirement = false;
				for (ItemStack req : items_required)
				{
					if (req.isItemEqual(stack))
					{
						satisfied_requirement = true;
						items_required.remove(req);
						break;
					}
				}
				// one input item can satisfy multiple ingredients!!! be careful hahahaha
				// but each can only satisfy the requirement for an specific ingredient once
				final ArrayList<Ingredient> ingredients_present = new ArrayList<>();
				final Item item = stack.getItem();
				for (Ingredient req : ingredients_required)
				{
					if (req.matches(item)) {
						if ( !counted_as.containsKey(item) )
						{
							satisfied_requirement = true;
							final ArrayList<Ingredient> al = new ArrayList<>();
							al.add(req);
							counted_as.put(item, al);
							ingredients_present.add(req);
						} else if ( !counted_as.get(item).contains(req) ) {
							satisfied_requirement = true;
							counted_as.get(item).add(req);
							ingredients_present.add(req);
						}
					}
				}
				if (!satisfied_requirement) { return false; }
				for (Ingredient present : ingredients_present) {
					ingredients_required.remove(present);
				}
			}
		}
		return items_required.isEmpty() && ingredients_required.isEmpty();
	}

	public boolean requiresBowl() {
		return this.requires_bowl;
	}

	/**
	 * WTF
	 
	public ArrayList<Object> getInput()
	{
		// if this does ever happen, i know how i can make it usable. i'm just gonna need 
		// a damn good reason to implement something better than "return new ArrayList<Object>();"
		throw new RuntimeException("just curious wtf asshole method could be calling this shit. WTF are interfaces even for??????");
	}*/


}
