package com.jtripled.voxen.asm;

/**
 *
 * @author jtripled
 */
public class Type
{
    /* Primitive types. */
    public static final String BOOL = "Z";
    public static final String VOID = "V";
    public static final String FLOAT = "F";
    public static final String INT = "I";
    
    /* Minecraft objects. */
    public static final String WORLD = "net/minecraft/world/World";
    public static final String BLOCK_STATE = "net/minecraft/block/state/IBlockState";
    public static final String BLOCK_POS = "net/minecraft/util/math/BlockPos";
    public static final String ENTITY_PLAYER = "net/minecraft/entity/player/EntityPlayer";
    public static final String ENUM_HAND = "net/minecraft/util/EnumHand";
    public static final String ENUM_FACING = "net/minecraft/util/EnumFacing";
    
    /* Java objects. */
    public static final String RANDOM = "java/util/Random";
}
