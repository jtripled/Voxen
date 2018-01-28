package com.jtripled.voxen.mod;

import com.jtripled.voxen.Voxen;
import com.jtripled.voxen.gui.GUIHolder;
import com.jtripled.voxen.network.Network;
import com.jtripled.voxen.proxy.Proxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author jtripled
 */
public abstract class ModBase
{
    private Logger logger;
    private RegistrationHandler registrationHandler;
    
    public final void preInit(FMLPreInitializationEvent event)
    {
        this.logger = event.getModLog();
        if (hasRegistry())
        {
            this.registrationHandler = new RegistrationHandler(this);
            MinecraftForge.EVENT_BUS.register(registrationHandler);
            registrationHandler.onPreInit(event);
        }
    }
    
    public final void init(FMLInitializationEvent event)
    {
        if (hasRegistry())
        {
            registrationHandler.onInit(event);
            NetworkRegistry.INSTANCE.registerGuiHandler(this, registrationHandler);
        }
    }
    
    public final void postInit(FMLPostInitializationEvent event)
    {
        if (hasRegistry())
        {
            registrationHandler.onPostInit(event);
        }
    }
    
    public boolean isDebug()
    {
        return false;
    }
    
    public abstract String getID();
    
    public abstract String getName();
    
    public abstract String getVersion();
    
    public final boolean hasRegistry()
    {
        return getRegistry() != null;
    }
    
    public Registry getRegistry()
    {
        return null;
    }
    
    public Network getNetwork()
    {
        return Voxen.NETWORK;
    }
    
    public Proxy getProxy()
    {
        return Voxen.PROXY;
    }
    
    public Logger getLogger()
    {
        return logger;
    }
    
    public final int getGUIID(GUIHolder holder)
    {
        for (Integer i : registrationHandler.guis.keySet())
            if (registrationHandler.guis.get(i) == holder)
                return i;
        return -1;
    }
}
