package nl.naxanria.researchpower;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import nl.naxanria.nlib.NMod;
import nl.naxanria.researchpower.block.Blocks;
import nl.naxanria.researchpower.item.Items;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.researchpower.recipe.Recipes;

@Mod
(
  modid = ResearchPower.MOD_ID,
  name = ResearchPower.MOD_NAME,
  version = ResearchPower.VERSION
)
public class ResearchPower extends NMod
{
    public static final String MOD_ID = "researchpower";
    public static final String MOD_NAME = "Research Power";
    public static final String VERSION = "1.0-SNAPSHOT";

//    /**
//     * This is the instance of your mod as created by Forge. It will never be null.
//     */
//    @Mod.Instance(MOD_ID)
//    public static ResearchPower INSTANCE;

    @SidedProxy(clientSide = "nl.naxanria.nlib.proxy.ClientProxy", serverSide = "nl.naxanria.nlib.proxy.ServerProxy")
    public static Proxy proxy;

    public static ResearchPowerTab tab = new ResearchPowerTab();
    
    @Override
    protected Class getBlockClass()
    {
        return Blocks.class;
    }
    
    @Override
    protected Class getItemClass()
    {
        return Items.class;
    }
    
    @Override
    protected Class getRecipeClass()
    {
        return Recipes.class;
    }
    
    @Override
    public String modId()
    {
        return MOD_ID;
    }
    
    @Override
    public String modName()
    {
        return MOD_NAME;
    }
    
    @Override
    public String modVersion()
    {
        return VERSION;
    }
//
//    /**
//     * This is the first initialization event. Register tile entities here.
//     * The registry events below will have fired prior to entry to this method.
//     */
//    @Mod.EventHandler
//    public void preinit(FMLPreInitializationEvent event)
//    {
//      PacketHandler.init();
//    }
//
//    /**
//     * This is the second initialization event. Register custom recipes
//     */
//    @Mod.EventHandler
//    public void init(FMLInitializationEvent event)
//    {
//      Recipes.init();
//    }
//
//    /**
//     * This is the final initialization event. Register actions from other mods here
//     */
//    @Mod.EventHandler
//    public void postinit(FMLPostInitializationEvent event)
//    {
//
//    }
//
//    /**
//     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
//     */
//    @Mod.EventBusSubscriber
//    public static class ObjectRegistryHandler
//    {
//        @SubscribeEvent
//        public static void registerItems(RegistryEvent.Register<Item> event)
//        {
//          Items.init();
//
//          ItemRegistry.registerItems(event.getRegistry());
//          BlockRegistry.registerItemBlocks(event.getRegistry());
//        }
//
//        @SubscribeEvent
//        public static void registerBlocks(RegistryEvent.Register<Block> event)
//        {
//          Blocks.init();
//          BlockRegistry.register(event.getRegistry());
//        }
//
//        @SubscribeEvent
//        public static void registerModels(ModelRegistryEvent event)
//        {
//          ItemRegistry.registerModels();
//          BlockRegistry.registerModels();
//        }
//    }

    public static class ResearchPowerTab extends CreativeTabs
    {
        public ResearchPowerTab()
        {
            super(MOD_ID);
        }

        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Item.getByNameOrId("minecraft:ingot_iron"));
        }
    }
}
