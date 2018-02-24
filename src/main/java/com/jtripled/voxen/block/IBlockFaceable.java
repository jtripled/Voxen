package com.jtripled.voxen.block;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
    
    public EnumFacing getFacing(IBlockState state);
    
    public PropertyDirection getFaceableProperty();
    
    public IBlockState getFaceableStateForPlacement(IBlockState state, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer);
    
    public IBlockState getFaceableStateFromMeta(IBlockState state, int meta);

    public int getFaceableMetaFromState(IBlockState state);
    
    public IBlockState addFaceableDefaultStates(IBlockState defaultState);
    
    public static interface All extends IBlockFaceable
    {
        @Override
        public default PropertyDirection getFaceableProperty()
        {
            return FACING;
        }
        
        @Override
        public default IBlockState getFaceableStateForPlacement(IBlockState state, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
        {
            return state.withProperty(FACING, placer.getHorizontalFacing());
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
    }
    
    public static interface Horizontal extends IBlockFaceable
    {
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
    }
    
    public static interface Vertical extends IBlockFaceable
    {
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
    }
}
