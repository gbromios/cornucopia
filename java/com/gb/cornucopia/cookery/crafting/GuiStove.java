package com.gb.cornucopia.cookery.crafting;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class GuiStove extends GuiContainer {
	public GuiStove(World world, InventoryPlayer inventory, BlockPos blockPos, DishRegistry d) {
		// TODO Auto-generated constructor stub
		super(new ContainerStove(inventory, inventory, d));
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}

}
