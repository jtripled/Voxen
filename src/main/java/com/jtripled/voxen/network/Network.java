package com.jtripled.voxen.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 * @author jtripled
 */
public class Network
{
    private final SimpleNetworkWrapper wrapper;
    private int currentID;
    
    public Network(String modID)
    {
        this.wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(modID);
        this.currentID = 0;
    }
    
    public final <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> message, Side side)
    {
        wrapper.registerMessage(handler, message, currentID++, side);
    }
    
    public final <REQ extends IMessage, REPLY extends IMessage> void registerMessage(IMessageHandler<REQ, REPLY> handler, Class<REQ> message, Side side)
    {
        wrapper.registerMessage(handler, message, currentID++, side);
    }
    
    public Packet<?> getPacketFrom(IMessage message)
    {
        return wrapper.getPacketFrom(message);
    }
    
    public void sendTo(IMessage message, EntityPlayerMP player)
    {
        wrapper.sendTo(message, player);
    }
    
    public void sendToDimension(IMessage message, int dimension)
    {
        wrapper.sendToDimension(message, dimension);
    }
    
    public void sendToAll(IMessage message)
    {
        wrapper.sendToAll(message);
    }
    
    public void sendToAllAround(IMessage message, TargetPoint location)
    {
        wrapper.sendToAllAround(message, location);
    }
    
    public void sendToServer(IMessage message)
    {
        wrapper.sendToServer(message);
    }
}
