package com.jtripled.voxen.gui;

/**
 *
 * @author jtripled
 */
public abstract class GUIElementBase implements GUIElement
{
    private final GUIContainer gui;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean visible;
    
    public GUIElementBase(GUIContainer gui, int x, int y, int width, int height)
    {
        this.gui = gui;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = true;
    }
    
    @Override
    public abstract void onDrawBackground(float ticks, int mouseX, int mouseY, int x, int y);

    @Override
    public abstract void onDrawForeground(int mouseX, int mouseY, int x, int y);

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
    
}
