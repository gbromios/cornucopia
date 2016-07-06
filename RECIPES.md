## crafting recipes
these need to be made compatible with wilson mats (ore dict?) probably worry about that later. Might be a good idea to sell cooking implements in stores.
 - [x] Stove
   ```
   III
   S_S
   SSS
   I = Iron Ingot
   S = cobblestone
   ```

 - [x] Mill
   ```
   SSS
   WIW
   WWW
   I = Iron Ingot
   W = wood planks
   S = cobblestone
   ```

 - [x] Presser
   ```
   III
   WPW
   WWW
   I = Iron Ingot
   P = Piston
   W = wood planks
   ```

 - [x] Water Basin
   ```
   S_S
   SBS
   SSS
   S = cobblestone
   W = water bucket
   ```

 - [x] Cutting Board
   ```
   I__
   SSS
   I = Iron Ingot (might change this to be a sword)
   S = wood slabs
   ```

 - [x] Pan
   ```
   _II
   _II
   S__
   I = Iron Ingot
   S = Stick
   ```

 - [x] Pot
   ```
   S_S
   I_I
   III
   S = Stick
   I = Iron Ingot
   ```

 - [x] Apiary
   ```
   WSW
   WSW
   WSW
   S = wood slab
   W = wood planks
   ```

 - [x] Tiny Crown
   ```
   RRR
   GGG
   _R_
   R = redstone powder
   G = gold ingot
   RRR
   GGG
    R 
   ```

 - [x] Queen Bee
   ```
   HCH
   HBH
   RRR
   H = raw honey (not comb)
   C = tiny crown
   B = worker bee
   R = royal jelly
   ```
   
## Brewing & Cheesery

After making a barrel with the above recipe, you can fill the barrels using a shaped recipe:

```
_S_
123
_B_

S = Wooden Slab
B = Empty Barrel
```

different products are obtained with different ingredients for 1, 2 and 3:

 - Wine <= 3x Grape Juice
 - Cider* <= 3x Apple Juice
 - Cordial* <= 3x Blueberry Juice
 - Beer <= 3x Mash
 - Mead <= 3x Raw Honey
 - Pickle <= Vinegar, Cucumber, Vinegar
 - Anchovy <= Salt, Raw Fish, Salt
 - Fresh Cheese <= 3x Milk
 - Vinegar <= Wine (just 1)

* for cider and cordial, I intend to allow a lot of different juices to be used as input, but as I was writing this document, I realized I never implemented that. Coming soon!

As you can see, fresh cheese blocks are made in a barrel. placing the cheese block and waiting patiently will result in aged cheese, which acts like cake.

## Cooking Recipes

no grid here, all the cuisine recipes are shapeless. These are broken down by crafting station. "Ingredients" are like categories for recipe inputs, and they're listed below

vanilla meat can be cooked on the grill or on a pan, though the vanilla furnace recipe still works. I wanna take it out, but I'll have to ask wilson first.

The vanilla Recipes for bread and cake no longer work! You now need to make dough or batter respectively, which can be cooked in a furnace, vanilla style. I decided to make these not require container items, since that would make them consume wood, lol
 
on that note: some of these recipes require bowls available in the cooking station's bowl slot, some require access to fresh water (i.e. cooking station build next to a water basin block) I've tried to only require a bowl where i can have it so eating the final product will return the bowl. no way to do that in furnace sadly

I have a lot more recipes to add, and some of the stuff below is not used for anything yet. All that will need to wait until I find a good chunk of free time sadly. As you can see, this system is pretty damn flexible, so the sky's really the limit for what we want to add. Currently, the recpies are hard-coded but it won't be hard for me to make it so they can be added dynamically at run time. That would be cool and I got a lot of ideas there.
 
### Grill (i.e. a Stove with no cooking vessel on it)
 - Toast <= 1x Bread
 - Kebab <= 1x Stick, 2x [Red Meat], 2x [Kebab Veggie], 1x [Seasoning]
 
