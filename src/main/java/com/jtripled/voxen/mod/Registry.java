package com.jtripled.voxen.mod;

/**
 *
 * @author jtripled
 */
public interface Registry
{
    public default void onRegistryInit() {}
    
    public default void onRegisterBlocks(RegistrationHandler handler) {}
    
    public default void onRegisterItems(RegistrationHandler handler) {}
    
    public default void onRegisterEntities(RegistrationHandler handler) {}
    
    public default void onRegisterModels(RegistrationHandler handler) {}
    
    public default void onRegisterMessages(RegistrationHandler handler) {}
    
    public default void onRegisterGUIs(RegistrationHandler handler) {}
    
    public default void onRegisterSounds(RegistrationHandler handler) {}
    
    public default void onRegisterRecipes(RegistrationHandler handler) {}
}
