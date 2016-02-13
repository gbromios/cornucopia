package com.gb.cornucopia.veggie.block;

import java.util.List;
import java.util.Random;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.veggie.item.ItemVeggieRaw;
import com.gb.cornucopia.veggie.item.ItemVeggieSeed;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockVeggieWild
extends BlockBush
implements IPlantable
{	
	private EnumPlantType plantType;
	public final String name;
	private ItemVeggieRaw raw;
	private ItemVeggieSeed seed;

	public BlockVeggieWild(final String name, final EnumPlantType plantType){
		super();
		this.name = "veggie_" + name + "_wild"; 
		this.setTickRandomly(true);
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabVeggies);
		this.plantType = plantType;

		GameRegistry.registerBlock(this, this.name);
		InvModel.add(this, this.name);
	}

	public void setDrop(final ItemVeggieRaw raw, final ItemVeggieSeed seed){
		this.raw = raw;
		this.seed = seed;
	}

	@Override
	public EnumPlantType getPlantType(final IBlockAccess world, final BlockPos pos) { return this.plantType; }

	@Override
	public IBlockState getPlant(final IBlockAccess world, final BlockPos pos) { return this.getDefaultState(); }	

	public boolean isReplaceable(final World world, final BlockPos pos) { return false; }

	@Override
	public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
		final List<ItemStack> drop_stacks = new java.util.ArrayList<ItemStack>();
		drop_stacks.add(new ItemStack(this.raw));
		drop_stacks.add(new ItemStack(this.raw));
		drop_stacks.add(new ItemStack(this.seed));

		return drop_stacks;
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumFacing side, final float hitX, final float hitY, final float hitZ)
	{
		Block.spawnAsEntity(world, pos, new ItemStack(this));
		world.setBlockToAir(pos);
		return false;
	}

	@Override
	public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand){
		// TODO: add config setting to disable wild veggie spread and option to control the rate:
		// if (!config.wild_veggie_spread_enabled || RANDOM.next_int(config.wild_veggie_spread_rate) != 0) return;

		// TODO: add config options to control density of wild veggies, 
		final int search_radius = 4;
		int max_wild_veggies = 5; 

		for (int x = -search_radius; x <= search_radius; x++){
			for (int y = -search_radius; y <= search_radius; y++){
				for (int z = -search_radius; z <= search_radius; z++){
					// TODO: add option to count wild veggies types separately?
					if (world.getBlockState(pos.add(x, y, z)).getBlock() == this){
						if (--max_wild_veggies == 0) return;
					}
				}	
			}    		
		}
		// pick a random block somewhere in the radius and try to grow a new wild veggies there. wont always work.
		BlockPos grow_at = pos.add(
				RANDOM.nextInt(9) - search_radius,
				RANDOM.nextInt(2) ,
				RANDOM.nextInt(9) - search_radius
				);

		if (
				world.isAirBlock(grow_at) && 
				world.getBlockState(grow_at.down()).getBlock().canSustainPlant(world, grow_at.down(), EnumFacing.UP, this)

				){
			//world.setBlockState(grow_at, new BlockState(this), 0);
			world.setBlockState(grow_at, this.getDefaultState());
		} 
	}
	
}
