package com.jtripled.voxen;

import com.jtripled.voxen.network.*;
import com.jtripled.voxen.proxy.Proxy;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Voxen.ID, name = Voxen.NAME, version = Voxen.VERSION)
public class Voxen
{
    @Mod.Instance(Voxen.ID)
    public static Voxen INSTANCE;
    
    public static final String ID = "voxenforge";
    public static final String NAME = "VoxenForge";
    public static final String VERSION = "1.0";
    
    @SidedProxy(serverSide = "com.jtripled.voxen.proxy.ProxyServer", clientSide = "com.jtripled.voxen.proxy.ProxyClient")
    public static Proxy PROXY;
    
    public static final Network NETWORK = new Network(ID);
    
    public Network getNetwork()
    {
        return NETWORK;
    }
    
    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    {
        
    }
    
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event)
    {
        NETWORK.registerMessage(MessageHandlerFluidCapacity.class, MessageFluidCapacity.class, Side.CLIENT);
        NETWORK.registerMessage(MessageHandlerFluidContent.class, MessageFluidContent.class, Side.CLIENT);
    }
    
    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event)
    {
        
    }
}
