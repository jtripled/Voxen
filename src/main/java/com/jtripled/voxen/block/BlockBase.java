package com.jtripled.voxen.block;

import com.google.common.collect.Lists;
import com.jtripled.voxen.gui.GUIHolder;
import com.jtripled.voxen.item.IItemBase;
import com.jtripled.voxen.item.ItemBlockBase;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import static net.minecraft.block.Block.FULL_BLOCK_AABB;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author jtripled
 */
public class BlockBase extends Block implements IBlockBase
{
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
    private AxisAlignedBB box;
    
    public BlockBase(String name, Material material)
    {
        this(name, material, material.getMaterialMapColor());
    }

    public BlockBase(String name, Material material, MapColor mapColor)
    {
        super(material, mapColor);
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
        this.box = FULL_BLOCK_AABB;
        
        if (this instanceof IBlockFaceable)
            this.setDefaultState(((IBlockFaceable) this).addFaceableDefaultStates(this.getDefaultState()));
        
        if (this instanceof IBlockConnectable)
            this.setDefaultState(((IBlockConnectable) this).addConnectableDefaultStates(this.getDefaultState()));
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
    
    public final BlockBase setItem()
    {
        if (!isRegistered())
        {
            this.item = new ItemBlockBase(this);
        }
        return this;
    }
    
    public final BlockBase setItem(IItemBase item)
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
    
    public final BlockBase setTab(CreativeTabs tab)
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
    
    public final BlockBase setIgnoredProperties(IProperty[] ignoredProperties)
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
    
    public final BlockBase setTESRClass(Class<? extends TileEntitySpecialRenderer> tileRenderer)
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
    
    public final BlockBase setTileClass(Class<? extends TileEntity> tileClass)
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
    
    public final BlockBase setHarvestable(boolean harvestable)
    {
        if (!isRegistered())
        {
            this.harvestable = harvestable;
        }
        return this;
    }
    
    public final BlockBase setSilkHarvestable(boolean harvestable)
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
    
    public final BlockBase setHarvestLevel(int harvestLevel)
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
    
    public final BlockBase setHarvestTool(String harvestTool)
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
    
    public final BlockBase setOpaque(boolean opaque)
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
    
    public final BlockBase setFullCube(boolean full)
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
        return this.full;
    }
    
    public final BlockBase setRecipe(boolean shaped, ItemStack output, Object... input)
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
    
    public final BlockBase setBoundingBox(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        if (!isRegistered())
        {
            this.box = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
        }
        return this;
    }
    
    public final BlockBase setBoundingBox(AxisAlignedBB bb)
    {
        if (!isRegistered())
        {
            this.box = bb;
        }
        return this;
    }

    @Deprecated
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return this.box;
    }
    
    public final BlockBase setUniqueInventoryModel(boolean unique)
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
    
    public final BlockBase setRenderLayer(BlockRenderLayer layer)
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
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (this instanceof GUIHolder)
        {
            if (!world.isRemote)
            {
                ((GUIHolder) this).openGUI(player, world, pos);
            }
            return true;
        }
        else if ((IBlockBase) this instanceof IBlockSittable &&
                IBlockSittable.sitOnBlock(world, pos.getX(), pos.getY(), pos.getZ(), player, ((IBlockSittable) this).getSeatHeight(state, world, pos)))
        {
            if (!world.isRemote)
            {
                world.updateComparatorOutputLevel(pos, this);
            }
            return true;
        }
        else
        {
            return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
        }
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
    
    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        if ((IBlockBase) state.getBlock() instanceof IBlockSittable)
        {
            return true;
        }
        return super.hasComparatorInputOverride(state);
    }
    
    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        if ((IBlockBase) state.getBlock() instanceof IBlockSittable)
        {
            return IBlockSittable.isSomeoneSitting(world, pos.getX(), pos.getY(), pos.getZ()) ? 1 : 0;
        }
        return super.getComparatorInputOverride(state, world, pos);
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = this.getDefaultState();
        if (this instanceof IBlockFaceable)
            state = ((IBlockFaceable) this).getFaceableStateForPlacement(state, world, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        List<IProperty> properties = new ArrayList<>();
        
        if (this instanceof IBlockFaceable)
            properties.add(((IBlockFaceable) this).getFaceableProperty());
        
        if (this instanceof IBlockConnectable)
            properties.addAll(Lists.newArrayList(((IBlockConnectable) this).getConnectableProperties()));
        
        if (!properties.isEmpty())
            return new BlockStateContainer(this, properties.toArray(new IProperty[properties.size()]));
        
        return super.createBlockState();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState();
        if (this instanceof IBlockFaceable)
            return ((IBlockFaceable) this).getFaceableStateFromMeta(state, meta);
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        if (this instanceof IBlockFaceable)
            return ((IBlockFaceable) this).getFaceableMetaFromState(state);
        return 0;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if (this instanceof IBlockConnectable)
            state = ((IBlockConnectable) this).getConnectableActualState(state, world, pos);
        return state;
    }
}
