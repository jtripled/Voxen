package com.jtripled.voxen.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/**
 *
 * @author jtripled
 */
public interface IEntityRegistration
{
    public String getName();

    public ResourceLocation getResourceLocation();
    
    public Class<? extends Entity> getEntityClass();

    public IRenderFactory getRenderFactory();
    
    public default SoundEvent[] getSounds()
    {
        return null;
    }
}
