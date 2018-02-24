package com.jtripled.voxen.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author jtripled
 */
public abstract class BlockDuct extends BlockBase implements IBlockConnectable.All
{
    public BlockDuct(String name, Material material)
    {
        this(name, material, material.getMaterialMapColor());
    }
    
    public BlockDuct(String name, Material material, MapColor mapColor)
    {
        super(name, material, mapColor);
        this.setOpaque(false);
        this.setFullCube(false);
        this.setBoundingBox(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        AxisAlignedBB bb = super.getBoundingBox(state, world, pos);
        state = state.getActualState(world, pos);
        if (this.isConnectedSouth(state)) bb = bb.expand( 0,       0,      0.3125);
        if (this.isConnectedNorth(state)) bb = bb.expand( 0,       0,     -0.3125);
        if (this.isConnectedEast(state))  bb = bb.expand( 0.3125,  0,      0);
        if (this.isConnectedWest(state))  bb = bb.expand(-0.3125,  0,      0);
        if (this.isConnectedUp(state))    bb = bb.expand( 0,       0.3125, 0);
        if (this.isConnectedDown(state))  bb = bb.expand( 0,      -0.3125, 0);
        return bb;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return true;
    }
}
