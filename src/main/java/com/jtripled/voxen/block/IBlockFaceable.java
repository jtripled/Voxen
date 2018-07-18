package com.jtripled.voxen.block;

import com.jtripled.voxen.block.BlockBase.BlockPredicate;
import javax.annotation.Nullable;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 *
 * @author jtripled
 */
public interface IBlockFaceable extends IBlockBase
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyDirection FACING_HORIZONTAL = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyDirection FACING_VERTICAL = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyDirection FACING_NO_UP = PropertyDirection.create("facing", (@Nullable EnumFacing face) -> face != EnumFacing.UP);
    
    public EnumFacing getFacing(IBlockState state);
    
    public PropertyDirection getFaceableProperty();
    
    public IBlockState getFaceableStateForPlacement(IBlockState state, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer);
    
    public IBlockState getFaceableStateFromMeta(IBlockState state, int meta);

    public int getFaceableMetaFromState(IBlockState state);
    
    public IBlockState addFaceableDefaultStates(IBlockState defaultState);
    
    public static interface All extends IBlockFaceable
    {
        @Override
        public default EnumFacing getFacing(IBlockState state)
        {
            return state.getValue(FACING);
        }
        
        @Override
        public default PropertyDirection getFaceableProperty()
        {
            return FACING;
        }
        
        @Override
        public default IBlockState getFaceableStateForPlacement(IBlockState state, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
        {
            return state.withProperty(FACING, facing.getOpposite());
        }
    
        @Override
        public default IBlockState getFaceableStateFromMeta(IBlockState state, int meta)
        {
            return state.withProperty(FACING, EnumFacing.getFront(meta & 7));
        }

        @Override
        public default int getFaceableMetaFromState(IBlockState state)
        {
            return state.getValue(FACING).getIndex();
        }
    
        @Override
        public default IBlockState addFaceableDefaultStates(IBlockState defaultState)
        {
            return defaultState.withProperty(FACING, EnumFacing.NORTH);
        }
        
        public default boolean isFacingDown(IBlockState state)
        {
            return getFacing(state) == EnumFacing.DOWN;
        }
        
        public default boolean isFacingUp(IBlockState state)
        {
            return getFacing(state) == EnumFacing.UP;
        }
        
        public default boolean isFacingSouth(IBlockState state)
        {
            return getFacing(state) == EnumFacing.SOUTH;
        }
        
        public default boolean isFacingNorth(IBlockState state)
        {
            return getFacing(state) == EnumFacing.NORTH;
        }
        
        public default boolean isFacingWest(IBlockState state)
        {
            return getFacing(state) == EnumFacing.WEST;
        }
        
        public default boolean isFacingEast(IBlockState state)
        {
            return getFacing(state) == EnumFacing.EAST;
        }
        
        public static BlockPredicate IS_FACING_DOWN =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING) == EnumFacing.DOWN);
        
        public static BlockPredicate IS_FACING_UP =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING) == EnumFacing.UP);
        
        public static BlockPredicate IS_FACING_SOUTH =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING) == EnumFacing.SOUTH);
        
        public static BlockPredicate IS_FACING_NORTH =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING) == EnumFacing.NORTH);
        
        public static BlockPredicate IS_FACING_WEST =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING) == EnumFacing.WEST);
        
        public static BlockPredicate IS_FACING_EAST =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING) == EnumFacing.EAST);
    }
    
    public static interface Horizontal extends IBlockFaceable
    {
        @Override
        public default EnumFacing getFacing(IBlockState state)
        {
            return state.getValue(FACING_HORIZONTAL);
        }
        
        @Override
        public default PropertyDirection getFaceableProperty()
        {
            return FACING_HORIZONTAL;
        }
        
        @Override
        public default IBlockState getFaceableStateForPlacement(IBlockState state, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
        {
            return state.withProperty(FACING_HORIZONTAL, placer.getHorizontalFacing());
        }
    
        @Override
        public default IBlockState getFaceableStateFromMeta(IBlockState state, int meta)
        {
            return state.withProperty(FACING_HORIZONTAL, EnumFacing.getFront((meta & 3) + 2));
        }

        @Override
        public default int getFaceableMetaFromState(IBlockState state)
        {
            return state.getValue(FACING_HORIZONTAL).getIndex() - 2;
        }
    
        @Override
        public default IBlockState addFaceableDefaultStates(IBlockState defaultState)
        {
            return defaultState.withProperty(FACING_HORIZONTAL, EnumFacing.NORTH);
        }
        
        public default boolean isFacingSouth(IBlockState state)
        {
            return getFacing(state) == EnumFacing.SOUTH;
        }
        
        public default boolean isFacingNorth(IBlockState state)
        {
            return getFacing(state) == EnumFacing.NORTH;
        }
        
        public default boolean isFacingWest(IBlockState state)
        {
            return getFacing(state) == EnumFacing.WEST;
        }
        
        public default boolean isFacingEast(IBlockState state)
        {
            return getFacing(state) == EnumFacing.EAST;
        }
        
        public static BlockPredicate IS_FACING_SOUTH =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_HORIZONTAL) == EnumFacing.SOUTH);
        
        public static BlockPredicate IS_FACING_NORTH =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_HORIZONTAL) == EnumFacing.NORTH);
        
        public static BlockPredicate IS_FACING_WEST =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_HORIZONTAL) == EnumFacing.WEST);
        
        public static BlockPredicate IS_FACING_EAST =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_HORIZONTAL) == EnumFacing.EAST);
    }
    
    public static interface Vertical extends IBlockFaceable
    {
        @Override
        public default EnumFacing getFacing(IBlockState state)
        {
            return state.getValue(FACING_VERTICAL);
        }
        
        @Override
        public default PropertyDirection getFaceableProperty()
        {
            return FACING_VERTICAL;
        }
        
        @Override
        public default IBlockState getFaceableStateForPlacement(IBlockState state, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
        {
            return state.withProperty(FACING_VERTICAL, EnumFacing.UP);
        }
    
        @Override
        public default IBlockState getFaceableStateFromMeta(IBlockState state, int meta)
        {
            return state.withProperty(FACING_VERTICAL, EnumFacing.getFront(meta & 1));
        }

        @Override
        public default int getFaceableMetaFromState(IBlockState state)
        {
            return state.getValue(FACING_VERTICAL).getIndex();
        }
    
        @Override
        public default IBlockState addFaceableDefaultStates(IBlockState defaultState)
        {
            return defaultState.withProperty(FACING_VERTICAL, EnumFacing.UP);
        }
        
        public default boolean isFacingDown(IBlockState state)
        {
            return getFacing(state) == EnumFacing.DOWN;
        }
        
        public default boolean isFacingUp(IBlockState state)
        {
            return getFacing(state) == EnumFacing.UP;
        }
        
        public static BlockPredicate IS_FACING_DOWN =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_VERTICAL) == EnumFacing.DOWN);
        
        public static BlockPredicate IS_FACING_UP =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_VERTICAL) == EnumFacing.UP);
    }
    
    public static interface NoUp extends IBlockFaceable
    {
        @Override
        public default EnumFacing getFacing(IBlockState state)
        {
            return state.getValue(FACING_NO_UP);
        }
        
        @Override
        public default PropertyDirection getFaceableProperty()
        {
            return FACING_NO_UP;
        }
        
        @Override
        public default IBlockState getFaceableStateForPlacement(IBlockState state, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
        {
            if (facing.getOpposite() == EnumFacing.UP)
                return state.withProperty(FACING_NO_UP, EnumFacing.DOWN);
            return state.withProperty(FACING_NO_UP, facing.getOpposite());
        }
    
        @Override
        public default IBlockState getFaceableStateFromMeta(IBlockState state, int meta)
        {
            return state.withProperty(FACING_NO_UP, EnumFacing.getFront(meta & 7));
        }

        @Override
        public default int getFaceableMetaFromState(IBlockState state)
        {
            return state.getValue(FACING_NO_UP).getIndex();
        }
    
        @Override
        public default IBlockState addFaceableDefaultStates(IBlockState defaultState)
        {
            return defaultState.withProperty(FACING_NO_UP, EnumFacing.NORTH);
        }
        
        public default boolean isFacingDown(IBlockState state)
        {
            return getFacing(state) == EnumFacing.DOWN;
        }
        
        public default boolean isFacingSouth(IBlockState state)
        {
            return getFacing(state) == EnumFacing.SOUTH;
        }
        
        public default boolean isFacingNorth(IBlockState state)
        {
            return getFacing(state) == EnumFacing.NORTH;
        }
        
        public default boolean isFacingWest(IBlockState state)
        {
            return getFacing(state) == EnumFacing.WEST;
        }
        
        public default boolean isFacingEast(IBlockState state)
        {
            return getFacing(state) == EnumFacing.EAST;
        }
        
        public static BlockPredicate IS_FACING_DOWN =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_NO_UP) == EnumFacing.DOWN);
        
        public static BlockPredicate IS_FACING_SOUTH =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_NO_UP) == EnumFacing.SOUTH);
        
        public static BlockPredicate IS_FACING_NORTH =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_NO_UP) == EnumFacing.NORTH);
        
        public static BlockPredicate IS_FACING_WEST =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_NO_UP) == EnumFacing.WEST);
        
        public static BlockPredicate IS_FACING_EAST =
                (IBlockState state, IBlockAccess world, BlockPos pos) -> (state.getValue(FACING_NO_UP) == EnumFacing.EAST);
    }
}
