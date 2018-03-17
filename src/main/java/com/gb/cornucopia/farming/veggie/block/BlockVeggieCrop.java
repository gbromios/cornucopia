package com.gb.cornucopia.farming.veggie.block;

import com.gb.cornucopia.farming.veggie.item.ItemVeggieRaw;
import com.gb.cornucopia.farming.veggie.item.ItemVeggieSeed;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockVeggieCrop extends BlockCrops {
    private ItemVeggieSeed seed;
    private ItemVeggieRaw raw;
    public final String name;

    BlockVeggieCrop(final String name) {
        super();
        this.name = "veggie_" + name + "_crop";
        this.setUnlocalizedName(this.name);
        this.setCreativeTab(null);
    }

    public void setDrops(final ItemVeggieRaw raw, final ItemVeggieSeed seed) {
        this.seed = seed;
        this.raw = raw;
    }

    @Override
    protected Item getSeed() {
        return seed;
    }

    @Override
    protected Item getCrop() {
        return raw;
    }

    @SideOnly(Side.CLIENT) // TODO find out what replaced this (or if it's necessary!)
    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5D, 1.0D);
    }

    @SideOnly(Side.CLIENT) // TODO find out what replaced this (or if it's necessary!)
    public int getRenderColor(IBlockState state) {
        return this.getBlockColor();
    }

    @SideOnly(Side.CLIENT) // TODO find out what replaced this (or if it's necessary!)
    public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
        return BiomeColorHelper.getGrassColorAtPos(world, pos);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (this.canGrow(world, pos, state, true) && this.shouldGrow(world, pos, rand)) {
            this.grow(world, rand, pos, state);
        }
        super.updateTick(world, pos, state, rand);
    }

    @Override
    public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        // TODO: make drops better. this works for now
        java.util.List<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this.seed));

        if ((Integer) state.getValue(AGE) == getMaxAge()) {
            // after three grows, veggie is ready to harvest (might spawn a bonus seed)
            ret.add(new ItemStack(this.raw));
            if (RANDOM.nextInt(3) == 0) {
                ret.add(new ItemStack(this.seed));
            }

        }

        return ret;
    }

    private boolean shouldGrow(World world, BlockPos pos, Random rand) {
        float chance = 0.8F;
        // this is essentially where I'd add biome/crop rotation mechanics
        // either a straight up manual mapping or temp/elevation/rainfall based

        // every neighboring plant cuts the chance in half
        List<Block> blocks = getSurroundingBlocks(world, pos.down());
        for (Block block : blocks) {
            if (block == this) chance /= 2F;
        }
        return rand.nextFloat() < chance;
    }

    private static List<Block> getSurroundingBlocks(World world, BlockPos pos) {
        List<Block> blocks = new ArrayList<Block>();
        blocks.add(world.getBlockState(pos.north()).getBlock());
        blocks.add(world.getBlockState(pos.east()).getBlock());
        blocks.add(world.getBlockState(pos.south()).getBlock());
        blocks.add(world.getBlockState(pos.west()).getBlock());
        return blocks;
    }
}
