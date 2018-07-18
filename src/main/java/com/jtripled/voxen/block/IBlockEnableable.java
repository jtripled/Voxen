package com.jtripled.voxen.block;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;

/**
 *
 * @author jtripled
 */
public interface IBlockEnableable
{
    public static final PropertyBool ENABLED = PropertyBool.create("enabled");
    
    public default PropertyBool getEnableableProperty()
    {
        return ENABLED;
    }
    
    public default IBlockState addEnableableDefaultStates(IBlockState defaultState)
    {
        return defaultState.withProperty(ENABLED, true);
    }
    
    public default boolean isEnabled(IBlockState state)
    {
        return state.getValue(ENABLED);
    }
    
    public default IBlockState getEnableableStateFromMeta(IBlockState state, int meta)
    {
        return state.withProperty(ENABLED, (meta & 8) != 8);
    }
    
    public default int getEnableableMetaFromState(IBlockState state)
    {
        return state.getValue(ENABLED) ? 8 : 0;
    }
}
