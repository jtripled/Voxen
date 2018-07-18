package com.jtripled.voxen.tile;

import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author jtripled
 */
public class TileNameable extends TileBase implements ITileNameable
{
    private String customName;
    
    @Override
    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    @Override
    public String getCustomName()
    {
        return this.customName;
    }

    @Override
    public boolean hasCustomName()
    {
        return this.customName != null;
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        if (this.hasCustomName())
        {
            compound.setString("customName", this.customName);
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("customName", 8))
        {
            this.customName = compound.getString("customName");
        }
        super.readFromNBT(compound);
    }
}
