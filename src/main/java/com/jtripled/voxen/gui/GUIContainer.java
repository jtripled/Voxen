package com.jtripled.voxen.gui;

import com.jtripled.voxen.Voxen;
import com.jtripled.voxen.container.ContainerBase;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author jtripled
 */
public abstract class GUIContainer<T extends ContainerBase> extends GuiContainer
{
    protected final T container;
    private final List<GUIElement> elements;
    private int currentButtonID;
    private GUIContainer.Type type;
    
    public GUIContainer(T container)
    {
        super(container);
        this.container = container;
        this.elements = new ArrayList<>();
        this.currentButtonID = 0;
        this.type = null;
    }
    
    @Override
    public void initGui()
    {
        super.initGui();
        this.addElements(this.guiLeft, this.guiTop);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX, int mouseY)
    {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawDefaultBackground();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        
        /* Render GUI background. */
        if (type != null)
        {
            mc.getTextureManager().bindTexture(type.getTexture());
            drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        }
        else if (getTexture() != null)
        {
            mc.getTextureManager().bindTexture(getTexture());
            drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        }
        
        /* Custom rendering per GUI. */
        if (getTexture() != null)
            mc.getTextureManager().bindTexture(getTexture());
        drawBackground(ticks, mouseX, mouseY, x, y);
        
        /* Draw all elements. */
        elements.forEach((GUIElement element) -> {
            element.drawBackground(ticks, mouseX, mouseY, x, y);
        });
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        
        /* Draw titles. */
        fontRenderer.drawString("Inventory", 8, ySize - 93, 0x404040);
        fontRenderer.drawString(getName(), 8, 6, 0x404040);
        
        /* Custom rendering per GUI. */
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        if (getTexture() != null)
            mc.getTextureManager().bindTexture(getTexture());
        drawForeground(mouseX, mouseY, x, y);
        
        /* Draw all elements. */
        elements.forEach((GUIElement element) -> {
            element.drawForeground(mouseX, mouseY, x, y);
        });
        /*buttons.keySet().forEach((GUIButton button) -> {
            if (button.getTooltip() != null && button.isMouseOver())
                drawHoveringText(button.getTooltip(), mouseX - x, mouseY - y);
        });*/
    }
    
    public void addElements(int x, int y)
    {
        
    }
    
    public void drawBackground(float ticks, int mouseX, int mouseY, int x, int y)
    {
        
    }
    
    public void drawForeground(int mouseX, int mouseY, int x, int y)
    {
        
    }
    
    public int getX()
    {
        return this.guiLeft;
    }
    
    public int getY()
    {
        return this.guiTop;
    }
    
    public float getZLevel()
    {
        return zLevel;
    }
    
    public T getContainer()
    {
        return container;
    }
    
    public void setType(GUIContainer.Type type)
    {
        this.ySize = type.getHeight();
        this.type = type;
    }
    
    public ResourceLocation getTexture()
    {
        return null;
    }
    
    public abstract String getName();
    
    public void addElement(GUIElement element)
    {
        if (element instanceof GUIButton)
        {
            GUIButton button = (GUIButton) element;
            button.id = currentButtonID++;
            this.addButton(button);
        }
        elements.add(element);
    }
    
    @Override
    protected void actionPerformed(GuiButton button)
    {
        elements.forEach((GUIElement element) -> {
            if (element instanceof GUIButton && button.id == ((GUIButton) element).id)
            {
                ((GUIButton) element).onClick();
            }
        });
    }
    
    public static enum Type
    {
        INVENTORY_1   (132, new ResourceLocation(Voxen.ID, "textures/gui/inventory_1.png")),
        INVENTORY_1_5 (141, new ResourceLocation(Voxen.ID, "textures/gui/inventory_1_5.png")),
        INVENTORY_2   (150, new ResourceLocation(Voxen.ID, "textures/gui/inventory_2.png")),
        INVENTORY_2_5 (159, new ResourceLocation(Voxen.ID, "textures/gui/inventory_2_5.png"));
        
        private final int height;
        private final ResourceLocation texture;
        
        private Type(int height, ResourceLocation texture)
        {
            this.height = height;
            this.texture = texture;
        }
        
        public int getHeight()
        {
            return this.height;
        }
        
        public ResourceLocation getTexture()
        {
            return this.texture;
        }
    }
}
