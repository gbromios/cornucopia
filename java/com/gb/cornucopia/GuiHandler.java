package com.gb.cornucopia;

import java.util.HashMap;

import com.gb.cornucopia.bees.block.BlockApiary;
import com.gb.cornucopia.bees.crafting.ContainerApiary;
import com.gb.cornucopia.bees.crafting.GuiApiary;
import com.gb.cornucopia.cookery.crafting.ContainerCookingTable;
import com.gb.cornucopia.cookery.crafting.DishRegistry;
import com.gb.cornucopia.cookery.crafting.GuiCookingTable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	private static final HashMap<Integer, Object> block_map = new HashMap<>();
	
	// this is gonna get ugly :D
	public static void register(Block b, Object o){
		block_map.put(Block.getIdFromBlock(b), o);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Object o = block_map.get(ID);
		int i = 4;
		if (o instanceof DishRegistry) {
			return new ContainerCookingTable(player.inventory, world, new BlockPos(x, y ,z), (DishRegistry)o);
		}
		else if (o instanceof BlockApiary){
			return new ContainerApiary(player.inventory, world, new BlockPos(x, y ,z));
		}
		else {
			throw new RuntimeException("idk how to handle server gui for this");
		}
		
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Object o = block_map.get(ID);
		if (o instanceof DishRegistry) {
			return new GuiCookingTable(world, player.inventory, new BlockPos(x,y,z), (DishRegistry)o);
		}
		else if (o instanceof BlockApiary){
			return new GuiApiary(world, player.inventory, new BlockPos(x, y ,z));
		}
		else {
			throw new RuntimeException("idk how to handle client gui for this");
		}
	}
}
