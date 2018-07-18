package com.jtripled.voxen.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 *
 * @author jtripled
 */
public class MessageParticle implements IMessage
{
    private double x;
    private double y;
    private double z;
    private EnumParticleTypes particle;
    
    public MessageParticle()
    {
        
    }
    
    public MessageParticle(double x, double y, double z, EnumParticleTypes particle)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.particle = particle;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public double getZ()
    {
        return z;
    }
    
    public EnumParticleTypes getParticle()
    {
        return particle;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        particle = EnumParticleTypes.getParticleFromId(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeInt(particle.getParticleID());
    }
    
}
