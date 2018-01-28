package com.jtripled.voxen.item;

import com.jtripled.voxen.block.IBlockBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 *
 * @author jtripled
 */
public class ItemBlockBase extends ItemBlock implements IItemBase
{
    private final String name;
    private boolean registered;
    
    public ItemBlockBase(IBlockBase block)
    {
        super((Block) block);
        this.name = block.getName();
        this.registered = false;
        this.setUnlocalizedName(((Block) block).getUnlocalizedName());
        this.setRegistryName(((Block) block).getRegistryName());
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
