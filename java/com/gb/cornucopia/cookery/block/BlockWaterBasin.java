package com.gb.cornucopia.cookery.block;

import com.gb.cornucopia.CornuCopia;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockWaterBasin extends Block{
	public final String name;
    //public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
	public BlockWaterBasin(){
		super(Material.rock);
		this.name = "cookery_water_basin";
		this.setCreativeTab(CornuCopia.tabCookeryBlock);
		this.setUnlocalizedName(this.name);
		//this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(0.5F);
		
		GameRegistry.registerBlock(this, this.name);
		
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		
		if (world.isRemote || player.inventory.getCurrentItem() == null) { return true; }

		ItemStack filled_container = null;
		Item held_item = player.inventory.getCurrentItem().getItem();
		
		if (held_item == Items.glass_bottle)
		{
			filled_container = new ItemStack(Items.potionitem, 1, 0);
		}

		else if (held_item == Items.bucket)
		{
			filled_container = new ItemStack(Items.water_bucket, 1, 0);

		}
		else
		{
			return true;
		}

		if (!player.inventory.addItemStackToInventory(filled_container))
		{
			//world.spawnEntityInWorld(new EntityItem(world, , water_bucket));
			this.spawnAsEntity(world, pos, filled_container);

		}
		else if (player instanceof EntityPlayerMP)
		{
			((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
		}

		// ....wat
		if (--(player.inventory.getCurrentItem().stackSize) <= 0)
		{
			player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
		}

		return true;
	}
	
    /*@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
            return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return getDefaultState().withProperty(FACING, enumfacing);
    }
    @Override
    public int getMetaFromState(IBlockState state) 
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING});
    }*/
}
