package com.gb.cornucopia.veggie;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.Settings;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockVeggieWild extends BlockBush implements IPlantable {
	private EnumPlantType plantType;
	public final String name;
	private ItemVeggieRaw raw;
	private ItemVeggieSeed seed;

	public BlockVeggieWild(final String name, final EnumPlantType plantType) {
		super();
		this.name = String.format("veggie_%s_wild", name);
		this.setTickRandomly(true);
		this.setUnlocalizedName(this.name);
		this.setRegistryName(this.name);
		this.setCreativeTab(CornuCopia.tabVeggies);
		this.plantType = plantType;
		InvModel.add(this);
	}

	public void setDrops(final ItemVeggieRaw raw, final ItemVeggieSeed seed) {
		this.raw = raw;
		this.seed = seed;
	}

	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return ColorizerGrass.getGrassColor(0.5D, 1.0D);
	}

	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state) {
		return this.getBlockColor();
	}

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
		return BiomeColorHelper.getGrassColorAtPos(world, pos);
	}

	@Override
	public EnumPlantType getPlantType(final IBlockAccess world, final BlockPos pos) {
		return this.plantType;
	}

	@Override
	public IBlockState getPlant(final IBlockAccess world, final BlockPos pos) {
		return this.getDefaultState();
	}

	public boolean isReplaceable(final World world, final BlockPos pos) {
		return false;
	}

	@Override
	public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
		final List<ItemStack> drop_stacks = new java.util.ArrayList<ItemStack>();
		// drop 1-2 seeds and 1-2 raw
		drop_stacks.add(new ItemStack(this.seed));
		if (RANDOM.nextInt(3) > 0) {
			drop_stacks.add(new ItemStack(this.seed));
		}
		drop_stacks.add(new ItemStack(this.raw));
		if (RANDOM.nextInt(3) == 0) {
			drop_stacks.add(new ItemStack(this.raw));
		}

		return drop_stacks;
	}

	public IBlockColor getWildColor() {
		return (state, worldIn, pos, tintIndex) -> tintIndex == 0
				? (worldIn != null && pos != null
				? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos)
				: ColorizerFoliage.getFoliageColorBasic())
				: -1;
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, ItemStack stack, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		//Block.spawnAsEntity(world, pos, new ItemStack(this));
		//world.setBlockToAir(pos);
		return false;
	}

	@Override
	public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
		if (!Settings.wild_veggie_spread || RANDOM.nextInt(Settings.wild_veggie_spread_rate) != 0) return;
		// TODO: add config options to control density of wild veggies?
		final int search_radius = 3;
		int max_wild_veggies = 3;

		for (int x = -search_radius; x <= search_radius; x++) {
			for (int y = -search_radius; y <= search_radius; y++) {
				for (int z = -search_radius; z <= search_radius; z++) {
					// TODO: add option to count wild veggies types separately?
					if (world.getBlockState(pos.add(x, y, z)).getBlock() == this) {
						if (--max_wild_veggies == 0) return;
					}
				}
			}
		}
		// pick a random block somewhere in the radius and try to grow a new wild veggies there. wont always work.
		BlockPos grow_at = pos.add(
				RANDOM.nextInt(5) - 2,
				RANDOM.nextInt(2),
				RANDOM.nextInt(5) - 2
		);

		IBlockState grow_state = world.getBlockState(grow_at.down());
		if (
				world.isAirBlock(grow_at) &&
						grow_state.getBlock().canSustainPlant(grow_state, world, grow_at.down(), EnumFacing.UP, this)

				) {
			//world.setBlockState(grow_at, new BlockState(this), 0);
			world.setBlockState(grow_at, this.getDefaultState());
		}
	}

}