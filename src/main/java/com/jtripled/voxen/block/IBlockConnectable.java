package com.jtripled.voxen.block;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 *
 * @author jtripled
 */
public interface IBlockConnectable extends IBlockBase
{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    
    public abstract boolean canConnect(BlockPos pos, IBlockState state, IBlockAccess world, BlockPos otherPos, IBlockState otherState, EnumFacing face);
    
    public PropertyBool[] getConnectableProperties();
    
    public IBlockState addConnectableDefaultStates(IBlockState defaultState);
    
    public IBlockState getConnectableActualState(IBlockState state, IBlockAccess world, BlockPos pos);
    
    public static interface All extends IBlockConnectable
    {
        public static final PropertyBool[] PROPERTIES = new PropertyBool[] { DOWN, UP, NORTH, SOUTH, WEST, EAST };
        
        @Override
        public default PropertyBool[] getConnectableProperties()
        {
            return PROPERTIES;
        }

        @Override
        public default IBlockState addConnectableDefaultStates(IBlockState defaultState)
        {
            return defaultState.withProperty(DOWN, false).withProperty(UP, false).withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(EAST, false);
        }
    
        @Override
        public default IBlockState getConnectableActualState(IBlockState state, IBlockAccess world, BlockPos pos)
        {
            return state.withProperty(NORTH, this.canConnect(pos, state, world, pos.north(), world.getBlockState(pos.north()), EnumFacing.NORTH)).withProperty(EAST, this.canConnect(pos, state, world, pos.east(), world.getBlockState(pos.east()), EnumFacing.EAST)).withProperty(SOUTH, this.canConnect(pos, state, world, pos.south(), world.getBlockState(pos.south()), EnumFacing.SOUTH)).withProperty(WEST, this.canConnect(pos, state, world, pos.west(), world.getBlockState(pos.west()), EnumFacing.WEST)).withProperty(UP, this.canConnect(pos, state, world, pos.up(), world.getBlockState(pos.up()), EnumFacing.UP)).withProperty(DOWN, this.canConnect(pos, state, world, pos.down(), world.getBlockState(pos.down()), EnumFacing.DOWN));
        }

        public default boolean isConnectedDown(IBlockState state)
        {
            return state.getValue(DOWN);
        }

        public default boolean isConnectedUp(IBlockState state)
        {
            return state.getValue(UP);
        }
    
        public default boolean isConnectedNorth(IBlockState state)
        {
            return state.getValue(NORTH);
        }

        public default boolean isConnectedSouth(IBlockState state)
        {
            return state.getValue(SOUTH);
        }

        public default boolean isConnectedWest(IBlockState state)
        {
            return state.getValue(WEST);
        }

        public default boolean isConnectedEast(IBlockState state)
        {
            return state.getValue(EAST);
        }
    }
    
    public static interface Horizontal extends IBlockConnectable
    {
        public static final PropertyBool[] PROPERTIES = new PropertyBool[] { NORTH, SOUTH, WEST, EAST };
        
        @Override
        public default PropertyBool[] getConnectableProperties()
        {
            return PROPERTIES;
        }

        @Override
        public default IBlockState addConnectableDefaultStates(IBlockState defaultState)
        {
            return defaultState.withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(EAST, false);
        }
    
        @Override
        public default IBlockState getConnectableActualState(IBlockState state, IBlockAccess world, BlockPos pos)
        {
            return state.withProperty(NORTH, this.canConnect(pos, state, world, pos.north(), world.getBlockState(pos.north()), EnumFacing.NORTH)).withProperty(EAST, this.canConnect(pos, state, world, pos.east(), world.getBlockState(pos.east()), EnumFacing.EAST)).withProperty(SOUTH, this.canConnect(pos, state, world, pos.south(), world.getBlockState(pos.south()), EnumFacing.SOUTH)).withProperty(WEST, this.canConnect(pos, state, world, pos.west(), world.getBlockState(pos.west()), EnumFacing.WEST));
        }
    
        public default boolean isConnectedNorth(IBlockState state)
        {
            return state.getValue(NORTH);
        }

        public default boolean isConnectedSouth(IBlockState state)
        {
            return state.getValue(SOUTH);
        }

        public default boolean isConnectedWest(IBlockState state)
        {
            return state.getValue(WEST);
        }

        public default boolean isConnectedEast(IBlockState state)
        {
            return state.getValue(EAST);
        }
    }
    
    public static interface Vertical extends IBlockConnectable
    {
        public static final PropertyBool[] PROPERTIES = new PropertyBool[] { DOWN, UP };
        
        @Override
        public default PropertyBool[] getConnectableProperties()
        {
            return PROPERTIES;
        }

        @Override
        public default IBlockState addConnectableDefaultStates(IBlockState defaultState)
        {
            return defaultState.withProperty(DOWN, false).withProperty(UP, false);
        }
    
        @Override
        public default IBlockState getConnectableActualState(IBlockState state, IBlockAccess world, BlockPos pos)
        {
            return state.withProperty(UP, this.canConnect(pos, state, world, pos.up(), world.getBlockState(pos.up()), EnumFacing.UP)).withProperty(DOWN, this.canConnect(pos, state, world, pos.down(), world.getBlockState(pos.down()), EnumFacing.DOWN));
        }

        public default boolean isConnectedDown(IBlockState state)
        {
            return state.getValue(DOWN);
        }

        public default boolean isConnectedUp(IBlockState state)
        {
            return state.getValue(UP);
        }
    }
}
