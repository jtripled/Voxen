package com.jtripled.voxen.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 *
 * @author jtripled
 */
public abstract class BlockConnect extends BlockBase
{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    
    public BlockConnect(String name, Material material)
    {
        this(name, material, material.getMaterialMapColor());
    }
    
    public BlockConnect(String name, Material material, MapColor mapColor)
    {
        super(name, material, mapColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(UP, false).withProperty(DOWN, false));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer((Block) this, new IProperty[] {NORTH, EAST, SOUTH, WEST, UP, DOWN});
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.withProperty(NORTH, canConnect(state, world, world.getBlockState(pos.north())))
             .withProperty(EAST, canConnect(state, world, world.getBlockState(pos.east())))
             .withProperty(SOUTH, canConnect(state, world, world.getBlockState(pos.south())))
             .withProperty(WEST, canConnect(state, world, world.getBlockState(pos.west())))
             .withProperty(UP, canConnect(state, world, world.getBlockState(pos.up())))
             .withProperty(DOWN, canConnect(state, world, world.getBlockState(pos.down())));
    }
    
    public abstract boolean canConnect(IBlockState state, IBlockAccess world, IBlockState otherState);
}
