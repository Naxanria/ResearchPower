package nl.naxanria.nlib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nl.naxanria.nlib.Registy.BlockRegistry;
import nl.naxanria.nlib.Registy.ItemRegistry;
import nl.naxanria.nlib.Registy.RecipeRegistry;
import nl.naxanria.nlib.network.PacketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class NMod
{
  protected static NMod instance;
  public static Logger LOGGER;
  
  public NMod()
  {
    instance = this;
    LOGGER = LogManager.getLogger(modId());
    
    LOGGER.info("Created mod instance - " + getModName() + " " + getVersion());
  }
  
  public static String getModId()
  {
    return instance.modId();
  }
  
  public static String getModName()
  {
    return instance.modName();
  }
  
  public static String getVersion()
  {
    return instance.modVersion();
  }
  
  protected abstract Class getBlockClass();
  protected abstract Class getItemClass();
  protected abstract Class getRecipeClass();
  
  public abstract String modId();
  public abstract String modName();
  public abstract String modVersion();
  
  protected BlockRegistry blockRegistry = new BlockRegistry();
  protected ItemRegistry itemRegistry = new ItemRegistry();
  protected RecipeRegistry recipeRegistry = new RecipeRegistry();
  
  /**
   * This is the first initialization event. Register tile entities here.
   * The registry events below will have fired prior to entry to this method.
   */
  @Mod.EventHandler
  public final void preInit(FMLPreInitializationEvent event)
  {
    PacketHandler.init();
    
    onPreInit(event);
  }
  
  protected void onPreInit(FMLPreInitializationEvent event)
  { }
  /**
   * This is the second initialization event. Register custom recipes
   */
  @Mod.EventHandler
  public final void init(FMLInitializationEvent event)
  {
    initRecipeClass();
    
    onInit(event);
  }
  
  protected void onInit(FMLInitializationEvent event)
  { }
  
  /**
   * This is the final initialization event. Register actions from other mods here
   */
  @Mod.EventHandler
  public final void postInit(FMLPostInitializationEvent event)
  {
    onPostInit(event);
  }
  
  protected void onPostInit(FMLPostInitializationEvent event)
  { }
  
  private static void initClass(Class<?> c)
  {
    initClass(c, null);
  }
  
  private static <T> void initClass(Class<?> c, T arg)
  {
    try
    {
      Class argClass = (arg != null) ? arg.getClass() : null;
      Method method = c.getMethod("init", argClass);
      method.invoke(null, arg);
    }
    catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
    {
      LOGGER.error(c.getName() + " has no init method!");
      e.printStackTrace();
      e.printStackTrace();
    }
  }
  
  private static void initItemClass()
  {
    initClass(instance.getItemClass(), instance.itemRegistry);
  }
  
  private static void initBlockClass()
  {
    initClass(instance.getBlockClass(), instance.blockRegistry);
  }
  
  private static void initRecipeClass()
  {
    initClass(instance.getRecipeClass(), instance.recipeRegistry);
  }
  
  @Mod.EventBusSubscriber
  public static class ObjectRegistryHandler
  {
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
      initItemClass();
      
      instance.itemRegistry.registerAll(event.getRegistry());
      instance.blockRegistry.registerItemBlocks(event.getRegistry());
    }
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
      initBlockClass();
      instance.blockRegistry.registerAll(event.getRegistry());
    }
    
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
      instance.itemRegistry.registerModels();
      instance.blockRegistry.registerModels();
    }
  }
}
