package com.jtripled.voxen.item;

import com.jtripled.voxen.block.BlockDoor;
import net.minecraft.item.ItemStack;

/**
 *
 * @author jtripled
 */
public class ItemDoor extends net.minecraft.item.ItemDoor implements IItemBase
{
    private final BlockDoor block;
    private boolean registered;

    public ItemDoor(BlockDoor block)
    {
        super(block);
        this.block = block;
        this.registered = false;
    }

    @Override
    public String getName()
    {
        return this.getUnlocalizedName();
    }

    @Override
    public boolean isRegistered()
    {
        return this.registered;
    }

    @Override
    public void setRegistered()
    {
        this.registered = true;
    }

    @Override
    public String getUnlocalizedName()
    {
        return block.getUnlocalizedName();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return block.getUnlocalizedName();
    }
}
