package com.jtripled.voxen.gui;

import net.minecraft.client.Minecraft;

/**
 *
 * @author jtripled
 */
public abstract class GUIButtonHidden extends GUIButton
{
    public GUIButtonHidden(GUIContainer gui, int x, int y)
    {
        this(gui, x, y, 20, 20);
    }
    
    public GUIButtonHidden(GUIContainer gui, int x, int y, int w, int h)
    {
        super(gui, x, y, w, h, "");
        this.visible = false;
    }
    
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }
}
