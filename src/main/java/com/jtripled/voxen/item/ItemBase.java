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
    
    public ItemBase(String name)
    {
        this.registered = false;
    }
    
    @Override
    public final String getName()
    {
        return name;
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
