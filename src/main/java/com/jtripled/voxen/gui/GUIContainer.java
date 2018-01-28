package com.jtripled.voxen.gui;

import com.jtripled.voxen.container.ContainerBase;
import com.jtripled.voxen.gui.GUIButton.GUIButtonAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final Map<GUIButton, GUIButtonAction> buttons;
    private final List<GUIElement> elements;
    private int currentButtonID;
    
    public GUIContainer(T container)
    {
        super(container);
        this.container = container;
        this.buttons = new HashMap<>();
        this.elements = new ArrayList<>();
        this.currentButtonID = 0;
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
        mc.getTextureManager().bindTexture(getTexture());
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        drawBackground(ticks, mouseX, mouseY, x, y);
        elements.forEach((GUIElement element) -> {
            element.drawBackground(ticks, mouseX, mouseY, x, y);
        });
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        fontRenderer.drawString("Inventory", 8, ySize - 93, 0x404040);
        fontRenderer.drawString(getName(), 8, 6, 0x404040);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(getTexture());
        drawForeground(mouseX, mouseY, x, y);
        elements.forEach((GUIElement element) -> {
            element.drawForeground(mouseX, mouseY, x, y);
        });
        buttons.keySet().forEach((GUIButton button) -> {
            if (button.getTooltip() != null && button.isMouseOver())
                drawHoveringText(button.getTooltip(), mouseX - x, mouseY - y);
        });
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
    
    public float getZLevel()
    {
        return zLevel;
    }
    
    public T getContainer()
    {
        return container;
    }
    
    public abstract ResourceLocation getTexture();
    
    public abstract String getName();
    
    public void addElement(GUIElement element)
    {
        elements.add(element);
    }
    
    public void addButton(GUIButton button, GUIButtonAction action)
    {
        if (!buttons.containsKey(button))
        {
            button.id = currentButtonID++;
            this.addButton(button);
            buttons.put(button, action);
        }
        else
        {
            
        }
    }
    
    @Override
    protected void actionPerformed(GuiButton button)
    {
        buttons.forEach((GUIButton b, GUIButtonAction a) -> {
            if (button.id == b.id)
            {
                a.run(b);
            }
        });
    }
}
