package com.gb.cornucopia.farming.veggie;

import com.gb.cornucopia.CornuCopia;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemVeggieSeed extends ItemSeeds {
    public final String name;

    public ItemVeggieSeed(String name, BlockVeggieCrop crop) {
        super(Block.REGISTRY.getObject(new ResourceLocation("veggie_" + name + "_crop")), Blocks.FARMLAND); // TODO tie the plantable block to the crop
        this.name = "veggie_" + name + "_seed";
        this.setUnlocalizedName(this.name);
        this.setCreativeTab(CornuCopia.tabVeggies);
    }
}