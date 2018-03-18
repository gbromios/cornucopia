package com.gb.cornucopia.farming.fruit;

import com.gb.cornucopia.InvModel;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockFruitLeaf extends BlockLeaves {
    public final String name;
    private BlockFruitCrop crop;
    public static final PropertyBool SAPLING_GENERATOR = PropertyBool.create("sapling_generator");

    public BlockFruitLeaf(final String name) {
        super();
        this.name = "fruit_" + name + "_leaf";
        //this.setGraphicsLevel(true); // FUCK IT
        this.setUnlocalizedName(this.name);
        this.setCreativeTab(null);
        this.setDefaultState(
                this.blockState.getBaseState()
                        .withProperty(CHECK_DECAY, Boolean.valueOf(true))
                        .withProperty(DECAYABLE, Boolean.valueOf(true))
                        .withProperty(SAPLING_GENERATOR, Boolean.valueOf(false)) // other leaves use the first two bits as VARIANT
        );

        GameRegistry.registerBlock(this, this.name);
        InvModel.add(this, "oak_leaves", "minecraft"); // TODO: maybe dont event have these in creative??
    }

    @Override
    public EnumType getWoodType(final int meta) {
        return EnumType.OAK;
    }

    public void makeSaplings(final World world, final BlockPos pos) {

    }

    public void setGrows(final BlockFruitCrop crop) {
        this.crop = crop;
    }

    @Override
    public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
        // this does all the brutal leaf decay code
        super.updateTick(world, pos, state, rand);
        // super call might destroy the block so make sure it's still there afterwards
        if (world.getBlockState(pos).getBlock() != this) {
            return;
        }

        // fruits spawn in open air below a leaf only
        if (!world.isAirBlock(pos.down())) {
            return;
        }

        // watch for crowding
        int max_neighbors = 1;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if ((world.getBlockState(pos.add(x, -1, z)).getBlock() == this.crop) && --max_neighbors < 1) {
                    return;
                }
            }
        }
        if (rand.nextInt(64) == 0) {
            world.setBlockState(pos, state.withProperty(SAPLING_GENERATOR, true));
        }

        world.setBlockState(pos.down(), this.crop.getDefaultState().withProperty(
                BlockFruitCrop.DROP_SAPLING,
                state.getValue(SAPLING_GENERATOR) && rand.nextInt(4) == 0
        ));

    }

    // 1 2 4 8
    //   ^     - when this leaf spawns a fruit: will the fruit drop a sapling?
    //     ^ ^ - no fucking idea, BlockLeaf uses these to check for decay. 4 seems to work in reverse but i just copied BlockNewLeaf for those...
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState()
                .withProperty(SAPLING_GENERATOR, (meta & 2) == 2)
                .withProperty(DECAYABLE, (meta & 4) == 0)
                .withProperty(CHECK_DECAY, (meta & 8) == 8)
                ;
    }

    public int getMetaFromState(final IBlockState state) {
        return (state.getValue(SAPLING_GENERATOR) ? 2 : 0)
                | (state.getValue(DECAYABLE) ? 0 : 4)
                | (state.getValue(CHECK_DECAY) ? 8 : 0)
                ;

    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE, SAPLING_GENERATOR);
    }


    //region MOJANG PLS
    // fruit trees don't drop shit when you break em
    //private void destroy(final World world, final BlockPos pos)
    //{
    //world.setBlockToAir(pos);
    //}

    public int quantityDropped(final Random random) {
        return 0;
    }

    public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
        return Item.getItemFromBlock(Blocks.SAPLING);
    }

    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        return;
    }

    @Override
    public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
        return new ArrayList<ItemStack>();
    }

    //aint shearin shit
    @Override
    public boolean isShearable(final ItemStack item, final IBlockAccess world, final BlockPos pos) {
        return false;
    }

    @Override
    public List<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final BlockPos pos, final int fortune) {
        return new ArrayList<ItemStack>();
    }

    //endregion

}
