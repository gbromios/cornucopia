package com.gb.cornucopia.veggie;

import com.gb.cornucopia.CornuCopia;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemVeggieSeed extends ItemSeeds {
    public final String name;

    public ItemVeggieSeed(String name, BlockVeggieCrop crop) {
        super(crop, Blocks.FARMLAND); // TODO tie the plantable block to the crop
        this.name = String.format("veggie_%s_seed", name);
        this.setUnlocalizedName(this.name);
        this.setCreativeTab(CornuCopia.tabVeggies);
        GameRegistry.register(this);
    }
}