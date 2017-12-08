package nl.naxanria.researchpower;


import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.researchpower.tile.machines.generators.TileEntitySolarGenerator;

@Config(modid = ResearchPower.MOD_ID)
@Config.LangKey(ResearchPower.MOD_ID + ".config.title")
@Config.RequiresWorldRestart()
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
  
  public static class SolarPanel
  {
    private int tier;
  
    @Config.Name("Capacity")
    @Config.RangeInt(min = 1)
    @Config.Comment("The total amount the internal storage holds")
    public int capacity;
    
    @Config.Name("Produce")
    @Config.RangeInt(min = 1)
    @Config.Comment("The amount of RF/t it produces")
    public int produce;
    
    @Config.Name("Transfer")
    @Config.RangeInt(min = 1)
    @Config.Comment("The amount of RF/t it can transfer")
    public int transfer;
    
    public SolarPanel(int tier)
    {
      this.tier = tier;
      
      capacity = TileEntitySolarGenerator.getCapacity(tier);
      produce = TileEntitySolarGenerator.getProduce(tier);
      transfer = TileEntitySolarGenerator.getMaxExtract(tier);
    }
  }
  
  public static class SolarPanels
  {
    @Config.Name("Tier 1 Solar panel")
    public static SolarPanel tier1 = new SolarPanel(1);
    @Config.Name("Tier 2 Solar panel")
    public static SolarPanel tier2 = new SolarPanel(2);
    @Config.Name("Tier 3 Solar panel")
    public static SolarPanel tier3 = new SolarPanel(3);
    @Config.Name("Tier 4 Solar panel")
    public static SolarPanel tier4 = new SolarPanel(4);
    @Config.Name("Tier 5 Solar panel")
    public static SolarPanel tier5 = new SolarPanel(5);
    @Config.Name("Tier 6 Solar panel")
    public static SolarPanel tier6 = new SolarPanel(6);
    @Config.Name("Tier 7 Solar panel")
    public static SolarPanel tier7 = new SolarPanel(7);
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
