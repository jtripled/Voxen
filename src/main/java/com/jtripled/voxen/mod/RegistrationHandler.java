package com.jtripled.voxen.mod;

import com.google.common.collect.Lists;
import com.jtripled.voxen.Voxen;
import com.jtripled.voxen.block.IBlockBase;
import com.jtripled.voxen.gui.GUIHolder;
import com.jtripled.voxen.item.IItemBase;
import com.jtripled.voxen.network.Network;
import com.jtripled.voxen.proxy.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import com.jtripled.voxen.entity.IEntityRegistration;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import com.jtripled.voxen.entity.IMobRegistration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.registries.IForgeRegistryModifiable;

/**
 *
 * @author jtripled
 */
public class RegistrationHandler implements IGuiHandler
{
    private final ModBase mod;
    private final Registry registry;
    private final Network network;
    private final Proxy proxy;
    private final List<IBlockBase> blocks;
    private final List<IItemBase> items;
    private final List<IEntityRegistration> entities;
    private int currentGUIID;
    protected final Map<Integer, GUIHolder> guis;
    RegistryEvent.Register<IRecipe> recipeRegistryEvent;
    
    public RegistrationHandler(ModBase mod)
    {
        this.mod = mod;
        this.registry = mod.getRegistry();
        this.network = mod.getNetwork() == null ? Voxen.NETWORK : mod.getNetwork();
        this.proxy = mod.getProxy() == null ? Voxen.PROXY : mod.getProxy();
        this.blocks = new ArrayList<>();
        this.items = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.currentGUIID = 0;
        this.guis = new HashMap<>();
    }
    
    /*
     * Utility methods to register stuff.
     */
    
    public final void registerBlock(IBlockBase block)
    {
        blocks.add(block);
    }
    
    public final void registerItem(IItemBase item)
    {
        items.add(item);
    }
    
    public final void registerEntity(IEntityRegistration entity)
    {
        entities.add(entity);
    }
    
    public final <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> message, Side side)
    {
        network.registerMessage(handler, message, side);
    }
    
    public final void registerGUI(GUIHolder holder)
    {
        guis.put(currentGUIID++, holder);
    }
    
    /*
     * Registration events.
     */
    
    protected final void onPreInit(FMLPreInitializationEvent event)
    {
        
    }
    
    protected final void onInit(FMLInitializationEvent event)
    {
        registry.onRegisterMessages(this);
        registry.onRegisterGUIs(this);
        
        // Register block GUIs.
        blocks.forEach((IBlockBase block) -> {
            if (block instanceof GUIHolder)
                registerGUI((GUIHolder) block);
        });
        
        // Register entity spawns.
        entities.forEach((IEntityRegistration entity) -> {
            if (entity instanceof IMobRegistration)
            {
                IMobRegistration e = (IMobRegistration) entity;
                List<Biome> biomes = Lists.newArrayList();
                for (Biome biome : Biome.REGISTRY)
                    if (e.canSpawn(biome))
                        biomes.add(biome);
                if (!biomes.isEmpty())
                    EntityRegistry.addSpawn(e.getEntityClass(), e.getSpawnRate(), e.getSpawnMin(), e.getSpawnMax(), e.getSpawnType(), biomes.toArray(new Biome[biomes.size()]));
            }
        });
    }
    
    protected final void onPostInit(FMLPostInitializationEvent event)
    {
        
    }
    
    @SubscribeEvent
    protected final void onRegisterBlocks(RegistryEvent.Register<Block> event)
    {
        registry.onRegisterBlocks(this);
        
        // Register blocks.
        blocks.forEach((IBlockBase block) -> {
            if (!block.isRegistered())
            {
                block.setRegistered();
                event.getRegistry().register((Block) block);
                if (block.hasTile())
                    GameRegistry.registerTileEntity(block.getTileClass(), ((Block) block).getRegistryName().toString());
                if (block.hasIgnoredProperties())
                    proxy.registerIgnoredProperties(block);
            }
        });
    }
    
    @SubscribeEvent
    protected final void onRegisterEntities(RegistryEvent.Register<EntityEntry> event)
    {
        registry.onRegisterEntities(this);
        
        // Register entities.
        entities.forEach((IEntityRegistration entity) -> {
            EntityEntry entry = new EntityEntry(entity.getEntityClass(), entity.getName());
            entry.setRegistryName(entity.getResourceLocation());
            if (entity instanceof IMobRegistration)
            {
                IMobRegistration e = (IMobRegistration) entity;
                if (e.getEggPrimary() != -1 && e.getEggSecondary() != -1)
                    entry.setEgg(new EntityEggInfo(entity.getResourceLocation(), e.getEggPrimary(), e.getEggSecondary()));
            }
            event.getRegistry().register(entry);
        });
    }
    
    @SubscribeEvent
    public void onRegisterSounds(RegistryEvent.Register<SoundEvent> event)
    {
        registry.onRegisterSounds(this);
        
        // Register entity sounds.
        entities.forEach((IEntityRegistration entity) -> {
            if (entity.getSounds() != null)
                event.getRegistry().registerAll(entity.getSounds());
        });
    }
    
    @SubscribeEvent
    protected final void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        registry.onRegisterItems(this);
        
        // Register item blocks.
        blocks.forEach((IBlockBase block) -> {
            if (block.hasItem()) event.getRegistry().register((Item) block.getItem());
        });
        
        // Register items.
        items.forEach((IItemBase item) -> {
            if (!item.isRegistered())
            {
                item.setRegistered();
                event.getRegistry().register((Item) item);
            }
        });
    }
    
    @SubscribeEvent
    protected final void onRegisterModels(ModelRegistryEvent event)
    {
        registry.onRegisterModels(this);
        
        // Register entity renderers.
        entities.forEach((IEntityRegistration entity) -> {
            proxy.registerEntityRenderer(entity);
        });
        
        // Register block renderers (item and/or TESR).
        blocks.forEach((IBlockBase block) -> {
            if (block.hasItem()) proxy.registerItemRenderer(block.getItem(), block.hasUniqueInventoryModel() ? "inventory" : "normal");
            if (block.hasTESR()) proxy.registerTileRenderer(block);
        });
        
        // Register item renderers.
        items.forEach((IItemBase item) -> {
            proxy.registerItemRenderer(item, "normal");
        });
    }
    
    @SubscribeEvent
    protected final void onRegisterRecipes(RegistryEvent.Register<IRecipe> event)
    {
        recipeRegistryEvent = event;
        registry.onRegisterRecipes(this);
        recipeRegistryEvent = null;
    }
    
    public final void removeRecipe(String modID, String name)
    {
        if (recipeRegistryEvent != null)
        {
            ResourceLocation resource = new ResourceLocation(modID, name);
            IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) recipeRegistryEvent.getRegistry();
            modRegistry.remove(resource);
        }
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        return guis.containsKey(id)
                ? guis.get(id).getServerGUI(player, world, x, y, z)
                : null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        return guis.containsKey(id)
                ? guis.get(id).getClientGUI(player, world, x, y, z)
                : null;
    }
}
