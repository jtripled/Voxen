package com.jtripled.voxen.gui;

import java.util.Arrays;

/**
 *
 * @author jtripled
 */
public interface GUIElement
{
    public default void drawBackground(float ticks, int mouseX, int mouseY, int x, int y)
    {
        if (isVisible())
        {
            onDrawBackground(ticks, mouseX, mouseY, x, y);
        }
    }
    
    public default void drawForeground(int mouseX, int mouseY, int x, int y)
    {
        if (isVisible())
        {
            onDrawForeground(mouseX, mouseY, x, y);

            /* Draw tooltips. */
            if (isHovered(mouseX, mouseY))
            {
                String[] tooltip = getTooltip();
                if (tooltip != null)
                    getGUI().drawHoveringText(Arrays.asList(tooltip), mouseX - (getGUI().width - getGUI().getXSize()) / 2, mouseY - (getGUI().height - getGUI().getYSize()) / 2);
            }
        }
    }
    
    public void onDrawBackground(float ticks, int mouseX, int mouseY, int x, int y);
    
    public void onDrawForeground(int mouseX, int mouseY, int x, int y);
    
    public GUIContainer getGUI();
    
    public int getX();
    
    public int getY();
    
    public int getWidth();
    
    public int getHeight();
    
    public boolean isVisible();
    
    public void setVisible(boolean visibility);
    
    public default String[] getTooltip() { return null; }
    
    public default boolean isHovered(int mouseX, int mouseY)
    {
        return mouseX >= this.getGUI().getX() + getX()
            && mouseY >= this.getGUI().getY() + getY()
            && mouseX < this.getGUI().getX() + getX() + getWidth()
            && mouseY < this.getGUI().getY() + getY() + getHeight();
    }
}
