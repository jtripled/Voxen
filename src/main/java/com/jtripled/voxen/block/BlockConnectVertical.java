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
public abstract class BlockConnectVertical extends BlockBase
{
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    
    public BlockConnectVertical(String name, Material material)
    {
        this(name, material, material.getMaterialMapColor());
    }
    
    public BlockConnectVertical(String name, Material material, MapColor mapColor)
    {
        super(name, material, mapColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(UP, false).withProperty(DOWN, false));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer((Block) this, new IProperty[] {UP, DOWN});
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
        return state.withProperty(UP, canConnect(state, world, world.getBlockState(pos.up())))
             .withProperty(DOWN, canConnect(state, world, world.getBlockState(pos.down())));
    }
    
    public abstract boolean canConnect(IBlockState state, IBlockAccess world, IBlockState otherState);
}
