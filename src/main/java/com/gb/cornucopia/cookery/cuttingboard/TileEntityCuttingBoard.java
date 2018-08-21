package com.gb.cornucopia.cookery.cuttingboard;

import com.gb.cornucopia.cookery.Cookery;
import com.gb.cornucopia.cuisine.dish.Dish;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityCuttingBoard extends TileEntity {

    private boolean changed_by_board = false;

    public ItemStackHandler inventory = new ItemStackHandler(10){
        //slot 0 is output, slots 1-9 looks like craft grid
        @Override
        public void onContentsChanged(int slot){

            //if no output was just added:
            if (!changed_by_board ){

                //if output slot changed, means output just taken:
                if (slot == 0){
                    _consumeIngredients();
                    markDirty();
                }
                //System.out.println("what is cooking? " + _whatsCooking());

                //if something can be made and there is space in the output slot
                if (_whatsCooking() != null && inventory.getStackInSlot(0).isEmpty()) {
                    //System.out.println("There is space for an output.");
                    ItemStack output = _whatsCooking().getItem();
                    changed_by_board = true;
                    inventory.setStackInSlot(0, output);
                    markDirty();
                }

                //if inputs changed and it no longer makes a recipe
                if (slot > 0 && _whatsCooking() == null && !inventory.getStackInSlot(0).isEmpty()){
                    //System.out.println("my inputs changed and i shouldn't have an output, so i am making it empty.");
                    changed_by_board = true;
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                    markDirty();
                }
            }
            //if output just added or ingredients taken by the cutting board, skip the above logic of checking stuff
            else {
                changed_by_board = false;
            }
            markDirty();
            }
    };

    public boolean hasWater() {
        return this.hasWorld() && (
                this.world.getBlockState(this.pos.add(1, 0, 0).down()).getBlock() == Cookery.water_basin
                        || this.world.getBlockState(this.pos.add(-1, 0, 0).down()).getBlock() == Cookery.water_basin
                        || this.world.getBlockState(this.pos.add(0, 0, 1).down()).getBlock() == Cookery.water_basin
                        || this.world.getBlockState(this.pos.add(0, 0, -1).down()).getBlock() == Cookery.water_basin
        );
    }

    private void _consumeIngredients() {
        for (int i = 1; i <= 9; i++) {
            inventory.getStackInSlot(i).shrink(1);
        }
    }
    private Dish _whatsCooking() {
        // ok what is getting cooked:
        return Dish.cutting_board.findMatchingDish(inventory, 1, 9, false, this.hasWater());
    }


    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }

}
