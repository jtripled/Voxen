package com.jtripled.voxen.container;

import com.jtripled.voxen.tile.TileBase;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author jtripled
 */
public class ContainerTile<T extends TileBase> extends ContainerBase
{
    private final T tile;
    
    public ContainerTile(float rows, InventoryPlayer inventory, T tile)
    {
        super(rows, inventory);
        this.tile = tile;
    }
    
    public T getTile()
    {
        return tile;
    }
}
