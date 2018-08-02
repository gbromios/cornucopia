package com.gb.cornucopia;

import com.gb.cornucopia.bees.Bees;
import com.gb.cornucopia.bees.apiary.ContainerApiary;
import com.gb.cornucopia.bees.apiary.GuiApiary;
import com.gb.cornucopia.bees.apiary.TileEntityApiary;
import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cookery.cuttingboard.ContainerCuttingBoard;
import com.gb.cornucopia.cookery.cuttingboard.GuiCuttingBoard;
import com.gb.cornucopia.cookery.mill.ContainerMill;
import com.gb.cornucopia.cookery.mill.GuiMill;
import com.gb.cornucopia.cookery.presser.ContainerPresser;
import com.gb.cornucopia.cookery.presser.GuiPresser;
import com.gb.cornucopia.cookery.presser.TileEntityPresser;
import com.gb.cornucopia.cookery.stove.ContainerStove;
import com.gb.cornucopia.cookery.stove.GuiStove;
import com.gb.cornucopia.cookery.stove.TileEntityStove;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int APIARY = 0;
	public static final int PRESSER = 1;

	@Override
	public Container getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		switch (ID) {
			case APIARY:
				return new ContainerApiary(player.inventory, (TileEntityApiary)world.getTileEntity(new BlockPos(x, y, z)));
			case PRESSER:
				return new ContainerPresser(player.inventory, (TileEntityPresser)world.getTileEntity(new BlockPos(x, y, z)));
			default:
				return null;

		}
/*		final Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		if (block == Cookery.cutting_board) { // cutting board
			return new ContainerCuttingBoard(player.inventory, world, new BlockPos(x, y, z));
		} else if (block == Cookery.stove) {
			return new ContainerStove(player.inventory, (TileEntityStove) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (block == Cookery.presser) {
			return new ContainerPresser(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (block == Cookery.mill) {
			return new ContainerMill(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (block == Bees.apiary) {
			return new ContainerApiary(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)), world, new BlockPos(x, y, z));
		} else {
			throw new RuntimeException("no gui: idk how to server container for this");
		}*/
	}

	@Override
	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, int x, int y, int z) {

		switch (ID) {
			case APIARY:
				return new GuiApiary(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
			case PRESSER:
				return new GuiPresser(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
			default:
				return null;
		}
/*		final Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

		// i dont think these should be passing in world >___> deal w/ it later TODO
		if (block == Cookery.cutting_board) {
			return new GuiCuttingBoard(world, player.inventory, new BlockPos(x, y, z));
		} else if (block == Cookery.stove) {
			return new GuiStove(player.inventory, (TileEntityStove) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (block == Cookery.presser) {
			return new GuiPresser(world, player.inventory, new BlockPos(x, y, z));
		} else if (block == Cookery.mill) {
			return new GuiMill(world, player.inventory, new BlockPos(x, y, z));
		} else if (block == Bees.apiary) {
			return new GuiApiary(world, player.inventory, new BlockPos(x, y, z));
		} else {
			throw new RuntimeException("no gui: idk how client container/gui for this");
		}*/
	}
}
