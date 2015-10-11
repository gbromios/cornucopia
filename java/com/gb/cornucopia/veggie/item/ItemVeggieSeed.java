package com.gb.cornucopia.veggie.item;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.veggie.block.BlockVeggieCrop;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ItemVeggieSeed extends Item implements IPlantable{
	private BlockVeggieCrop crop;
	public final String name; 
	
	public ItemVeggieSeed(String name){
		super();
		this.name = "veggie_" + name + "_seed";
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(CornuCopia.tabSeedVeg);
		GameRegistry.registerItem(this, this.name);

	}
	
	public void setCrop(BlockVeggieCrop crop){
		this.crop = crop;
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		
		// may elect to put biome restrictions here?
		
        if (side != EnumFacing.UP || !player.canPlayerEdit(pos.offset(side), side, stack))
        {
            return false;
        }
        else if (world.getBlockState(pos).getBlock().canSustainPlant(world, pos, EnumFacing.UP, this) && world.isAirBlock(pos.up()))
        {
            world.setBlockState(pos.up(), this.crop.getDefaultState());
            --stack.stackSize;
            return true;
        }
        else
        {
            return false;
        }
    }

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		// TODO: can make this changeable if needed, but for now all crops grow on farmland
		return EnumPlantType.Crop;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		return this.crop.getDefaultState();
	}

}
