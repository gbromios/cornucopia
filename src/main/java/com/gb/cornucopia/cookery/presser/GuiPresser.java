package com.gb.cornucopia.cookery.presser;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPresser extends GuiContainer {
	// copies GuiCrafting -- change later
	private ResourceLocation textures = new ResourceLocation("cornucopia:textures/gui/container/cookery_presser.png");

	public GuiPresser(final World world, final InventoryPlayer player, final BlockPos pos) {
		// will this crash if the wrong kind of tile entity is there? probably lol
		super(new ContainerPresser(player, (IInventory)world.getTileEntity(pos)));
		// find out for sure!!
		if (!(world.getTileEntity(pos) instanceof TileEntityPresser)){
		}		
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
	{
		this.fontRenderer.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
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
