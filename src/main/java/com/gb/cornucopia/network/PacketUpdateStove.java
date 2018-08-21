package com.gb.cornucopia.network;

import com.gb.cornucopia.cookery.stove.TileEntityStove;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateStove implements IMessage {
    private BlockPos pos;
    private int burn_time;
    private int initial_burn_time;
    private int cook_time;
    private int cook_time_goal;
    private ItemStack stack0;
    private ItemStack stack1;
    private ItemStack stack2;
    private ItemStack stack3;
    private ItemStack stack4;
    private ItemStack stack5;
    private ItemStack stack6;
    private ItemStack stack7;


    public PacketUpdateStove(BlockPos pos, int burn_time, int initial_burn_time, int cook_time, int cook_time_goal, ItemStack stack0, ItemStack stack1, ItemStack stack2, ItemStack stack3, ItemStack stack4, ItemStack stack5, ItemStack stack6, ItemStack stack7) {
        this.pos = pos;
        this.burn_time = burn_time;
        this.initial_burn_time = initial_burn_time;
        this.cook_time = cook_time;
        this.cook_time_goal = cook_time_goal;
        this.stack0 = stack0;
        this.stack1 = stack1;
        this.stack2 = stack2;
        this.stack3 = stack3;
        this.stack4 = stack4;
        this.stack5 = stack5;
        this.stack6 = stack6;
        this.stack7 = stack7;
    }

    public PacketUpdateStove(TileEntityStove te) {
        this(te.getPos(), te.getField(0), te.getField(1), te.getField(2), te.getField(3), te.inventory.getStackInSlot(0), te.inventory.getStackInSlot(1), te.inventory.getStackInSlot(2),
                te.inventory.getStackInSlot(3), te.inventory.getStackInSlot(4), te.inventory.getStackInSlot(5), te.inventory.getStackInSlot(6), te.inventory.getStackInSlot(7));


    }

    public PacketUpdateStove() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(burn_time);
        buf.writeInt(initial_burn_time);
        buf.writeInt(cook_time);
        buf.writeInt(cook_time_goal);
        ByteBufUtils.writeItemStack(buf, stack0);
        ByteBufUtils.writeItemStack(buf, stack1);
        ByteBufUtils.writeItemStack(buf, stack2);
        ByteBufUtils.writeItemStack(buf, stack3);
        ByteBufUtils.writeItemStack(buf, stack4);
        ByteBufUtils.writeItemStack(buf, stack5);
        ByteBufUtils.writeItemStack(buf, stack6);
        ByteBufUtils.writeItemStack(buf, stack7);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        burn_time = buf.readInt();
        initial_burn_time = buf.readInt();
        cook_time = buf.readInt();
        cook_time_goal = buf.readInt();
        stack0 = ByteBufUtils.readItemStack(buf);
        stack1 = ByteBufUtils.readItemStack(buf);
        stack2 = ByteBufUtils.readItemStack(buf);
        stack3 = ByteBufUtils.readItemStack(buf);
        stack4 = ByteBufUtils.readItemStack(buf);
        stack5 = ByteBufUtils.readItemStack(buf);
        stack6 = ByteBufUtils.readItemStack(buf);
        stack7 = ByteBufUtils.readItemStack(buf);
    }

    public static class Handler implements IMessageHandler<PacketUpdateStove, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateStove message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityStove te = (TileEntityStove) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                te.setField(0, message.burn_time);
                te.setField(1, message.initial_burn_time);
                te.setField(2, message.cook_time);
                te.setField(3, message.cook_time_goal);
                te.inventory.setStackInSlot(0, message.stack0);
                te.inventory.setStackInSlot(1, message.stack1);
                te.inventory.setStackInSlot(2, message.stack2);
                te.inventory.setStackInSlot(3, message.stack3);
                te.inventory.setStackInSlot(4, message.stack4);
                te.inventory.setStackInSlot(5, message.stack5);
                te.inventory.setStackInSlot(6, message.stack6);
                te.inventory.setStackInSlot(7, message.stack7);
            });
            return null;
        }

    }
}
