package com.gb.cornucopia.cookery.crafting;

import com.gb.cornucopia.cookery.block.TileEntityStove;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiStove extends GuiContainer {
	private ResourceLocation textures = new ResourceLocation("cornucopia:textures/gui/container/cookery_stove.png");
	public GuiStove(InventoryPlayer player, TileEntityStove stove) {
		// TODO Auto-generated constructor stub
		super(new ContainerStove(player, stove));
		
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        //this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
        //this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(textures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

}

