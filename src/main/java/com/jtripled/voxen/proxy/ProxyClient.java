package com.jtripled.voxen.proxy;

import com.jtripled.voxen.block.IBlockBase;
import com.jtripled.voxen.entity.IEntityRegistration;
import com.jtripled.voxen.item.IItemBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 *
 * @author jtripled
 */
public class ProxyClient extends Proxy
{
    @Override
    public String localize(String unlocalized, Object... args)
    {
        return net.minecraft.client.resources.I18n.format(unlocalized, args);
    }
    
    @Override
    public void registerIgnoredProperties(IBlockBase block)
    {
        ModelLoader.setCustomStateMapper((Block) block, (new StateMap.Builder()).ignore(block.getIgnoredProperties()).build());
    }
    
    @Override
    public void registerItemRenderer(IItemBase item, String variant)
    {
        ModelLoader.setCustomModelResourceLocation((Item) item, 0, new ModelResourceLocation(((Item) item).getRegistryName(), "normal"));
    }

    @Override
    public void registerTileRenderer(IBlockBase block)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(block.getTileClass(), block.createTESR());
    }
    
    @Override
    public void registerEntityRenderer(IEntityRegistration entity)
    {
        RenderingRegistry.registerEntityRenderingHandler(entity.getEntityClass(), entity.getRenderFactory());
    }
}
