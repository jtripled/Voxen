package com.jtripled.voxen.gui;

import com.jtripled.voxen.block.BlockBase;
import com.jtripled.voxen.mod.ModBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 *
 * @author jtripled
 */
public interface GUIHolder
{
    public Object getServerGUI(EntityPlayer player, World world, BlockPos pos);
    
    public Object getClientGUI(EntityPlayer player, World world, BlockPos pos);
    
    public default ModBase getGUIOwner()
    {
        if (this instanceof BlockBase)
        {
            return ((BlockBase) this).getMod();
        }
        return null;
    }
    
    public default int getGUIID()
    {
        return getGUIOwner().getGUIID(this);
    }

    public default Object getServerGUI(EntityPlayer player, World world, int x, int y, int z)
    {
        return getServerGUI(player, world, new BlockPos(x, y, z));
    }

    public default Object getClientGUI(EntityPlayer player, World world, int x, int y, int z)
    {
        return getClientGUI(player, world, new BlockPos(x, y, z));
    }

    public default void openGUI(EntityPlayer player, World world, int x, int y, int z)
    {
        player.openGui(getGUIOwner(), getGUIID(), world, x, y, z);
    }

    public default void openGUI(EntityPlayer player, World world, BlockPos pos)
    {
        openGUI(player, world, pos.getX(), pos.getY(), pos.getZ());
    }
}
