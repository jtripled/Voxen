package com.jtripled.voxen.gui;

import net.minecraft.client.Minecraft;

/**
 *
 * @author jtripled
 */
public class GUIButtonHidden extends GUIButton
{
    public GUIButtonHidden(int x, int y)
    {
        this(x, y, 20, 20);
    }
    
    public GUIButtonHidden(int x, int y, int w, int h)
    {
        super(x, y, w, h, "");
        this.visible = false;
    }
    
    @Override
    public final GUIButtonHidden setVisible(boolean visible)
    {
        return this;
    }
    
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }
}
