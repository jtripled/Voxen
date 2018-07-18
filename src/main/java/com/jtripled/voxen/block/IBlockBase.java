package com.jtripled.voxen.block;

import com.jtripled.voxen.item.IItemBase;
import com.jtripled.voxen.mod.ModBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;

/**
 *
 * @author jtripled
 */
public interface IBlockBase
{
    public ModBase getOwner();
    
    public String getName();
    
    public boolean isRegistered();
    
    public void setRegistered(ModBase owner);

    public IItemBase getItem();
    
    public boolean hasItem();
    
    public IProperty[] getIgnoredProperties();
    
    public boolean hasIgnoredProperties();
    
    public Class<? extends TileEntity> getTileClass();
    
    public boolean hasTile();

    public Class<? extends TileEntitySpecialRenderer> getTESRClass();
    
    public boolean hasTESR();
    
    public TileEntitySpecialRenderer createTESR();
    
    public boolean hasUniqueInventoryModel();
    
    public IRecipe getRecipe();
    
    public boolean hasRecipe();
}
