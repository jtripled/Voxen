package com.jtripled.voxen.gui;

/**
 *
 * @author jtripled
 */
public abstract class GUIInventorySlotBlacklist extends GUIInventorySlot
{
    public GUIInventorySlotBlacklist(GUIContainer gui, int x, int y)
    {
        super(gui, x, y);
    }
    
    @Override
    public void onDrawBackground(float ticks, int mouseX, int mouseY, int x, int y)
    {
        super.onDrawBackground(ticks, mouseX, mouseY, x, y);
        if (getBlacklistState())
            this.getGUI().drawTexturedModalRect(this.getX() + x + 1, this.getY() + y + 1, 19, 49, 16, 16);
    }

    @Override
    public void onDrawForeground(int mouseX, int mouseY, int x, int y)
    {
        super.onDrawForeground(mouseX, mouseY, x, y);
    }
    
    public abstract boolean getBlacklistState();
}
