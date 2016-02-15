package com.gb.cornucopia.brewing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gb.cornucopia.CornuCopia;
import com.gb.cornucopia.InvModel;
import com.gb.cornucopia.cookery.Vessel;
import com.gb.cornucopia.cookery.stove.TileEntityStove;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// barrel is built with wood + iron + cooking product
// e.g wood + grape juice = wine barrel
// barrel can be placed + takes time to ferment
// once the barrel is ready, it can be broken open to drop finished product + some scrap
// bubbling animation to show completeness
// might like to make this process a little more involved but this will be good for now

public class BlockBarrel extends Block implements ITileEntityProvider{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
	public static final PropertyBool UNDER_BARREL = PropertyBool.create("under_barrel");
	public static final PropertyEnum AXIS = PropertyEnum.create("axis", BlockLog.EnumAxis.class);
	
	public final String name;
	public final int last_age;
	public final Item[] drops;
	public final int fermentation_time;
	
	public static final int DEFUALT_F_TIME = (int) 7.2e+7; // 20 hours in ms
	
	public BlockBarrel(String name, int last_stage, Item[] drops, Item[] inputs){
		this(name, last_stage, drops, inputs, DEFUALT_F_TIME);
	}
	
	public BlockBarrel(String name, int last_stage, Item[] drops, Item[] inputs, int f_time){
		super(Material.wood);
		if (last_stage < 1 || last_stage > 3) {
			throw new RuntimeException(String.format("error initializing %s barrel: must have one drop per stage, last_stage %d != 1..3\n", name, last_stage));
		}
		if (drops.length != last_stage) {
			throw new RuntimeException(String.format("invalid drops for %s barrel-  must be = to number of stages %d \n %s\n", name, last_stage, drops));
		}
		this.drops = drops.clone();
		this.last_age = Math.min(3, Math.max(1, last_stage)); // must be 1, 2, or 3 
		this.name = "brew_"+name+"_barrel";
		this.fermentation_time = f_time;
		this.setUnlocalizedName(this.name);
		this.setHardness(1.8F);
		this.setCreativeTab(CornuCopia.tabCookery);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(AXIS, EnumAxis.Y));
		//this.setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.95F, 0.7F);
		GameRegistry.registerBlock(this, this.name);
		GameRegistry.registerTileEntity(TileEntityBarrel.class, "brew_barrel_entity");
		InvModel.add(this, this.name);
		
		if (inputs.length == 1) {
			GameRegistry.addShapedRecipe(new ItemStack(this),
					" S ", " I ", " B ",
					'S', Blocks.wooden_slab,
					'B', Brewing.empty_barrel,
					'I', inputs[0]
					);	
		} else if (inputs.length == 2) {
			GameRegistry.addShapedRecipe(new ItemStack(this),
					" S ", "JI ", " B ",
					'S', Blocks.wooden_slab,
					'B', Brewing.empty_barrel,
					'I', inputs[0],
					'J', inputs[1]
					);			
		} else if (inputs.length == 3) {
			GameRegistry.addShapedRecipe(new ItemStack(this),
					" S ", "JIK", " B ",
					'S', Blocks.wooden_slab,
					'B', Brewing.empty_barrel,
					'I', inputs[0],
					'J', inputs[1],
					'K', inputs[2]
					);
		}  else {
			throw new RuntimeException(String.format("invalid recipe for %s barrel- needs 1, 2 or 3 inputs\n%s\n", name, inputs));
		}
		
		
	}
	
	public boolean isRipe(IBlockState state){
		// stage >= 1
		return (int)state.getValue(AGE) >= 1 ;
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	@Override
	public boolean isFullCube(){
		return false;
	}
	
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
    	return this.getDefaultState().withProperty(AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
    }

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(AGE, meta & 3).withProperty(AXIS, EnumAxis.values()[(meta & 12) >> 2]);
	}
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(
				UNDER_BARREL,
				state.getValue(AXIS) != EnumAxis.Y && world.getBlockState(pos.up()).getBlock() instanceof BlockBarrel
				);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {AGE, AXIS, UNDER_BARREL});
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return (Integer)state.getValue(AGE) | ((EnumAxis)state.getValue(AXIS)).ordinal() << 2;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		//world.setBlockState(pos, state.withProperty(AGE, 0));
		//return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
		return false;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		final List<ItemStack> drops = new ArrayList<>();
		if ((int)state.getValue(AGE) > 0) {
			drops.add(new ItemStack(this.drops[(int)state.getValue(AGE) - 1], 4)); // drop 4 for now, might add mechanics for that later
			drops.add(new ItemStack(Brewing.empty_barrel));
		}
		else {
			drops.add(new ItemStack(this));
		}
		
		return drops;
		
	};
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final World world, final BlockPos pos, final IBlockState state, final Random rand){
		final int age = (int) state.getValue(AGE); 
		
		if (age >= rand.nextFloat() * 3){
			double pX = pos.getX() + 0.5;
			double pY = pos.getY() + 1;
			double pZ = pos.getZ() + 0.5;
			
		    double mX = rand.nextGaussian() * 0.02D;
		    double mY = rand.nextGaussian() * 0.02D;
		    double mZ = rand.nextGaussian() * 0.02D;
		    
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pX, pY, pZ, mX, mY, mZ);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBarrel();
	}
	
	
	
}
