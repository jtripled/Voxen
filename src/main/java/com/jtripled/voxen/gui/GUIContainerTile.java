package com.jtripled.voxen.gui;

import com.jtripled.voxen.Voxen;
import com.jtripled.voxen.container.ContainerTile;

/**
 *
 * @author jtripled
 */
public abstract class GUIContainerTile<T extends ContainerTile> extends GUIContainer<T>
{
    public GUIContainerTile(T container)
    {
        super(container);
    }

    @Override
    public String getName()
    {
        return Voxen.PROXY.localize(getContainer().getTile().getBlockType().getUnlocalizedName() + ".name");
    }
}
