package com.gb.cornucopia;

import java.util.HashMap;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.bees.block.BlockApiary;
import com.gb.cornucopia.bees.crafting.ContainerApiary;
import com.gb.cornucopia.bees.crafting.GuiApiary;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cookery.block.BlockStoveTop;
import com.gb.cornucopia.cookery.block.TileEntityStove;
import com.gb.cornucopia.cookery.block.Vessel;
import com.gb.cornucopia.cookery.crafting.ContainerCuttingBoard;
import com.gb.cornucopia.cookery.crafting.ContainerPresser;
import com.gb.cornucopia.cookery.crafting.ContainerStove;
import com.gb.cornucopia.cookery.crafting.DishRegistry;
import com.gb.cornucopia.cookery.crafting.GuiCuttingBoard;
import com.gb.cornucopia.cookery.crafting.GuiPresser;
import com.gb.cornucopia.cookery.crafting.GuiStove;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		final Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		if (block == Cookery.cutting_board) { // cutting board
			return new ContainerCuttingBoard(player.inventory, world, new BlockPos(x, y ,z));
		}
		else if (block == Cookery.stovetop){
			final TileEntityStove stoveEntity = (TileEntityStove)world.getTileEntity(new BlockPos(x, y ,z));
			return new ContainerStove(player.inventory, stoveEntity);
		}
		else if (block == Cookery.presser){
			return new ContainerPresser(player.inventory, (IInventory)world.getTileEntity(new BlockPos(x, y ,z)));
		}
		else if (block == Bees.apiary){
			return new ContainerApiary(player.inventory, (IInventory)world.getTileEntity(new BlockPos(x, y ,z)), world, new BlockPos(x, y ,z));
		}
		else {
			throw new RuntimeException("no gui: idk how to server container for this");
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock(); 
		if (block == Cookery.cutting_board) { // cutting board
			return new GuiCuttingBoard(world, player.inventory, new BlockPos(x,y,z));
		}
		else if (block == Cookery.stovetop){
			final TileEntityStove stoveEntity = (TileEntityStove)world.getTileEntity(new BlockPos(x, y ,z));
			return new GuiStove(player.inventory, stoveEntity);
		}
		else if (block == Cookery.presser){
			return new GuiPresser(world, player.inventory, new BlockPos(x, y ,z));
		}
		else if (block == Bees.apiary){
			return new GuiApiary(world, player.inventory, new BlockPos(x, y ,z));
		}
		else {
			throw new RuntimeException("no gui: idk how client container/gui for this");
		}
	}
}
