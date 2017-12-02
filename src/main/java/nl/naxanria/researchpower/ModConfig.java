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
  public static int[] fluidDrumSize = new int[] { 16000, 50000, 160000 };
  
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
