package com.jtripled.voxen.tile;

import com.jtripled.voxen.Voxen;
import com.jtripled.voxen.network.MessageFluidCapacity;
import com.jtripled.voxen.network.MessageFluidContent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/**
 *
 * @author jtripled
 */
public interface ITileFluidStorage extends IFluidHandler
{
    public FluidTank getInternalTank();
    
    public BlockPos getInternalTankPos();
    
    public default int getCapacity()
    {
        return getInternalTank().getCapacity();
    }
    
    public default void setCapacity(int capacity)
    {
        FluidTank tank = getInternalTank();
        tank.setCapacity(capacity);
        if (tank.getFluidAmount() > capacity)
            this.fill(new FluidStack(this.drain(Integer.MAX_VALUE, true), capacity), true);
        if (!((TileEntity) this).getWorld().isRemote)
        {
            Voxen.INSTANCE.getNetwork().sendToAll(new MessageFluidCapacity(getInternalTankPos(), tank.getCapacity()));
            if (tank.getFluidAmount() > capacity)
                Voxen.INSTANCE.getNetwork().sendToAll(new MessageFluidContent(getInternalTankPos(), new FluidStack(tank.getFluid(), tank.getFluidAmount())));
        }
    }

    public default NBTTagCompound writeInternalTank(NBTTagCompound compound)
    {
        getInternalTank().writeToNBT(compound);
        return compound;
    }

    public default void readInternalTank(NBTTagCompound compound)
    {
        getInternalTank().readFromNBT(compound);
    }
    
    @Override
    public default IFluidTankProperties[] getTankProperties()
    {
        return getInternalTank().getTankProperties();
    }

    @Override
    public default int fill(FluidStack resource, boolean doFill)
    {
        int filled = getInternalTank().fill(resource, doFill);
        if (!((TileEntity) this).getWorld().isRemote && doFill && filled > 0)
        {
            FluidTank tank = getInternalTank();
            Voxen.INSTANCE.getNetwork().sendToAll(new MessageFluidContent(getInternalTankPos(), tank.getFluid()));
        }
        return filled;
    }

    @Override
    public default FluidStack drain(FluidStack resource, boolean doDrain)
    {
        FluidStack drained = getInternalTank().drain(resource, doDrain);
        if (!((TileEntity) this).getWorld().isRemote && doDrain && drained != null)
        {
            FluidTank tank = getInternalTank();
            Voxen.INSTANCE.getNetwork().sendToAll(new MessageFluidContent(getInternalTankPos(), tank.getFluid()));
        }
        return drained;
    }

    @Override
    public default FluidStack drain(int maxDrain, boolean doDrain)
    {
        FluidStack drained = getInternalTank().drain(maxDrain, doDrain);
        if (!((TileEntity) this).getWorld().isRemote && doDrain && drained != null)
        {
            FluidTank tank = getInternalTank();
            Voxen.INSTANCE.getNetwork().sendToAll(new MessageFluidContent(getInternalTankPos(), tank.getFluid()));
        }
        return drained;
    }
}
