package com.jtripled.voxen.gui;

import net.minecraft.client.gui.GuiButton;

/**
 *
 * @author jtripled
 */
public abstract class GUIButton extends GuiButton implements GUIElement
{
    private final GUIContainer gui;
    
    public GUIButton(GUIContainer gui, int x, int y)
    {
        this(gui, x, y, 20, 20, "");
    }
    
    public GUIButton(GUIContainer gui, int x, int y, String text)
    {
        this(gui, x, y, 20, 20, text);
    }
    
    public GUIButton(GUIContainer gui, int x, int y, int w, int h)
    {
        this(gui, x, y, w, h, "");
    }
    
    public GUIButton(GUIContainer gui, int x, int y, int w, int h, String text)
    {
        super(-1, x, y, w, h, text);
        this.gui = gui;
    }
    
    @Override
    public String[] getTooltip()
    {
        return null;
    }
    
    public abstract void onClick();

    @Override
    public void onDrawBackground(float ticks, int mouseX, int mouseY, int x, int y)
    {
        
    }

    @Override
    public void onDrawForeground(int mouseX, int mouseY, int x, int y)
    {
        
    }

    @Override
    public final GUIContainer getGUI()
    {
        return gui;
    }

    @Override
    public final int getX()
    {
        return x;
    }

    @Override
    public final int getY()
    {
        return y;
    }

    @Override
    public final int getWidth()
    {
        return width;
    }

    @Override
    public final int getHeight()
    {
        return height;
    }

    @Override
    public final boolean isVisible()
    {
        return visible;
    }
    
    @Override
    public final void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    @Override
    public final boolean isHovered(int mouseX, int mouseY)
    {
        return this.hovered;
    }
}
