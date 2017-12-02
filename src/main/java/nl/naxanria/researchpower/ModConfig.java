package nl.naxanria.researchpower;


import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nl.naxanria.nlib.util.logging.Log;

@Config(modid = ResearchPower.MOD_ID)
@Config.LangKey(ResearchPower.MOD_ID + ".config.title")
public class ModConfig
{
  static
  {
    Log.info("Mod config loading");
  }
  
  @Config.Comment("Sizes for the fluid drums. (1000 is 1 bucket)")
  @Config.Name("Fluid Drum Size")
  @Config.RequiresWorldRestart()
  public static FluidDrumSize fluidDrumSize = new FluidDrumSize();
  
  public static class FluidDrumSize
  {
    @Config.Name("Tier 1")
    public int tier1 = 16000;
  
    @Config.Name("Tier 2")
    public int tier2 = 50000;
  
    @Config.Name("Tier 3")
    public int tier3 = 160000;
  }
  
  @Mod.EventBusSubscriber
  private static class EventHandler
  {
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
      if (event.getModID().equals(ResearchPower.MOD_ID))
      {
        ConfigManager.sync(ResearchPower.MOD_ID, Config.Type.INSTANCE);
      }
    }
  }
}
