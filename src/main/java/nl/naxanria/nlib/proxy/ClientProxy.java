package nl.naxanria.nlib.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import nl.naxanria.researchpower.ResearchPower;

public class ClientProxy extends Proxy
{
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ResearchPower.MOD_ID + ":" + id, "inventory"));
    }

    @Override
    public String getLocalization(String unlocalized, Object... args)
    {
        return I18n.format(unlocalized, args);
    }
}
