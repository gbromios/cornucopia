package com.gb.cornucopia.farming.veggie.item;

import com.gb.cornucopia.CornuCopia;

import com.gb.cornucopia.farming.veggie.block.BlockVeggieCrop;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

public class ItemVeggieSeed extends ItemSeeds {
    private BlockVeggieCrop crop;
    public final String name;

    public ItemVeggieSeed(String name, BlockVeggieCrop crop) {
        super(crop, Blocks.FARMLAND); // TODO tie the plantable block to the crop
        setCrop(crop);
        this.name = "veggie_" + name + "_seed";
        this.setUnlocalizedName(this.name);
        this.setCreativeTab(CornuCopia.tabVeggies);
    }

    public void setCrop(BlockVeggieCrop crop) {
        this.crop = crop;
    }
}
