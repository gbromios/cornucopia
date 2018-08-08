package com.gb.cornucopia.bees.apiary;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

/*@SideOnly(Side.CLIENT)*/
public class GuiApiary extends GuiContainer {
	private ResourceLocation textures = new ResourceLocation("cornucopia:textures/gui/container/bee_apiary.png");

	private InventoryPlayer playerInv;

	public GuiApiary(Container container, InventoryPlayer playerInv) {
		super(container);
		this.playerInv = playerInv;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
	{
		this.fontRenderer.drawString("Hive", 20, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(textures);
		final int k = (this.width - this.xSize) / 2;
		final int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}
