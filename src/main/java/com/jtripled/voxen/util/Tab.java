package com.jtripled.voxen.util;

import net.minecraft.creativetab.CreativeTabs;

/**
 *
 * @author jtripled
 */
public class Tab
{
    public static final Tab BREWING = new Tab(CreativeTabs.BREWING);
    public static final Tab BUILDING = new Tab(CreativeTabs.BUILDING_BLOCKS);
    public static final Tab COMBAT = new Tab(CreativeTabs.COMBAT);
    public static final Tab DECORATION = new Tab(CreativeTabs.DECORATIONS);
    public static final Tab FOOD = new Tab(CreativeTabs.FOOD);
    public static final Tab MATERIAL = new Tab(CreativeTabs.MATERIALS);
    public static final Tab MISC = new Tab(CreativeTabs.MISC);
    public static final Tab REDSTONE = new Tab(CreativeTabs.REDSTONE);
    public static final Tab TOOL = new Tab(CreativeTabs.TOOLS);
    public static final Tab TRANSPORTATION = new Tab(CreativeTabs.TRANSPORTATION);
    
    private final CreativeTabs creativeTab;
    
    public Tab(CreativeTabs creativeTab)
    {
        this.creativeTab = creativeTab;
    }
    
    public final CreativeTabs getCreativeTab()
    {
        return creativeTab;
    }
}
