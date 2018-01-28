package com.jtripled.voxen.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;

/**
 *
 * @author jtripled
 */
public interface IMobRegistration extends IEntityRegistration
{
    @Override
    public Class<? extends EntityLiving> getEntityClass();
    
    public default int getEggPrimary()
    {
        return -1;
    }

    public default int getEggSecondary()
    {
        return -1;
    }
    
    public default boolean canSpawn(Biome biome)
    {
        return false;
    }

    public default int getSpawnRate()
    {
        return 8;
    }

    public default int getSpawnMin()
    {
        return 2;
    }

    public default int getSpawnMax()
    {
        return 3;
    }
        
    public default EnumCreatureType getSpawnType()
    {
        return EnumCreatureType.CREATURE;
    }
}
