package com.jtripled.voxen.block;

import com.jtripled.voxen.mod.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
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
public abstract class BlockDuct extends BlockBase
{
    public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);
    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    
    public BlockDuct(ModBase mod, String name, Material material)
    {
        this(mod, name, material, material.getMaterialMapColor());
    }
    
    public BlockDuct(ModBase mod, String name, Material material, MapColor mapColor)
    {
        super(mod, name, material, mapColor);
        if (hasFacing())
            this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(UP, false).withProperty(DOWN, false).withProperty(FACING, EnumFacing.NORTH));
        else
            this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(UP, false).withProperty(DOWN, false));
        this.setOpaque(false);
        this.setFullCube(false);
    }
    
    public boolean hasFacing()
    {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        if (hasFacing())
            return new BlockStateContainer((Block) this, new IProperty[] {FACING, NORTH, EAST, SOUTH, WEST, UP, DOWN});
        return new BlockStateContainer((Block) this, new IProperty[] {NORTH, EAST, SOUTH, WEST, UP, DOWN});
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (hasFacing())
            return ((Block) this).getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
        return ((Block) this).getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        if (hasFacing())
            return ((EnumFacing)state.getValue(FACING)).getIndex();
        return 0;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        AxisAlignedBB bb = BOUNDING_BOX;
        state = state.getActualState(world, pos);
        boolean north = state.getValue(NORTH);
        boolean east = state.getValue(EAST);
        boolean south = state.getValue(SOUTH);
        boolean west = state.getValue(WEST);
        boolean up = state.getValue(UP);
        boolean down = state.getValue(DOWN);
        if (hasFacing())
        {
            switch(state.getValue(FACING))
            {
                case NORTH: north = true; break;
                case EAST: east = true; break;
                case SOUTH: south = true; break;
                case WEST: west = true; break;
                case UP: up = true; break;
                case DOWN: down = true; break;
            }
        }
        if (south) bb = bb.expand( 0,       0,      0.3125);
        if (north) bb = bb.expand( 0,       0,     -0.3125);
        if (east)  bb = bb.expand( 0.3125,  0,      0);
        if (west)  bb = bb.expand(-0.3125,  0,      0);
        if (up)    bb = bb.expand( 0,       0.3125, 0);
        if (down)  bb = bb.expand( 0,      -0.3125, 0);
        return bb;
    }
    
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = getDefaultState();
        if (hasFacing())
            state = state.withProperty(FACING, facing.getOpposite());
        return state;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        EnumFacing facing = null;
        if (hasFacing())
            facing = ((EnumFacing)state.getValue(FACING));
        return state.withProperty(NORTH, canConnect(state, world, pos.north(), EnumFacing.SOUTH) && facing != EnumFacing.NORTH)
             .withProperty(EAST, canConnect(state, world, pos.east(), EnumFacing.WEST) && facing != EnumFacing.EAST)
             .withProperty(SOUTH, canConnect(state, world, pos.south(), EnumFacing.NORTH) && facing != EnumFacing.SOUTH)
             .withProperty(WEST, canConnect(state, world, pos.west(), EnumFacing.EAST) && facing != EnumFacing.WEST)
             .withProperty(UP, canConnect(state, world, pos.up(), EnumFacing.DOWN) && facing != EnumFacing.UP)
             .withProperty(DOWN, canConnect(state, world, pos.down(), EnumFacing.UP) && facing != EnumFacing.DOWN);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return true;
    }
    
    public abstract boolean canConnect(IBlockState state, IBlockAccess world, BlockPos otherPos, EnumFacing otherFacing);
}
