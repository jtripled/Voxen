package com.jtripled.voxen.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 *
 * @author jtripled
 */
public interface IBlockStorage extends IBlockBase
{
    public default IItemHandler getStorage(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null)
        {
            return tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        }
        return null;
    }
    
    public default boolean canDropStorage(World world, BlockPos pos, IBlockState state)
    {
        return true;
    }
    
    public static void dropStorage(IItemHandler handler, World world, double x, double y, double z)
    {
        Random rand = new Random();
        for (int i = 0; i < handler.getSlots(); ++i)
        {
            ItemStack stack = handler.getStackInSlot(i);
            if (!stack.isEmpty())
            {
                float f = rand.nextFloat() * 0.8F + 0.1F;
                float f1 = rand.nextFloat() * 0.8F + 0.1F;
                float f2 = rand.nextFloat() * 0.8F + 0.1F;

                while (!stack.isEmpty())
                {
                    EntityItem entityitem = new EntityItem(world, x + (double)f, y + (double)f1, z + (double)f2, stack.splitStack(rand.nextInt(21) + 10));
                    float f3 = 0.05F;
                    entityitem.motionX = rand.nextGaussian() * 0.05000000074505806D;
                    entityitem.motionY = rand.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
                    entityitem.motionZ = rand.nextGaussian() * 0.05000000074505806D;
                    world.spawnEntity(entityitem);
                }
            }
        }
    }
}
