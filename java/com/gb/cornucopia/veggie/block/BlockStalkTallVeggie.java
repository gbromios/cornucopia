package com.gb.cornucopia.veggie.block;

import com.gb.cornucopia.CornuCopia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockStalkTallVeggie extends BlockBush{
	public final String name;
	public final BlockCropTallVeggie crop;
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

	public BlockStalkTallVeggie(String name, BlockCropTallVeggie crop) {
		super(Material.plants);
		this.name = "veggie_" + name + "_stalk";
		this.crop = crop;
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		
		GameRegistry.registerBlock(this, this.name);
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
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{

		return
				// if stalks are ever 2 grow, we must allow (this) to be below itself 
				(world.getBlockState(pos.down()).getBlock() == Blocks.farmland)
				// crop has to be above
				&& (world.getBlockState(pos.up()).getBlock() == this.crop)
				;
	}

	@Override
	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state)
	{
		if (!this.canBlockStay(world, pos, state))
		{
			// exact same as the parent but don't drop anything :I
			world.setBlockToAir(pos);
		}
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { AGE });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer)state.getValue(AGE));
	}
}
