package com.jtripled.voxen.gui;

import com.jtripled.voxen.Voxen;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author jtripled
 */
public abstract class GUIProgressIndicator extends GUIElementBase
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Voxen.ID, "textures/gui/widgets.png");
    
    private final Shape shape;
    
    public GUIProgressIndicator(GUIContainer gui, int x, int y)
    {
        this(gui, x, y, Shape.ARROW);
    }
    
    public GUIProgressIndicator(GUIContainer gui, int x, int y, Shape shape)
    {
        super(gui, x, y, shape.getWidth(), shape.getHeight());
        this.shape = shape;
    }
    
    public abstract float getProgress();

    @Override
    public void onDrawBackground(float ticks, int mouseX, int mouseY, int x, int y)
    {
        if (this.isVisible())
        {
            float progress = this.getProgress();
            this.getGUI().mc.getTextureManager().bindTexture(TEXTURE);
            if (null != this.shape)
            switch (this.shape)
            {
                case ARROW:
                    getGUI().drawTexturedModalRect(this.getX() + x, this.getY() + y, 0, 16, this.getWidth(), this.getHeight());
                    getGUI().drawTexturedModalRect(this.getX() + x, this.getY() + y, 0, 32, (int) Math.ceil(this.getWidth() * progress), this.getHeight());
                    break;
                case LIGHTNING:
                    break;
                case FIRE:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDrawForeground(int mouseX, int mouseY, int x, int y)
    {
        if (this.isVisible())
        {
            
        }
    }
    
    public static enum Shape
    {
        ARROW (22, 16),
        LIGHTNING (0, 0),
        FIRE (0, 0);
        
        private final int width;
        private final int height;
        
        private Shape(int width, int height)
        {
            this.width = width;
            this.height = height;
        }
        
        public final int getWidth()
        {
            return this.width;
        }
        
        public final int getHeight()
        {
            return this.height;
        }
    }
}
