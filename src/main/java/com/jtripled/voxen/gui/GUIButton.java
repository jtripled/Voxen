package com.jtripled.voxen.gui;

import net.minecraft.client.gui.GuiButton;

/**
 *
 * @author jtripled
 */
public class GUIButton extends GuiButton
{
    public GUIButton(int x, int y)
    {
        this(x, y, 20, 20, "");
    }
    
    public GUIButton(int x, int y, String text)
    {
        this(x, y, 20, 20, text);
    }
    
    public GUIButton(int x, int y, int w, int h)
    {
        this(x, y, w, h, "");
    }
    
    public GUIButton(int x, int y, int w, int h, String text)
    {
        super(-1, x, y, w, h, text);
    }
    
    public GUIButton setVisible(boolean visible)
    {
        this.visible = visible;
        return this;
    }
    
    public String getTooltip()
    {
        return null;
    }
    
    public static interface GUIButtonAction
    {
        public void run(GUIButton button);
    }
}
