package com.gb.cornucopia.cuisine;

import java.util.Random;

import com.gb.cornucopia.bees.Bees;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// cf issue #61
/*
public class SaltyBoy {
	private static final Random RANDOM = new Random();
	/// seems a bit sketchy
	@SubscribeEvent
	public void onGetSalty(BlockEvent.HarvestDropsEvent e) {
		// 1/96 chance to drop rock salt when mining any vanilla stone under y=40
		if ((e.harvester != null && e.harvester.getPosition().getY() < 40) && e.state.getBlock() == Blocks.stone && RANDOM.nextInt(96) == 0){
			e.drops.add(new ItemStack(Cuisine.rock_salt));
			// 1/4 chance to get two rock salts!
			if (RANDOM.nextInt(4) == 0) {
				e.drops.add(new ItemStack(Cuisine.rock_salt));
			}
		}
	}
	
}*/
