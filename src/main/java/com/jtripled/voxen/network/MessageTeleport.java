package com.jtripled.voxen.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 *
 * @author jtripled
 */
public class MessageTeleport implements IMessage
{
    private BlockPos from;
    private BlockPos to;

    public MessageTeleport()
    {

    }

    public MessageTeleport(BlockPos from, BlockPos to)
    {
        this.from = from;
        this.to = to;
    }

    public BlockPos getFrom()
    {
        return from;
    }

    public BlockPos getTo()
    {
        return to;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        from = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        to = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(from.getX());
        buf.writeInt(from.getY());
        buf.writeInt(from.getZ());
        buf.writeInt(to.getX());
        buf.writeInt(to.getY());
        buf.writeInt(to.getZ());
    }
}
