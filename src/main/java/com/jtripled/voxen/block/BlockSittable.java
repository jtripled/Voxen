package com.jtripled.voxen.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 *
 * @author jtripled
 */
public class BlockSittable extends BlockBase
{
    private double height = 0.5d;
    
    public BlockSittable(String name, Material material)
    {
        super(name, material);
    }
    
    public BlockSittable(String name, Material material, MapColor color)
    {
        super(name, material, color);
    }
    
    public final BlockSittable setSeatHeight(double height)
    {
        if (!this.isRegistered())
            this.height = height;
        return this;
    }
    
    public final double getSeatHeight()
    {
        return this.height;
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (sitOnBlock(world, pos.getX(), pos.getY(), pos.getZ(), player, getSeatHeight()))
        {
            world.updateComparatorOutputLevel(pos, this);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        return isSomeoneSitting(world, pos.getX(), pos.getY(), pos.getZ()) ? 1 : 0;
    }
    
    public static boolean sitOnBlock(World world, double x, double y, double z, EntityPlayer player, double height)
    {
        if (!checkForExistingEntity(world, x, y, z, player))
        {
            EntitySittableBlock seat = new EntitySittableBlock(world, x, y, z, height);
            world.spawnEntity(seat);
            player.startRiding(seat);
        }
        return true;
    }

    public static boolean sitOnBlockWithRotationOffset(World world, double x, double y, double z, EntityPlayer player, double height, int metadata, double offset)
    {
        if ((!checkForExistingEntity(world, x, y, z, player)) && (!world.isRemote))
        {
            EntitySittableBlock seat = new EntitySittableBlock(world, x, y, z, height, metadata, offset);
            world.spawnEntity(seat);
            player.startRiding(seat);
        }
        return true;
    }

    public static boolean checkForExistingEntity(World world, double x, double y, double z, EntityPlayer player)
    {
        List<EntitySittableBlock> listEMB = world.getEntitiesWithinAABB(EntitySittableBlock.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1.0D, 1.0D, 1.0D));
        for (EntitySittableBlock mount : listEMB) {
            if ((mount.blockPosX == x) && (mount.blockPosY == y) && (mount.blockPosZ == z))
            {
                if (!mount.isBeingRidden()) {
                    player.startRiding(mount);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isSomeoneSitting(World world, double x, double y, double z)
    {
        List<EntitySittableBlock> listEMB = world.getEntitiesWithinAABB(EntitySittableBlock.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1.0D, 1.0D, 1.0D));
        for (EntitySittableBlock mount : listEMB) {
            if ((mount.blockPosX == x) && (mount.blockPosY == y) && (mount.blockPosZ == z)) {
                return mount.isBeingRidden();
            }
        }
        return false;
    }
    
    public static class EntitySittableBlock extends Entity
    {
        public int blockPosX;
        public int blockPosY;
        public int blockPosZ;

        public EntitySittableBlock(World world)
        {
            super(world);
            this.noClip = true;
            this.height = 0.01F;
            this.width = 0.01F;
        }

        public EntitySittableBlock(World world, double x, double y, double z, double y0ffset)
        {
            this(world);
            this.blockPosX = ((int)x);
            this.blockPosY = ((int)y);
            this.blockPosZ = ((int)z);
            this.setPosition(x + 0.5D, y + y0ffset, z + 0.5D);
        }

        public EntitySittableBlock(World world, double x, double y, double z, double y0ffset, int rotation, double rotationOffset)
        {
            this(world);
            this.blockPosX = ((int)x);
            this.blockPosY = ((int)y);
            this.blockPosZ = ((int)z);
            this.setPostionConsideringRotation(x + 0.5D, y + y0ffset, z + 0.5D, rotation, rotationOffset);
        }

        public void setPostionConsideringRotation(double x, double y, double z, int rotation, double rotationOffset)
        {
            switch (rotation)
            {
                case 2: 
                    z += rotationOffset;
                    break;
                case 0: 
                    z -= rotationOffset;
                    break;
                case 3: 
                    x -= rotationOffset;
                    break;
                case 1: 
                    x += rotationOffset;
            }
            this.setPosition(x, y, z);
        }

        @Override
        public double getMountedYOffset()
        {
            return this.height * 0.0D;
        }

        @Override
        protected boolean shouldSetPosAfterLoading()
        {
            return false;
        }

        @Override
        public void onEntityUpdate()
        {
            if (!this.world.isRemote) {
                if ((!isBeingRidden()) || (this.world.isAirBlock(new BlockPos(this.blockPosX, this.blockPosY, this.blockPosZ))))
                {
                    this.setDead();
                    this.world.updateComparatorOutputLevel(this.getPosition(), this.world.getBlockState(this.getPosition()).getBlock());
                }
            }
        }

        @Override
        protected void entityInit()
        {

        }

        @Override
        public void readEntityFromNBT(NBTTagCompound compound)
        {

        }

        @Override
        public void writeEntityToNBT(NBTTagCompound compound)
        {

        }
    }
}
