package com.gb.cornucopia.cookery.stove;

import com.gb.cornucopia.cookery.Vessel;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotStoveVessel extends Slot {
	private final TileEntityStove stove;
	public SlotStoveVessel(final TileEntityStove stove, final int index, final int xPosition, final int yPosition)
	{
		super(null, index, yPosition, yPosition);
		this.stove = stove;
	}

	// fucking does nothing man
	public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) {}
	protected void onCrafting(ItemStack stack, int amount) {}
	protected void onCrafting(ItemStack stack) {}
	public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack){}
	public boolean isItemValid(ItemStack stack){ return false; }

	public boolean getHasStack() {return false; }
	public void putStack(ItemStack stack){}
	public void onSlotChanged() {}
	public int getSlotStackLimit() {return 0;}
	public int getItemStackLimit(ItemStack stack) {return 0;}

	public ItemStack decrStackSize(int amount) {return null;}
	public boolean canTakeStack(EntityPlayer playerIn) {return false;}

	public boolean isSlotInInventory(IInventory inv, int slotIn){
		return inv == this.stove && slotIn == this.slotNumber;
	}
	
	public ItemStack getStack() {
		final Vessel v = this.stove.getVessel();
		if (v != Vessel.NONE) {
			return new ItemStack(v.getItem());
		} else {
			return null;
		}
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
		return (backgroundLocation == null ? net.minecraft.client.renderer.texture.TextureMap.locationBlocksTexture : backgroundLocation);
	}

	/**
	 * Sets the texture file to use for the background image of the slot when it's empty.
	 * @param textureFilename String: Path of texture file to use, or null to use "/gui/items.png"
	 */
	@SideOnly(Side.CLIENT)
	public void setBackgroundLocation(net.minecraft.util.ResourceLocation texture)
	{
		this.backgroundLocation = texture;
	}

	/**
	 * Sets which icon index to use as the background image of the slot when it's empty.
	 * Getter is func_178171_c
	 * @param icon The icon to use, null for none
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