package com.gb.cornucopia;

import com.gb.cornucopia.bees.apiary.ContainerApiary;
import com.gb.cornucopia.bees.apiary.GuiApiary;
import com.gb.cornucopia.bees.apiary.TileEntityApiary;
import com.gb.cornucopia.cookery.cuttingboard.ContainerCuttingBoard;
import com.gb.cornucopia.cookery.cuttingboard.GuiCuttingBoard;
import com.gb.cornucopia.cookery.cuttingboard.TileEntityCuttingBoard;
import com.gb.cornucopia.cookery.mill.ContainerMill;
import com.gb.cornucopia.cookery.mill.GuiMill;
import com.gb.cornucopia.cookery.mill.TileEntityMill;
import com.gb.cornucopia.cookery.presser.ContainerPresser;
import com.gb.cornucopia.cookery.presser.GuiPresser;
import com.gb.cornucopia.cookery.presser.TileEntityPresser;
import com.gb.cornucopia.cookery.stove.ContainerStove;
import com.gb.cornucopia.cookery.stove.GuiStove;
import com.gb.cornucopia.cookery.stove.TileEntityStove;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int APIARY = 0;
	public static final int PRESSER = 1;
	public static final int MILL = 2;
	public static final int STOVE = 3;
	public static final int CUTTINGBOARD = 4;

	@Override
	public Container getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		switch (ID) {
			case APIARY:
				return new ContainerApiary(player.inventory, (TileEntityApiary)world.getTileEntity(new BlockPos(x, y, z)));
			case PRESSER:
				return new ContainerPresser(player.inventory, (TileEntityPresser)world.getTileEntity(new BlockPos(x, y, z)));
			case MILL:
				return new ContainerMill(player.inventory, (TileEntityMill)world.getTileEntity(new BlockPos(x, y, z)));
			case STOVE:
				return new ContainerStove(player.inventory, (TileEntityStove)world.getTileEntity(new BlockPos(x, y, z)));
			case CUTTINGBOARD:
				return new ContainerCuttingBoard(player.inventory, (TileEntityCuttingBoard)world.getTileEntity(new BlockPos(x, y, z)));
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, int x, int y, int z) {

		switch (ID) {
			case APIARY:
				return new GuiApiary(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
			case PRESSER:
				return new GuiPresser(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
			case MILL:
				return new GuiMill(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
			case STOVE:
				return new GuiStove(getServerGuiElement(ID, player, world, x, y, z), player.inventory, (TileEntityStove)world.getTileEntity(new BlockPos(x, y, z)));
			case CUTTINGBOARD:
				return new GuiCuttingBoard(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
			default:
				return null;
		}
	}
}
