package com.gb.cornucopia.cuisine;

import java.util.Random;

import com.gb.cornucopia.bees.Bees;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SaltyBoy {
	private static final Random RANDOM = new Random();
	/// seems a bit sketchy
	@SubscribeEvent
	public void onGetSalty(BlockEvent.HarvestDropsEvent e) {
		if ((e.harvester != null && e.harvester.getPosition().getY() < 50) && e.state.getBlock() == Blocks.stone && RANDOM.nextInt(100) == 0){
			e.drops.add(new ItemStack(Bees.honey_raw));
		}
	}
	
}
