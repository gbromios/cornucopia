package com.gb.cornucopia.farming.fruit;

import com.gb.cornucopia.InvModel;
import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockFruitCrop extends BlockCrops {
    public static final PropertyBool DROP_SAPLING = PropertyBool.create("drop_sapling");
    public final String name;

    private BlockFruitLeaf leaf; // so we know where it's okay to chill.
    private ItemFruitRaw raw;
    private BlockFruitSapling sapling;

    public BlockFruitCrop(final String name) {
        super();
        this.name = "fruit_" + name + "_crop";
        this.setUnlocalizedName(this.name);
        this.setCreativeTab(null);
        GameRegistry.registerBlock(this, this.name);
        InvModel.add(this, this.name);
    }

    public void setLeaf(final BlockFruitLeaf leaf) {
        this.leaf = leaf;
    }

    public void setDrops(final ItemFruitRaw raw, final BlockFruitSapling sapling) {
        this.raw = raw;
        this.sapling = sapling;
    }

    @Override
    public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, EnumHand hand, ItemStack stack, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        // not your normal bonemeal activatation...
        if (!world.isRemote && EnumDyeColor.byDyeDamage(stack.getItemDamage()) == EnumDyeColor.WHITE) {
            world.setBlockState(pos, state.withProperty(DROP_SAPLING, false));
        }
        return false;
    }

    @Override
    public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
        if (rand.nextInt(32) == 0 && this.canGrow(world, pos, state, true)) { //1/8 chance to grow on tick
            this.grow(world, rand, pos, state);
        }
    }

    @Override //TODO what's up with the weird error on this?
    public java.util.List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
        final List<ItemStack> ret = new ArrayList<ItemStack>();
        if ((Boolean) state.getValue(DROP_SAPLING)) {
            ret.add(new ItemStack(this.sapling));
        }
        if ((Integer) state.getValue(AGE) == 3) {

            ret.add(new ItemStack(this.raw));
            ret.add(new ItemStack(this.raw));
        }
        return ret;
    }

    @Override
    public void grow(final World world, final Random rand, final BlockPos pos, final IBlockState state) {
        world.setBlockState(
                pos,
                state.withProperty(AGE, Math.min(((Integer) state.getValue(AGE)) + 1, 3)).withProperty(DROP_SAPLING, state.getValue(DROP_SAPLING)),
                2
        );
    }

    @Override
    public void onNeighborBlockChange(final World world, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!(world.getBlockState(pos.up()).getBlock() instanceof BlockLeaves)) {
            if ((Integer) state.getValue(AGE) == 3) {

                spawnAsEntity(world, pos, new ItemStack(this.raw));
                spawnAsEntity(world, pos, new ItemStack(this.raw));
            }
            if ((Boolean) state.getValue(DROP_SAPLING)) {
                spawnAsEntity(world, pos, new ItemStack(this.sapling));
            }
            world.setBlockToAir(pos);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{AGE, DROP_SAPLING});
    }

    @Override
    public IBlockState getStateFromMeta(final int meta) {
        // 1 2 4 8
        // ^ ^     - age of the growing fruit 0-3
        //     ^   - whether the fruit should drop a sapling,
        return getDefaultState().withProperty(AGE, meta & 3).withProperty(DROP_SAPLING, (meta & 4) == 4);
    }

    @Override
    public int getMetaFromState(final IBlockState state) {
        return ((Integer) state.getValue(AGE) | ((Boolean) state.getValue(DROP_SAPLING) ? 4 : 0));
    }
}
