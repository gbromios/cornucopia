package com.gb.cornucopia.cookery.brewing;

import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cuisine.Cuisine;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// barrel is built with wood + iron + cooking product
// e.g wood + grape juice = wine barrel
// barrel can be placed + takes time to ferment
// once the barrel is ready, it can be broken open to drop finished product + some scrap
// bubbling animation to show completeness
// might like to make this process a little more involved but this will be good for now

public class BlockBarrel extends BlockBarrelEmpty implements ITileEntityProvider {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
	public final int last_age;
	private final ItemStack[] drops;
	private final Item[] inputs; // so we know wether to drop container items
	private final int fermentation_time;

	public static final int DEFUALT_F_TIME = (int) (3.0 * 7.2e+7); // a few days IRL

	public BlockBarrel(String name, int last_stage, ItemStack[] drops, Item[] inputs, boolean juice_flag) {
		this(name, last_stage, drops, inputs, DEFUALT_F_TIME, juice_flag);
	}

	public BlockBarrel(String name, int last_stage, ItemStack[] drops, Item[] inputs, int f_time, final boolean juice_flag) {
		super(name);
		if (last_stage < 1 || last_stage > 3) {
			throw new RuntimeException(String.format("error initializing %s barrel: must have one drop per stage, last_stage %d != 1..3\n", name, last_stage));
		}
		if (drops.length != last_stage) {
			throw new RuntimeException(String.format("invalid drops for %s barrel-  must be = to number of stages %d \n %s\n", name, last_stage, drops));
		}
		this.drops = drops.clone();
		this.inputs = inputs.clone();
		this.last_age = Math.min(3, Math.max(1, last_stage)); // must be 1, 2, or 3 
		this.fermentation_time = f_time;
		this.setDefaultState(super.getDefaultState().withProperty(AGE, 0));
		GameRegistry.registerTileEntity(TileEntityBarrel.class, String.format("brew_%s_entity", name));

//		if (juice_flag) { TODO fix these recipes
//			// TODO: this is bad sorry
//
//			final String juice = inputs[0] == Cuisine.apple_juice ? "juiceCider" : "juiceCordial";
//			GameRegistry.addRecipe(new ShapedOreRecipe(this, true, new Object[]{
//					" S ", "JJJ", " B ",
//					'S', Blocks.WOODEN_SLAB,
//					'B', Cookery.empty_barrel,
//					'J', juice
//			}));
//
//		} else {
//
//			if (inputs.length == 1) {
//				GameRegistry.addShapedRecipe(new ItemStack(this),
//						" S ", " I ", " B ",
//						'S', Blocks.WOODEN_SLAB,
//						'B', Cookery.empty_barrel,
//						'I', inputs[0]
//				);
//			} else if (inputs.length == 2) {
//				GameRegistry.addShapedRecipe(new ItemStack(this),
//						" S ", "JI ", " B ",
//						'S', Blocks.WOODEN_SLAB,
//						'B', Cookery.empty_barrel,
//						'I', inputs[0],
//						'J', inputs[1]
//				);
//			} else if (inputs.length == 3) {
//				GameRegistry.addShapedRecipe(new ItemStack(this),
//						" S ", "JIK", " B ",
//						'S', Blocks.WOODEN_SLAB,
//						'B', Cookery.empty_barrel,
//						'I', inputs[0],
//						'J', inputs[1],
//						'K', inputs[2]
//				);
//			} else {
//				throw new RuntimeException(String.format("invalid recipe for %s barrel- needs 1, 2 or 3 inputs\n%s\n", name, inputs));
//			}
//		}
	}

	public boolean fermented(long t) {
		return t >= this.fermentation_time;
	}

	public boolean isRipe(IBlockState state) {
		// stage >= 1
		return (int) state.getValue(AGE) >= 1;
	}


	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{AGE, AXIS, UNDER_BARREL});
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return super.getStateFromMeta(meta).withProperty(AGE, meta & 3);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return (Integer) state.getValue(AGE) | super.getMetaFromState(state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		//world.setBlockState(pos, state.withProperty(AGE, ((int)state.getValue(AGE) + 1) % 16 ));
		//return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
		return false;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		final List<ItemStack> drops = new ArrayList<>();
		if ((int) state.getValue(AGE) > 0) {
			drops.add(this.drops[(int) state.getValue(AGE) - 1].copy());
			drops.add(new ItemStack(Cookery.empty_barrel));
			for (Item i : this.inputs) {
				if (i.hasContainerItem()) {
					drops.add(new ItemStack(i.getContainerItem()));
				}
			}
		} else {
			drops.add(new ItemStack(this));
		}

		return drops;

	}

	;

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, final BlockPos pos, Random rand) {
		final int age = (int) stateIn.getValue(AGE);

		if (age >= rand.nextFloat() * 3) {
			double pX = pos.getX() + 0.5;
			double pY = pos.getY() + 1;
			double pZ = pos.getZ() + 0.5;

			double mX = rand.nextGaussian() * 0.02D;
			double mY = rand.nextGaussian() * 0.02D;
			double mZ = rand.nextGaussian() * 0.02D;

			worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pX, pY, pZ, mX, mY, mZ);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBarrel();
	}

}
