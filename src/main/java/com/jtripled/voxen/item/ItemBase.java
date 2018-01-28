package com.jtripled.voxen.item;

import net.minecraft.item.Item;

/**
 *
 * @author jtripled
 */
public class ItemBase extends Item implements IItemBase
{
    private String name;
    private boolean registered;
    
    public ItemBase()
    {
        this.registered = false;
    }
    
    @Override
    public final String getName()
    {
        return name;
    }
    
    public final ItemBase setName(String modID, String name)
    {
        if (!isRegistered())
        {
            this.name = name;
            this.setUnlocalizedName(name);
            this.setRegistryName(modID, name);
        }
        return this;
    }
    
    @Override
    public final boolean isRegistered()
    {
        return registered;
    }

    @Override
    public final void setRegistered()
    {
        this.registered = true;
    }
}
