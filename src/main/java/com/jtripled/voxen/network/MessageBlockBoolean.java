package com.jtripled.voxen.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 *
 * @author jtripled
 */
public class MessageBlockBoolean implements IMessage
{
    private BlockPos position;
    private boolean value;
    
    public MessageBlockBoolean()
    {
        
    }
    
    public MessageBlockBoolean(BlockPos position, boolean value)
    {
        this.position = position;
        this.value = value;
    }

    public BlockPos getPosition()
    {
        return position;
    }

    public boolean getValue()
    {
        return value;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        position = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        value = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(position.getX());
        buf.writeInt(position.getY());
        buf.writeInt(position.getZ());
        buf.writeBoolean(value);
    }
}
