package com.jtripled.voxen.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 *
 * @author jtripled
 */
public abstract class BlockDuct extends BlockBase implements IBlockConnectable.All
{
    public BlockDuct(String name, Material material)
    {
        super(name, material);
        this.setBoundingBox(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);
        this.setFullCube(false);
        this.setOpaque(false);
        this.setRenderSides(true);
    }

    @Deprecated
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        AxisAlignedBB bb = super.getBoundingBox(state, world, pos);
        state = state.getActualState(world, pos);
        if (this instanceof IBlockFaceable)
        {
            EnumFacing face = state.getValue(((IBlockFaceable) this).getFaceableProperty());
            switch (face)
            {
                case NORTH: state = state.withProperty(IBlockConnectable.NORTH, true); break;
                case EAST: state = state.withProperty(IBlockConnectable.EAST, true); break;
                case SOUTH: state = state.withProperty(IBlockConnectable.SOUTH, true); break;
                case WEST: state = state.withProperty(IBlockConnectable.WEST, true); break;
                case UP: state = state.withProperty(IBlockConnectable.UP, true); break;
                case DOWN: state = state.withProperty(IBlockConnectable.DOWN, true); break;
            }
        }
        IBlockConnectable.All blk = (IBlockConnectable.All) this;
        if (blk.isConnectedSouth(state)) bb = bb.expand( 0,       0,      0.3125);
        if (blk.isConnectedNorth(state)) bb = bb.expand( 0,       0,     -0.3125);
        if (blk.isConnectedEast(state))  bb = bb.expand( 0.3125,  0,      0);
        if (blk.isConnectedWest(state))  bb = bb.expand(-0.3125,  0,      0);
        if (blk.isConnectedUp(state))    bb = bb.expand( 0,       0.3125, 0);
        if (blk.isConnectedDown(state))  bb = bb.expand( 0,      -0.3125, 0);
        return bb;
    }
}
