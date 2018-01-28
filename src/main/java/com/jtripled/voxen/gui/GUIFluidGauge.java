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
public class GUIFluidGauge extends GUIElementBase
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Voxen.ID, "textures/gui/widgets.png");
    
    private final FluidTank tank;
    
    public GUIFluidGauge(GUIContainer gui, FluidTank tank, int x, int y)
    {
        super(gui, x, y, 145, 8);
        this.tank = tank;
    }
    
    @Override
    public String[] getTooltip()
    {
        String[] tooltip = new String[2];
        FluidStack fluid = this.tank.getFluid();
        if (fluid == null)
        {
            TextComponentString message = new TextComponentString("Empty");
            message.getStyle().setColor(TextFormatting.RED);
            tooltip[0] = (message.getFormattedText());
            message = new TextComponentString("0/" + this.tank.getCapacity() + "mB");
            message.getStyle().setColor(TextFormatting.GRAY);
            tooltip[1] = (message.getFormattedText());
        }
        else
        {
            tooltip[0] = (fluid.getFluid().getLocalizedName(fluid));
            TextComponentString message = new TextComponentString(this.tank.getFluidAmount() + "/" + this.tank.getCapacity() + "mB");
            message.getStyle().setColor(TextFormatting.GRAY);
            tooltip[1] = (message.getFormattedText());
        }
        return tooltip;
    }
    
    @Override
    public void onDrawBackground(float ticks, int mouseX, int mouseY, int x, int y)
    {
        this.getGUI().mc.getTextureManager().bindTexture(TEXTURE);
        this.getGUI().drawTexturedModalRect(x + this.getX(), y + this.getY(), 0, 248, 145, 8);
        FluidStack fluid = tank.getFluid();
        if (fluid != null)
        {
            TextureAtlasSprite texture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill().toString());
            int fill = (int) Math.ceil(143 * (float) tank.getFluidAmount() / tank.getCapacity());
            int offset = 0;
            this.getGUI().mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            while (fill > 0)
            {
                int xCoord = x + 16 + offset;
                int widthIn = fill >= 16 ? 16 : fill;
                int yCoord = y + 23;
                int heightIn = 6;
                bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)this.getGUI().getZLevel()).tex((double)texture.getMinU(), (double)texture.getInterpolatedV(6)).endVertex();
                bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)this.getGUI().getZLevel()).tex(fill >= 16 ? (double)texture.getMaxU() : (double)texture.getInterpolatedU(fill), (double)texture.getInterpolatedV(6)).endVertex();
                bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)this.getGUI().getZLevel()).tex(fill >= 16 ? (double)texture.getMaxU() : (double)texture.getInterpolatedU(fill), (double)texture.getMinV()).endVertex();
                bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)this.getGUI().getZLevel()).tex((double)texture.getMinU(), (double)texture.getMinV()).endVertex();
                offset += 16;
                fill -= 16;
            }
            tessellator.draw();
        }
        this.getGUI().mc.getTextureManager().bindTexture(TEXTURE);
        this.getGUI().drawTexturedModalRect(x + this.getX() + 2, y + this.getY() + 1, 0, 242, 141, 6);
    }

    @Override
    public void onDrawForeground(int mouseX, int mouseY, int x, int y)
    {
        
    }
    
    public static enum Direction
    {
        HORIZONTAL,
        VERTICAL
    }
}
