package com.jtripled.voxen.block;

import com.jtripled.voxen.gui.GUIHolder;
import com.jtripled.voxen.item.IItemBase;
import com.jtripled.voxen.item.ItemBlockBase;
import com.jtripled.voxen.mod.ModBase;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 *
 * @author jtripled
 */
public class BlockFalling extends net.minecraft.block.BlockFalling implements IBlockBase
{
    private ModBase mod;
    private String name;
    private MapColor color;
    private boolean registered;
    private IItemBase item;
    private IProperty[] ignoredProperties;
    private Class<? extends TileEntity> tileClass;
    private Class<? extends TileEntitySpecialRenderer> tileRenderer;

    public BlockFalling(ModBase mod, String name, Material material)
    {
        this(mod, name, material, material.getMaterialMapColor());
    }

    public BlockFalling(ModBase mod, String name, Material material, MapColor color)
    {
        super(material);
        this.registered = false;
        this.mod = mod;
        this.name = name;
        this.setUnlocalizedName(name);
        this.setRegistryName(mod.getID(), name);
    }
    
    @Override
    public final boolean isRegistered()
    {
        return registered;
    }

    @Override
    public final void setRegistered()
    {
        this.registered = true;
    }

    @Override
    public final ModBase getMod()
    {
        return mod;
    }
    
    @Override
    public final String getName()
    {
        return name;
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
    
    public final BlockFalling setItem()
    {
        if (!isRegistered())
        {
            this.item = new ItemBlockBase(this);
        }
        return this;
    }
    
    public final BlockFalling setItem(IItemBase item)
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
    
    public final BlockFalling setTab(CreativeTabs tab)
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
    
    public final BlockFalling setIgnoredProperties(IProperty[] ignoredProperties)
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
    
    public final BlockFalling setTESRClass(Class<? extends TileEntitySpecialRenderer> tileRenderer)
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
    
    public final BlockFalling setTileClass(Class<? extends TileEntity> tileClass)
    {
        if (!isRegistered())
        {
            this.tileClass = tileClass;
        }
        return this;
    }
    
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return color;
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (this instanceof GUIHolder && !world.isRemote)
        {
            ((GUIHolder) this).openGUI(player, world, pos);
        }
        else
        {
            return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
        }
        return true;
    }
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        if (this instanceof IBlockStorage && !world.isRemote)
        {
            IBlockStorage storage = (IBlockStorage) this;
            if (storage.canDropStorage(world, pos, state))
            {
                IBlockStorage.dropStorage(storage.getStorage(world, pos, state), world, pos.getX(), pos.getY(), pos.getZ());
                world.updateComparatorOutputLevel(pos, this);
            }
        }
        super.breakBlock(world, pos, state);
    }
}
