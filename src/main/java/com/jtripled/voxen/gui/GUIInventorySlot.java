package com.jtripled.voxen.gui;

import com.jtripled.voxen.Voxen;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author jtripled
 */
public class GUIInventorySlot extends GUIElementBase
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Voxen.ID, "textures/gui/widgets.png");
    
    public GUIInventorySlot(GUIContainer gui, int x, int y)
    {
        super(gui, x, y, 18, 18);
    }
    
    @Override
    public void onDrawBackground(float ticks, int mouseX, int mouseY, int x, int y)
    {
        this.getGUI().mc.getTextureManager().bindTexture(TEXTURE);
        this.getGUI().drawTexturedModalRect(this.getX() + x, this.getY() + y, 0, 48, 18, 18);
    }

    @Override
    public void onDrawForeground(int mouseX, int mouseY, int x, int y)
    {
        
    }
}
