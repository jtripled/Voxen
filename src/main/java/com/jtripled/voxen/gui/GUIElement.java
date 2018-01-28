package com.jtripled.voxen.gui;

/**
 *
 * @author jtripled
 */
public interface GUIElement
{
    public void drawBackground(float ticks, int mouseX, int mouseY, int x, int y);
    
    public void drawForeground(int mouseX, int mouseY, int x, int y);
}
