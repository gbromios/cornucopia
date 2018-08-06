package com.gb.cornucopia.cookery.stove;

import com.gb.cornucopia.cookery.Vessel;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiStove extends GuiContainer {
	private ResourceLocation textures = new ResourceLocation("cornucopia:textures/gui/container/cookery_stove.png");
	//private static final ResourceLocation textures = new ResourceLocation("textures/gui/container/furnace.png");
	private InventoryPlayer playerInv;
	private TileEntityStove stove;
	private int frames = 0;

	public GuiStove(Container container, final InventoryPlayer playerInv, TileEntityStove stove) {
		super(container);
		this.playerInv = playerInv;
		this.stove = stove;

	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
	{
		//this.fontRendererObj.drawString("container.inventory", 8, this.ySize - 96 + 2, 4210752);
		// brute force a usable interface for now (lol)
		String tooltip = "";
		int y0 = this.guiTop;
		int x0 = this.guiLeft;
		//if (( mouseX -x0 > 0 && mouseX - x0 < 100 ) && ( mouseY - y0 > 0 && mouseY - y0 < 100 )) {
		//tooltip = String.format("X%d, Y%d", mouseX -x0, mouseY - y0 );
		//}


		if (( mouseX - x0 > 22 && mouseX - x0 < 41 ) && ( mouseY - y0 > 17 && mouseY - y0 < 36 ) ) {
			//tooltip = String.format("X%d, Y%d", mouseX -x0, mouseY - y0 );
			Vessel v = this.stove.getVessel();
			if (v == Vessel.NONE) {
				tooltip = "Grill";
			} else {
				tooltip = v.getName(); 
				tooltip = Character.toUpperCase(tooltip.charAt(0)) + tooltip.substring(1); 
			}

		}

/*		else if (( mouseX - x0 > 22 && mouseX - x0 < 41 ) && ( mouseY - y0 > 35 && mouseY - y0 < 54 )) {
			tooltip = this.stove.hasBowl() ? "Bowls" : "Out of Bowls";
		}*/

		else if (( mouseX - x0 > 22 && mouseX - x0 < 41 ) && ( mouseY - y0 > 53 && mouseY - y0 < 72 )) {
			tooltip = this.stove.hasWater() ? "Water Available" : "No Water";
		} else if (( mouseX - x0 > 79 && mouseX - x0 < 96 ) && ( mouseY - y0 > 53 && mouseY - y0 < 72 )) {
			tooltip = "Fuel";
		} else if ( mouseY - y0 > 53 && mouseY - y0 < 64 ) {
			if ( mouseX - x0 > 64 && mouseX - x0 < 114 ) {
				tooltip = this.stove.isBurning() ? String.format("Remaining Fuel: %d%%", this.pixel_progress(100, this.stove.getField(0), this.stove.getField(1))) : "Not Burning";
			}
			else if ( mouseX - x0 > 134 && mouseX - x0 < 155 ) {
				tooltip = String.format("Cooking Progress %d%%", this.pixel_progress(100, this.stove.getField(2), this.stove.getField(3)));
			}
		}

		this.fontRenderer.drawString(tooltip, 20, 6, 4210752);
	}



	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(textures);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);


		if (this.stove.isBurning())
		{
			int burn_pixels = this.pixel_progress(11, this.stove.getField(1) - this.stove.getField(0), this.stove.getField(1) ); // fuel burn completion in pixels?

			this.drawTexturedModalRect(
					x + 66,   // x
					y + 55 + burn_pixels,   // y

					176, //   TX
					burn_pixels, // TY

					10,                 //  w
					11 - burn_pixels//   h
					);
			this.drawTexturedModalRect(
					x + 100,   // x
					y + 55 + burn_pixels,   // y

					176, //   TX
					burn_pixels, // TY

					10,                 //  w
					11 - burn_pixels//   h
					);

			int cook_pixels = this.pixel_progress(20, this.stove.getField(2), this.stove.getField(3));
			this.drawTexturedModalRect(
					x + 134,
					y + 55,
					176,  // tx
					12,   // ty
					cook_pixels, // W
					11);             // H

		}

		if (this.stove.hasWater()){
			this.drawTexturedModalRect(
					x + 25,
					y + 56,
					176,  // tx
					23 + 16 - ((frames/7) % 16),   // ty
					14, // W
					14);             // H
			frames++;
		}
	}

	private int pixel_progress(final int pixels, final int passed, final int max)
	{
		// i am guessing that this is to guard against /0
		//final int max = ;
		//final int passed = ;
		// if the timer has dinged or the numbers don't add up: fuck it
		if (passed >= max || max == 0) { return pixels; }
		final double progress = (double)passed / (double)max;      
		final int result = (int)Math.floor(progress * (double)pixels);        
		return result;

	}
}

