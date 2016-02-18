package com.gb.cornucopia.bees.apiary;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiApiary extends GuiContainer {
	// copies GuiCrafting -- change later.... again. especially because the slots will not line up in any way, lol
	private ResourceLocation textures = new ResourceLocation("cornucopia:textures/gui/container/bee_apiary.png");
	public GuiApiary(World world, InventoryPlayer player_inventory, BlockPos pos) {
		// will this crash if the wrong kind of tile entity is there? probably lol
		super(new ContainerApiary(player_inventory, (IInventory)world.getTileEntity(pos), world, pos));
		// find out for sure!!
		if (!(world.getTileEntity(pos) instanceof TileEntityApiary)){
			throw new RuntimeException(String.format("somehow GuiApiary is getting a %s at %s ???", world.getTileEntity(pos), pos));
		}

	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
	{
		this.fontRendererObj.drawString("Hive", 20, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
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
