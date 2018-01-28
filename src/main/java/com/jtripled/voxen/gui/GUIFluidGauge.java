package com.jtripled.voxen.gui;

import com.jtripled.voxen.Voxen;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 *
 * @author jtripled
 */
public class GUIFluidGauge implements GUIElement
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Voxen.ID, "textures/gui/buttons.png");
    
    private static final int LENGTH = 145;
    private static final int HEIGHT = 8;
    
    private final GUIContainer gui;
    private final FluidTank tank;
    private final int x;
    private final int y;
    private boolean visibility;
    private boolean hovered;
    
    public GUIFluidGauge(GUIContainer gui, FluidTank tank, int x, int y)
    {
        this.gui = gui;
        this.tank = tank;
        this.x = x;
        this.y = y;
        this.visibility = true;
    }
    
    @Override
    public void drawBackground(float ticks, int mouseX, int mouseY, int x, int y)
    {
        if (visibility)
        {
            gui.mc.getTextureManager().bindTexture(TEXTURE);
            gui.drawTexturedModalRect(x + this.x, y + this.y, 0, 248, 145, 8);
            FluidStack fluid = tank.getFluid();
            if (fluid != null)
            {
                TextureAtlasSprite texture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill().toString());
                int fill = (int) Math.ceil(143 * (float) tank.getFluidAmount() / tank.getCapacity());
                int offset = 0;
                gui.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                while (fill > 0)
                {
                    int xCoord = x + 16 + offset;
                    int widthIn = fill >= 16 ? 16 : fill;
                    int yCoord = y + 23;
                    int heightIn = 6;
                    bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)gui.getZLevel()).tex((double)texture.getMinU(), (double)texture.getInterpolatedV(6)).endVertex();
                    bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)gui.getZLevel()).tex(fill >= 16 ? (double)texture.getMaxU() : (double)texture.getInterpolatedU(fill), (double)texture.getInterpolatedV(6)).endVertex();
                    bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)gui.getZLevel()).tex(fill >= 16 ? (double)texture.getMaxU() : (double)texture.getInterpolatedU(fill), (double)texture.getMinV()).endVertex();
                    bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)gui.getZLevel()).tex((double)texture.getMinU(), (double)texture.getMinV()).endVertex();
                    offset += 16;
                    fill -= 16;
                }
                tessellator.draw();
            }
            gui.mc.getTextureManager().bindTexture(TEXTURE);
            gui.drawTexturedModalRect(x + this.x + 2, y + this.y + 1, 0, 242, 141, 6);
        }
    }
    
    @Override
    public void drawForeground(int mouseX, int mouseY, int x, int y)
    {
        this.hovered = mouseX >= x + this.x
                    && mouseY >= y + this.y
                    && mouseX < x + this.x + LENGTH
                    && mouseY < y + this.y + HEIGHT;
        if (this.visibility && this.hovered)
        {
            List<String> tooltip = new ArrayList<>();
            FluidStack fluid = tank.getFluid();
            if (fluid == null)
            {
                TextComponentString message = new TextComponentString("Empty");
                message.getStyle().setColor(TextFormatting.RED);
                tooltip.add(message.getFormattedText());
                message = new TextComponentString("0/" + tank.getCapacity() + "mB");
                message.getStyle().setColor(TextFormatting.GRAY);
                tooltip.add(message.getFormattedText());
            }
            else
            {
                tooltip.add(fluid.getFluid().getLocalizedName(fluid));
                TextComponentString message = new TextComponentString(tank.getFluidAmount() + "/" + tank.getCapacity() + "mB");
                message.getStyle().setColor(TextFormatting.GRAY);
                tooltip.add(message.getFormattedText());
            }
            gui.drawHoveringText(tooltip, mouseX - (gui.width - gui.getXSize()) / 2, mouseY - (gui.height - gui.getYSize()) / 2);
        }
    }
    
    public void setVisible(boolean visibility)
    {
        this.visibility = visibility;
    }
    
    public static enum Direction
    {
        HORIZONTAL,
        VERTICAL
    }
}
