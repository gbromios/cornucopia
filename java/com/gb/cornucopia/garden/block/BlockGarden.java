package com.gb.cornucopia.garden.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gb.cornucopia.HarvestCraft;
import com.gb.cornucopia.garden.Gardens;
import com.gb.cornucopia.veggie.Veggie;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockGarden
	extends BlockBush
	implements IPlantable
	{	
		private EnumPlantType plantType;
		private ArrayList<Item> drops;
		public final String name;
	
	public BlockGarden(String name, EnumPlantType plantType){
		super();
		this.name = "garden_" + name; 
		this.setTickRandomly(true);
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(HarvestCraft.tabGarden);
		this.plantType = plantType;
		this.drops = new ArrayList<Item>();
 
		GameRegistry.registerBlock(this, this.name);
	}
	
	public BlockGarden addDrop(Item item){
		this.drops.add(item);
		return this;
	}
	public BlockGarden addVeggie(Veggie veg){
		// TODO: depending on config, drop either vegetables or seeds
		this.addDrop(veg.raw);
		
		// register the garden so full grown crops have a chance to drop it
		veg.crop.setGarden(this);
		
		return this;
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) { return this.plantType; }

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) { return this.getDefaultState(); }	

    public boolean isReplaceable(World worldIn, BlockPos pos) { return false; }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	List<ItemStack> drop_stacks = new java.util.ArrayList<ItemStack>();
    	
    	// very small chance to drop a garden! TODO: make this configurable
    	if (RANDOM.nextInt(150) == 0){
    		drop_stacks.add(new ItemStack(this));
    	}
    	
    	// if no drops are configured, just stop now :C
        if (this.drops.isEmpty()){
        	return drop_stacks;
        }
        
        // default 1/4 chance 5 times.
        for (int i = 0; i < 5; i++){
        	if (RANDOM.nextInt(3) == 0) { 
        		drop_stacks.add(getRandomDrop());
        	}
        }
       
        // always give at least one food. TODO: configurable???
        if (drop_stacks.isEmpty()) {
        	drop_stacks.add(getRandomDrop());
        }
       
        return drop_stacks;
    }
    
    private ItemStack getRandomDrop() { // will IndexOutOfBoundsException if there's no drops 
    	return new ItemStack(this.drops.get(RANDOM.nextInt(this.drops.size())));
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	// TODO: add configurable option to disable garden block drops
    	this.spawnAsEntity(worldIn, pos, new ItemStack(this));
    	worldIn.setBlockToAir(pos);
        return false;
    }
	
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
    	// TODO: add config setting to disable garden spread and option to control the rate:
    	// if (!config.garden_spread_enabled || RANDOM.next_int(config.garden_spread_rate) != 0) return;
    	
    	// TODO: add config options to control density of gardens, 
    	// check surrounding blocks, pam does this in 9x9x9 block; i.e. x-4..x+4, y-4..y+4, z-4..z+4
    	int search_radius = 4;
    	int max_gardens = 5; 
    	
    	for (int x = -search_radius; x <= search_radius; x++){
        	for (int y = -search_radius; y <= search_radius; y++){
            	for (int z = -search_radius; z <= search_radius; z++){
            		// TODO: add option to count garden types separately?
            		if (worldIn.getBlockState(pos.add(x, y, z)).getBlock() == this){
            			if (--max_gardens == 0) return;
            		}
            	}	
        	}    		
    	}
    	// pick a random block somewhere in the radius and try to grow a new garden there. wont always work.
    	BlockPos grow_at = pos.add(
				search_radius - RANDOM.nextInt(9),
				search_radius - RANDOM.nextInt(9),
				search_radius - RANDOM.nextInt(9)
				);
    	
    	if (
    			worldIn.isAirBlock(grow_at) && 
    			worldIn.getBlockState(grow_at.down()).getBlock().canSustainPlant(worldIn, grow_at.down(), EnumFacing.UP, this)
    			
    		){
    			//worldIn.setBlockState(grow_at, new BlockState(this), 0);
    			worldIn.setBlockState(grow_at, this.getDefaultState());
    	} 

    }

}






















