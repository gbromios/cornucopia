package com.gb.cornucopia.network;

import com.gb.cornucopia.cookery.stove.TileEntityStove;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateStove implements IMessage {

    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateStove(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateStove(TileEntityStove te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateStove() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(dimension);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        dimension = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketRequestUpdateStove, PacketUpdateStove> {

        @Override
        public PacketUpdateStove onMessage(PacketRequestUpdateStove message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntityStove te = (TileEntityStove)world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateStove(te);
            } else {
                return null;
            }
        }

    }
}
