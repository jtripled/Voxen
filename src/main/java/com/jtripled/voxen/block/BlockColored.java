package com.jtripled.voxen.block;

import com.jtripled.voxen.util.Color;
import net.minecraft.block.material.Material;

/**
 *
 * @author jtripled
 */
public class BlockColored extends BlockBase
{
    public BlockColored(String name, Material material)
    {
        this(name, material, null);
    }
    
    public BlockColored(String name, Material material, Color color)
    {
        super(color == null ? name : color.getName() + "_" + name, material, color.getMapColor());
    }
}
