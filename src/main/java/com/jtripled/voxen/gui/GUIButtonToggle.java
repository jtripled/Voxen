package com.jtripled.voxen.gui;

import com.jtripled.voxen.Voxen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author jtripled
 */
public abstract class GUIButtonToggle extends GUIButton
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Voxen.ID, "textures/gui/buttons.png");
    
    public String text;
    
    public GUIButtonToggle(GUIContainer gui, int x, int y)
    {
        this(gui, x, y, 20, 20);
    }
    
    public GUIButtonToggle(GUIContainer gui, int x, int y, int w, int h)
    {
        this(gui, x, y, w, h, null);
    }
    
    public GUIButtonToggle(GUIContainer gui, int x, int y, int w, int h, String text)
    {
        super(gui, x, y, w, h, "");
        this.text = text;
    }
    
    public abstract boolean getState();
    
    @Override
    public final String[] getTooltip()
    {
        return getTooltip(getState());
    }
    
    public String[] getTooltip(boolean state)
    {
        return null;
    }
    
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        super.drawButton(mc, mouseX, mouseY, partialTicks);
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            boolean state = getState();
            
            if (text != null)
            {
                int j = 14737632;
                if (packedFGColour != 0)
                {
                    j = packedFGColour;
                }
                else if (!this.enabled)
                {
                    j = 10526880;
                }
                else if (this.hovered)
                {
                    j = 16777120;
                }
                this.drawString(fontrenderer, this.text, this.x + 8, this.y + (this.height - 8) / 2, j);
                mc.getTextureManager().bindTexture(TEXTURE);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(this.x + this.width - 21, this.y + this.height - 18, 16 * (state ? 1 : 0), 0, 16, 16);
            }
            else
            {
                mc.getTextureManager().bindTexture(TEXTURE);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(this.x + this.width / 2 - 8, this.y + (this.height - 18), 16 * (state ? 1 : 0), 0, 16, 16);
            }
        }
    }
}
