package com.gb.cornucopia.brewing;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Vessel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

// barrel is built with wood + iron + cooking product
// e.g wood + grape juice = wine barrel
// barrel can be placed + takes time to ferment
// once the barrel is ready, it can be broken open to drop finished product + some scrap
// bubbling animation to show completeness
// might like to make this process a little more involved but this will be good for now

public class BlockBarrel extends Block{
	// i think this is a case where i should actually use the tile entity to track time... hue hue hue
	//public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
	public static final PropertyBool FERMENTED = PropertyBool.create("fermented");
	public final String name;
	
	public BlockBarrel(String name){
		super(Material.wood);
		this.name = "brew_"+name+"_barrel";
		this.setUnlocalizedName(this.name);
		this.setHardness(5.0F);
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FERMENTED, false));
		//this.setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.95F, 0.7F);
		GameRegistry.registerBlock(this, this.name);
		InvModel.add(this, this.name);
	}
	

	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	@Override
	public boolean isFullCube(){
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(FERMENTED, (meta & 1) == 1);

	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {FERMENTED});
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return (boolean)state.getValue(FERMENTED) ? 1 : 0;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		world.setBlockState(pos, state.withProperty(FERMENTED, !(boolean)state.getValue(FERMENTED)));
		//return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
		
		return true;
		
	}
	
	
	
}
