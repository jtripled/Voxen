package com.jtripled.voxen.container;

import com.jtripled.voxen.tile.TileBase;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 *
 * @author jtripled
 */
public class ContainerTileItemSlot<T extends TileBase> extends SlotItemHandler
{
    private final T tile;
    
    public ContainerTileItemSlot(T tile, IItemHandler handler, int index, int x, int y)
    {
        super(handler, index, x, y);
        this.tile = tile;
    }
    
    public T getTile()
    {
        return tile;
    }
    
    @Override
    public void onSlotChanged()
    {
        tile.markDirty();
    }
}
