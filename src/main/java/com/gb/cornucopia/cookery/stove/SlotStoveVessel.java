package com.gb.cornucopia.cookery.stove;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotStoveVessel extends SlotItemHandler {

	private final IItemHandler itemHandler;
	private final int index;

	public SlotStoveVessel(final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
		this.itemHandler = itemHandler;
		this.index = index;
	}

	public boolean isItemValid(ItemStack stack){
		return false;
/*		//Item valid if it is a type of cookware
		return stack.getItem() instanceof ItemCookWare;*/
	}

	
	@SideOnly(Side.CLIENT)
	public String getSlotTexture() { return backgroundName; }
	@SideOnly(Side.CLIENT)
	public boolean canBeHovered()  { return true; }
	

	/*========================================= FORGE START =====================================*/
	protected String backgroundName = null;
	protected net.minecraft.util.ResourceLocation backgroundLocation = null;
	protected Object backgroundMap;
	/**
	 * Gets the path of the texture file to use for the background image of this slot when drawing the GUI.
	 * @return String: The texture file that will be used in GuiContainer.drawSlotInventory for the slot background.
	 */
	@SideOnly(Side.CLIENT)
	public net.minecraft.util.ResourceLocation getBackgroundLocation()
	{
		return (backgroundLocation == null ? net.minecraft.client.renderer.texture.TextureMap.LOCATION_BLOCKS_TEXTURE : backgroundLocation);
	}

	/**
	 * Sets the texture file to use for the background image of the slot when it's empty.
	 *  textureFilename String: Path of texture file to use, or null to use "/gui/items.png"
	 */
	@SideOnly(Side.CLIENT)
	public void setBackgroundLocation(net.minecraft.util.ResourceLocation texture)
	{
		this.backgroundLocation = texture;
	}

	/**
	 * Sets which icon index to use as the background image of the slot when it's empty.
	 * Getter is func_178171_c
	 *  icon The icon to use, null for none
	 */
	public void setBackgroundName(String name)
	{
		this.backgroundName = name;
	}

	@SideOnly(Side.CLIENT)
	public net.minecraft.client.renderer.texture.TextureAtlasSprite getBackgroundSprite()
	{
		String name = getSlotTexture();
		return name == null ? null : getBackgroundMap().getAtlasSprite(name);
	}

	@SideOnly(Side.CLIENT)
	protected net.minecraft.client.renderer.texture.TextureMap getBackgroundMap()
	{
		if (backgroundMap == null) backgroundMap = Minecraft.getMinecraft().getTextureMapBlocks();
		return (net.minecraft.client.renderer.texture.TextureMap)backgroundMap;
	}

}