package com.jtripled.voxen.util;

import net.minecraft.block.material.MapColor;
import net.minecraft.item.EnumDyeColor;

/**
 *
 * @author jtripled
 */
public enum Color
{
    BLACK("black", EnumDyeColor.BLACK),
    BLUE("blue", EnumDyeColor.BLUE),
    BROWN("brown", EnumDyeColor.BROWN),
    CYAN("cyan", EnumDyeColor.CYAN),
    GRAY("gray", EnumDyeColor.GRAY),
    GREEN("green", EnumDyeColor.GREEN),
    LIGHT_BLUE("light_blue", EnumDyeColor.LIGHT_BLUE),
    LIME("lime", EnumDyeColor.LIME),
    MAGENTA("magenta", EnumDyeColor.MAGENTA),
    ORANGE("orange", EnumDyeColor.ORANGE),
    PINK("pink", EnumDyeColor.PINK),
    PURPLE("purple", EnumDyeColor.PURPLE),
    RED("red", EnumDyeColor.RED),
    SILVER("silver", EnumDyeColor.SILVER),
    WHITE("white", EnumDyeColor.WHITE),
    YELLOW("yellow", EnumDyeColor.YELLOW);
    
    private final String name;
    private final EnumDyeColor dyeColor;
    private final MapColor mapColor;
    
    private Color(String name, EnumDyeColor dyeColor)
    {
        this.name = name;
        this.dyeColor = dyeColor;
        this.mapColor = MapColor.getBlockColor(this.dyeColor);
    }
    
    public final String getName()
    {
        return this.name;
    }
    
    public final EnumDyeColor getDyeColor()
    {
        return this.dyeColor;
    }
    
    public final MapColor getMapColor()
    {
        return this.mapColor;
    }
}
