package com.gb.cornucopia.veggie;

import com.gb.cornucopia.InvModel;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockVeggieStalk extends BlockBush {
	public final String name;
	public final BlockVeggieCropTall crop;
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

	public BlockVeggieStalk(final String name, final BlockVeggieCropTall crop) {
		super(Material.PLANTS);
		this.name = String.format("veggie_%s_stalk", name);
		this.crop = crop;
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		this.setCreativeTab(null);
		GameRegistry.register(this);
		InvModel.add(this, this.name);
	}

	/* probably not going to bother implementing plants that grow higher than 2, but it wouldnt be hard :3
	public int height(World world, BlockPos pos){
		if (world.getBlockState(pos.down()).getBlock() == this){
			return 1 + this.height(world, pos.down());
		}
		return 1;
	}
	 */

	@Override
	public boolean canBlockStay(final World world, final BlockPos pos, final IBlockState state) {
		return
				// if stalks are ever 2 grow, we must allow (this) to be below itself 
				(world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND)
						// crop has to be above
						&& (world.getBlockState(pos.up()).getBlock() == this.crop)
				;
	}

	@Override
	protected void checkAndDropBlock(final World world, final BlockPos pos, final IBlockState state) {
		if (!this.canBlockStay(world, pos, state)) {
			// exact same as the parent but don't drop anything :I
			world.setBlockToAir(pos);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{AGE});
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(AGE));
	}
}
