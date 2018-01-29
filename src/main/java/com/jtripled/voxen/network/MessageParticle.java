package com.jtripled.voxen.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 *
 * @author jtripled
 */
public class MessageParticle implements IMessage
{
    private BlockPos pos;
    private EnumParticleTypes particle;
    
    public MessageParticle()
    {
        
    }
    
    public MessageParticle(BlockPos pos, EnumParticleTypes particle)
    {
        this.pos = pos;
        this.particle = particle;
    }
    
    public BlockPos getPos()
    {
        return pos;
    }
    
    public EnumParticleTypes getParticle()
    {
        return particle;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        particle = EnumParticleTypes.getParticleFromId(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(particle.getParticleID());
    }
    
}
