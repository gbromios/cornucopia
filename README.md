# cornucopia
#### Enhanced Cooking and Agriculture for Wilson SMP (Minecraft 1.8)

Originally I set out to update Pam's HarvestCraft for 1.8 so we could use it on WilsonSMP. However, Pam herself didn't really seem too enthused when I asked her permission, though she did consent with the qualification that it was for a private server.

Given the breadth of changes required to upgrade from 1.7 to 1.8, it quickly became apparent that it would be faster and easier to re-implement the entire thing from scratch. It's gone quite well, and starting fresh allowed me to add and change functionality 

The underlying ethos of CornuCopia is that of WilsonSMP: to add depth of gameplay and aesthetics without over-complicated mechanics that detract from the general feel of vanilla.

There's a lot that's done already, and I have a bit more to add before release. Enzer & I also have a decent number of textures to work through. At this point, I'd say that's all we need to be ready for release. At the very minimum, we'll need textures for the crop blocks and their immediate food items, as well as a couple of crafting station textures. Maybe the gui templates could use a redo if enzer's got time.

I've got a list of crafting recipes for wilson, but I'd prefer if only a small number were made available at launch. Part of the fun of this mod (i hope) will be discovering what's possible.

Without further ado:

## Features Overview

#### Current
- 33 New Ground Crops
- 18 New Orchard Trees
- Beekeeping
- Kitchens (food-centered crafting stations)

#### Ready by Feb 2016 Re-Opening of WilsonSMP
- Dozens if not *Millions* of new Recipes & Textures
- Cheesemaking
- Libation Brewing

#### Immediate Post-Release Plans
- Enhanced Orchard Mechanics (get your pruning shrears out)
- Pottery Wheel (Enzer has some ideas for custom storage containers & decor)
- Fishing (like GrowthCraft except not hilariously overpowered)

#### Long-Term plans
- Food Buffs (the most difficult recipes will require extensive infrastructure to produce, so they should come with a reward. Think "well-fed" modifier in WoW or some such)
- Inventory UX (Related to the above, one of the natural side effects of cooking recipes is that they'll increase the density of the food you have to carry. that means, instead of three stacks of Cooked Beefx64, you can take half a stack of some prepared meal)
- Biome Awareness (for regional variety in both wild and domestic crops
- Dynamic Recipe System (I'm thinking big here, the sky is truly the limit)
- Cookbook/Recipe Cards (records recipes you've discovered and allows you to share them with other players)

## Crops
- 33 New crops. These work like wheat/potatos/carrots.
- May be found growing wild* very rarely
- Seeds may be obtained in shops*, and are also craftable (1x raw crop => 2x seeds)
- Don't plant them too close together! if a crop is placed too densely, it'll grow *much* more slowly. Different crops may be planted next to each other, so be sure to practice good crop rotation.

*exactly how the new crops will be obtainable is ultimately up to Wilson. It's too late to get them in worldgen, but he may decide to selectively place wild cultivars or sell seeds in shops.

## Orchards
 - 14 New Fruit-bearing trees. These are *not* based on apples. Apples are dumb, and these are cool.
 - Very rarely, you may spot a fruit growing on a tree in the wild. If you're lucky, you'll get a sapling when you harvest it.
 - Plant this sapling in your orchard to recieve a fruit tree, but make sure you pick a good spot... saplings are not easy to come by.
 - Fruits will grow under the branches of your new fruit tree. You may want to monitor new buds, and prune them back so they're not too close together.
 - Once the tree is well-established, its fruits will have a small chance to drop saplings. This should take quite a long time, so don't get impatient.
 - Fruit growth may be sped up with bonemeal just like crops, but fruits grown with bonemeal will never drop saplings.

## BEES
 - Bee hives can be found in the wild, and breaking them will drop a few bees that you can use to start your own colony. There is a tiny chance that wild hives will also drop a queen!
 - Build an *Apiary* block to give your bees a nice home: once they're settled in, they'll slowly begin to fill their new home with *Wax*, then fill that wax with *honeycomb*.
 - after harvesting honeycomb, you can turn it into honey using a *Press*
 - Apairies require proximity to flowers if your bees are going to get anything done. If you want to keep multiple hives, Be sure you leave plenty of room between your apiaries.
 - If you have a productive hive, pollenation will cause flowers to spread in the vicinity.
 - There's a very small chance that you may be blessed with a *Royal Bloom*, which will enable the production of *Royal Jelly*
 - After you've produced a few combs of royal jelly, you'll be able to craft a queen, though I'll leave the specifics as a mystery for now
 - Once you find or grow a queen, your bees' number will start to increase. The more flowers you have around your apiary, the faster they'll grow.
 - Unfortunately, without a queen, your bees will be unable to reproduce, and you'll be limited to whatever wild bees you can find in the wild

I'm still fine-tuning the numbers here, but this module is pretty well completed.

## Brewing
 - Craft empty barrels, and combine them with some ingredients (say, grape juice) and a lid (a wooden slab) and you'll have a barrel that you can place.
 - Once placed, this barrel will begin to ferment. You can break it and move it somewhere else, but be careful: this will re-start the fermentation process.
 - Fermentation takes a really long time. Like, up to a day or more. Earth day, not Minecraft day. The upside is: fermentation will continue even when a barrel's chunk isn't loaded. And it doesn't even put a load on the server.
 - You'll know a barrel is ready to be tapped when it starts to fizz.
 - At this point, when you break the barrel, you'll receive the empty barrel along with a few bottles of your libation of choice.
 - Related to the above note on Food Buffs, in the future, I'd like to have beverages provide benefits similar to potions.
 - There is already support for aging your brews, such that the longer you leave them, the better they'll get. Unfortunately, "better" doesn't actually mean anything yet. I'll expand those mechanics soon!
 
## Cheese 
  - Raw milk can be fermented (which takes much less time than fermenting libations) to produce a fresh cheese block
  - you can break this block down into fresh cheese wedges at the cutting board
  - this block can be placed somewhere out of the way (in your pantry or cellar) to age. Again, it's possible to move this block, but doing so may reset the aging process. This is very delicate cheese, man!
  - Aged cheese wheels act like cake: click to recieve a wedge.
  
## That's all for now.

I'll update this document as circumstances change.

-gbromios
