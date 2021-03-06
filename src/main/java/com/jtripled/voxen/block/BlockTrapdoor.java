package com.jtripled.voxen.block;

import com.jtripled.voxen.item.IItemBase;
import com.jtripled.voxen.item.ItemBlockBase;
import com.jtripled.voxen.mod.ModBase;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 *
 * @author jtripled
 */
public class BlockTrapdoor extends net.minecraft.block.BlockTrapDoor implements IBlockBase
{
    private ModBase owner;
    private String name;
    private boolean registered;
    private IItemBase item;
    private IProperty[] ignoredProperties;
    private Class<? extends TileEntity> tileClass;
    private Class<? extends TileEntitySpecialRenderer> tileRenderer;
    private boolean harvestable;
    private boolean silkHarvest;
    private boolean opaque;
    private boolean full;
    private BlockRenderLayer layer;
    private int harvestLevel;
    private String harvestTool;
    private boolean uniqueInventoryModel;
    private IRecipe recipe;

    public BlockTrapdoor(String name, Material material)
    {
        super(material);
        this.owner = null;
        this.registered = false;
        this.name = name;
        this.harvestable = true;
        this.silkHarvest = false;
        this.opaque = true;
        this.full = true;
        this.layer = BlockRenderLayer.SOLID;
        this.harvestLevel = 0;
        this.harvestTool = "pickaxe";
        this.uniqueInventoryModel = false;
    }
    
    @Override
    public final boolean isRegistered()
    {
        return registered;
    }

    @Override
    public final void setRegistered(ModBase owner)
    {
        this.registered = true;
        this.owner = owner;
    }
    
    @Override
    public final String getName()
    {
        return name;
    }
    
    @Override
    public final ModBase getOwner()
    {
        return owner;
    }
    
    /*
     * ItemBlock methods.
     */

    @Override
    public final IItemBase getItem()
    {
        return item;
    }
    
    @Override
    public final boolean hasItem()
    {
        return getItem() != null;
    }
    
    public final BlockTrapdoor setItem()
    {
        if (!isRegistered())
        {
            this.item = new ItemBlockBase(this);
        }
        return this;
    }
    
    public final BlockTrapdoor setItem(IItemBase item)
    {
        if (!isRegistered())
        {
            this.item = item;
        }
        return this;
    }
    
    /*
     * Creative tab methods.
     */
    
    public final BlockTrapdoor setTab(CreativeTabs tab)
    {
        if (!isRegistered())
        {
            this.setCreativeTab(tab);
        }
        return this;
    }
    
    /*
     * Blockstate methods.
     */
    
    @Override
    public final IProperty[] getIgnoredProperties()
    {
        return ignoredProperties;
    }
    
    @Override
    public final boolean hasIgnoredProperties()
    {
        return getIgnoredProperties() != null;
    }
    
    public final BlockTrapdoor setIgnoredProperties(IProperty[] ignoredProperties)
    {
        if (!isRegistered())
        {
            this.ignoredProperties = ignoredProperties;
        }
        return this;
    }
    
    /*
     * Tile entity special renderer methods.
     */

    @Override
    public final Class<? extends TileEntitySpecialRenderer> getTESRClass()
    {
        return tileRenderer;
    }
    
    @Override
    public final boolean hasTESR()
    {
        return getTESRClass() != null;
    }
    
    @Override
    public final TileEntitySpecialRenderer createTESR()
    {
        if (hasTESR())
        {
            try
            {
                return getTESRClass().newInstance();
            }
            catch (InstantiationException | IllegalAccessException ex)
            {
                Logger.getLogger(BlockBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public final BlockTrapdoor setTESRClass(Class<? extends TileEntitySpecialRenderer> tileRenderer)
    {
        if (!isRegistered())
        {
            this.tileRenderer = tileRenderer;
        }
        return this;
    }
    
    /*
     * Tile entity methods.
     */

    @Override
    public final Class<? extends TileEntity> getTileClass()
    {
        return tileClass;
    }
    
    @Override
    public final boolean hasTile()
    {
        return getTileClass() != null;
    }
    
    @Override
    public final boolean hasTileEntity()
    {
        return getTileClass() != null;
    }
    
    @Override
    public final boolean hasTileEntity(IBlockState state)
    {
        return getTileClass() != null;
    }
    
    @Override
    public final TileEntity createTileEntity(World world, IBlockState state)
    {
        if (hasTileEntity(state))
        {
            try
            {
                return getTileClass().newInstance();
            }
            catch (InstantiationException | IllegalAccessException ex)
            {
                Logger.getLogger(BlockBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public final BlockTrapdoor setTileClass(Class<? extends TileEntity> tileClass)
    {
        if (!isRegistered())
        {
            this.tileClass = tileClass;
        }
        return this;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune)
    {
        if (harvestable && hasItem())
            return (Item) getItem();
        return null;
    }
    
    public final BlockTrapdoor setHarvestable(boolean harvestable)
    {
        if (!isRegistered())
        {
            this.harvestable = harvestable;
        }
        return this;
    }
    
    public final BlockTrapdoor setSilkHarvestable(boolean harvestable)
    {
        if (!isRegistered())
        {
            this.silkHarvest = harvestable;
        }
        return this;
    }
    
    @Override
    public final boolean canSilkHarvest()
    {
        return harvestable || silkHarvest;
    }
    
    public final BlockTrapdoor setHarvestLevel(int level)
    {
        if (!isRegistered())
        {
            this.harvestLevel = harvestLevel;
        }
        return this;
    }
    
    @Override
    public final int getHarvestLevel(IBlockState state)
    {
        return harvestLevel;
    }
    
    public final BlockTrapdoor setHarvestTool(String tool)
    {
        if (!isRegistered())
        {
            this.harvestTool = harvestTool;
        }
        return this;
    }
    
    @Override
    public final String getHarvestTool(IBlockState state)
    {
        return harvestTool;
    }
    
    public final BlockTrapdoor setOpaque(boolean opaque)
    {
        if (!isRegistered())
        {
            this.opaque = opaque;
        }
        return this;
    }
    
    @Override
    public final boolean isOpaqueCube(IBlockState state)
    {
        return opaque;
    }
    
    public final BlockTrapdoor setFullCube(boolean full)
    {
        if (!isRegistered())
        {
            this.full = full;
        }
        return this;
    }
    
    @Override
    public final boolean isFullCube(IBlockState state)
    {
        return full;
    }
    
    public final BlockTrapdoor setRecipe(boolean shaped, ItemStack output, Object... input)
    {
        if (!isRegistered())
        {
            
        }
        return this;
    }
    
    @Override
    public final IRecipe getRecipe()
    {
        return this.recipe;
    }
    
    @Override
    public final boolean hasRecipe()
    {
        return this.recipe != null;
    }
    
    public final BlockTrapdoor setUniqueInventoryModel(boolean unique)
    {
        if (!isRegistered())
        {
            this.uniqueInventoryModel = unique;
        }
        return this;
    }
    
    @Override
    public final boolean hasUniqueInventoryModel()
    {
        return uniqueInventoryModel;
    }
    
    public final BlockTrapdoor setRenderLayer(BlockRenderLayer layer)
    {
        if (!isRegistered())
        {
            this.layer = layer;
        }
        return this;
    }
 
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return layer;
    }
}