### Stove + Pan
 - Cheese Sauce <= 2x [Seasoning], 1x Flour, 1x Butter, 2x Fresh Cheese
 - Eggs Over Easy <= 2x Egg (might add butter but that's a bit resource intense)
 
 ### Stove + Pot
  - Mozzarella <= 3x Milk Bucket, 1x Lemon Juice
  - Mash (to brew into Beer!) <= 3x Raw Barley, 1x Raw Hops
  - Ketchup <= 1x Vinegar, 1x Raw Tomato, 1x Sugar
  - Mayonnaise <= 2x [Fat], 1x Egg
  - Popcorn <= 3x Corn Seed, 1x [Fat]
  - Spaghetti Bolognese <= 1x [Red Meat], 1x Fresh Pasta, 1x Aged Cheese, 1x Red Sauce, 1x Wine
  - Cheesy Noodles <= 1x Fresh Pasta, 1x Cheese Sauce, 1x Casserole Veggie
  - Red Sauce <= 2x Raw Tomato, 1x Fat, 1x Seasoning, 2x [Mirepoix Part], 1x [Kebab Veggie]
  - Fish & Chips <= 1x Raw Fish, 1x Potato, 2x Canola Oil, 1x Seasoning, 1x Dressing
 
### Cutting Board

 - Bread Dough <= 3x Flour
 - Batter <= 2x Flour, 1x Egg, 1x [Fat], 1x [Sweetener]
 - Tortilla Dough <= 2x Corn Flour, 1x Soda
 - Pasta Dough <= 2x Flour, 1x Egg
 - Pastry Dough <= 2x Flour, 1x Fat
 - Honey Mustard <= 1x Mustard, 1x Honey (duh)
 - Garden Salad <= 2x Raw Lettuce, 2x [Savory Salad], 1x Dressing
 - Caesar Salad <= 2x Raw Lettuce, 1x Olive Oil, 1x Lemon Juice, 1x Salt, 1x Black Pepper, 1x Anchovy, 1x Egg, 1x Toast (for croutons)
 - Chicken Caesar <= 1x Caesar Salad, 1x Cooked Chicken
 - Bruscetta <= 1x Raw Tomato, 1x Olive Oil, 1x Raw Basil, 1x Toast
 - Smoothie <= 2x [Smoothie Base], 3x [Sweet Salad], 1x Snowball


## Ingredients

note: the names below are constants from the source code, but it should be pretty obvious what everything is

sweetener
 - Items.sugar
 - Bees.honey_raw
;

mirepoix_part
 - Veggie.onion.raw
 - Veggie.celery.raw
 - Veggie.garlic.raw
 - Veggie.bell_pepper.raw
 - Items.carrot
;

mountain_berry
 - Veggie.blackberry.raw
 - Fruit.pear.raw
 - Veggie.blueberry.raw
 - Veggie.strawberry.raw
 - Veggie.raspberry.raw
 - Fruit.cherry.raw
;

citrus
 - Fruit.orange.raw
 - Fruit.lime.raw
 - Fruit.lemon.raw
 - Fruit.grapefruit.raw
;

fat
 - Cuisine.olive_oil
 - Cuisine.butter
 - Cuisine.canola_oil
;

kebab_veggie
 - Veggie.bell_pepper.raw
 - Veggie.onion.raw
 - Veggie.zucchini.raw
 - Blocks.red_mushroom
 - Blocks.brown_mushroom
;

red_meat
 - Items.beef
 - Items.porkchop
 - Items.mutton
 - Items.rabbit
;

cooked_red_meat
 - Items.cooked_beef
 - Items.cooked_porkchop
 - Items.cooked_mutton
 - Items.cooked_rabbit
;

seasoning
 - Cuisine.black_pepper
 - Cuisine.basil
 - Cuisine.cilantro
 - Cuisine.chili_powder
 - Cuisine.cinnamon
 - Cuisine.curry_powder
 - Cuisine.mint
 - Cuisine.oregano
 - Cuisine.rosemary
 - Cuisine.salt
;

casserole_veggie
 - Veggie.artichoke.raw
 - Veggie.asparagus.raw
 - Veggie.broccoli.raw
 - Veggie.celery.raw
 - Veggie.garlic.raw
 - Items.carrot
 - Veggie.bean.raw
 - Veggie.lentil.raw
 - Veggie.pea.raw
 - Veggie.stringbean.raw
 - Veggie.eggplant.raw
 - Veggie.zucchini.raw
;

juice
 - Cuisine.apple_juice
 - Cuisine.carrot_juice
 - Cuisine.melon_juice
 - Cuisine.cherry_juice
 - Cuisine.fig_juice
 - Cuisine.grapefruit_juice
 - Cuisine.kiwi_juice
 - Cuisine.lemon_juice
 - Cuisine.lime_juice
 - Cuisine.orange_juice
 - Cuisine.peach_juice
 - Cuisine.pear_juice
 - Cuisine.plum_juice
 - Cuisine.pomegranate_juice
 - Cuisine.beet_juice
 - Cuisine.blackberry_juice
 - Cuisine.blueberry_juice
 - Cuisine.pineapple_juice
 - Cuisine.raspberry_juice
 - Cuisine.strawberry_juice
 - Cuisine.tomato_juice
 - Cuisine.grape_juice
;

cordialable
 - Cuisine.cherry_juice
 - Cuisine.kiwi_juice
 - Cuisine.fig_juice
 - Cuisine.plum_juice
 - Cuisine.pomegranate_juice
 - Cuisine.blackberry_juice
 - Cuisine.blueberry_juice
 - Cuisine.raspberry_juice
 - Cuisine.strawberry_juice
;

ciderable
 - Cuisine.apple_juice
 - Cuisine.peach_juice
 - Cuisine.pear_juice
;

savory_salad
 - Veggie.artichoke.raw
 - Veggie.beet.raw
 - Veggie.bell_pepper.raw
 - Veggie.broccoli.raw
 - Items.carrot
 - Veggie.cabbage.raw
 - Veggie.onion.raw
 - Veggie.tomato.raw
 - Veggie.turnip.raw
 - Veggie.pea.raw
 - Veggie.stringbean.raw
 - Veggie.cucumber.raw
 - Veggie.eggplant.raw
 - Fruit.avocado.raw
;

dressing
 - Cuisine.vinegar
 - Cuisine.olive_oil
 - Cuisine.honey_mustard
 - Cuisine.mayonnaise
 - Cuisine.ketchup
;

sweet_salad
 - Items.apple
 - Veggie.blackberry.raw
 - Veggie.blueberry.raw
 - Veggie.pineapple.raw
 - Veggie.raspberry.raw
 - Veggie.strawberry.raw
 - Veggie.grape.raw
 - Fruit.banana.raw
 - Fruit.cherry.raw
 - Fruit.pear.raw
 - Fruit.plum.raw
 - Fruit.kiwi.raw
 - Fruit.peach.raw
;

smoothie_base
 - Cuisine.apple_juice
 - Cuisine.carrot_juice
 - Cuisine.melon_juice
 - Cuisine.cherry_juice
 - Cuisine.fig_juice
 - Cuisine.grapefruit_juice
 - Cuisine.kiwi_juice
 - Cuisine.orange_juice
 - Cuisine.peach_juice
 - Cuisine.pear_juice
 - Cuisine.plum_juice
 - Cuisine.pomegranate_juice
 - Cuisine.beet_juice
 - Cuisine.blackberry_juice
 - Cuisine.blueberry_juice
 - Cuisine.pineapple_juice
 - Cuisine.raspberry_juice
 - Cuisine.strawberry_juice
 - Cuisine.grape_juice
 - Items.milk_bucket
;
