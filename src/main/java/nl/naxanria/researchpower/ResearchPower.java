package nl.naxanria.researchpower;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import nl.naxanria.researchpower.block.Blocks;
import nl.naxanria.researchpower.block.BlocksRegistry;
import nl.naxanria.researchpower.item.Items;
import nl.naxanria.researchpower.item.ItemsRegistry;
import nl.naxanria.researchpower.network.PacketHandler;
import nl.naxanria.researchpower.proxy.Proxy;
import nl.naxanria.researchpower.recipe.Recipes;

@Mod
(
  modid = ResearchPower.MOD_ID,
  name = ResearchPower.MOD_NAME,
  version = ResearchPower.VERSION
)
public class ResearchPower
{
    public static final String MOD_ID = "researchpower";
    public static final String MOD_NAME = "Research Power";
    public static final String VERSION = "1.0-SNAPSHOT";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static ResearchPower INSTANCE;

    @SidedProxy(clientSide = "nl.naxanria.researchpower.proxy.ClientProxy", serverSide = "nl.naxanria.researchpower.proxy.ServerProxy")
    public static Proxy proxy;

    public static ReseachPowerTab tab = new ReseachPowerTab();

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
      PacketHandler.init();
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
      Recipes.init();
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {

    }

    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler
    {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event)
        {
          Items.init();
          
          ItemsRegistry.registerItems(event.getRegistry());
          BlocksRegistry.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event)
        {
          Blocks.init();
          BlocksRegistry.register(event.getRegistry());
        }
        
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event)
        {
          ItemsRegistry.registerModels();
          BlocksRegistry.registerModels();
        }
    }

    public static class ReseachPowerTab extends CreativeTabs
    {
        public ReseachPowerTab()
        {
            super(MOD_ID);
        }

        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Item.getByNameOrId("ingot_iron"));
        }
    }
}
