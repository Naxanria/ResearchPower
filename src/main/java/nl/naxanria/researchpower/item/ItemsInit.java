package nl.naxanria.researchpower.item;

import nl.naxanria.nlib.registry.ItemRegistry;
import nl.naxanria.nlib.item.ItemBase;
import nl.naxanria.researchpower.item.metals.ItemCopperIngot;

public class ItemsInit
{
  private static boolean init = false;
  
  public static class Metals
  {
    public static final ItemCopperIngot INGOT_COPPER = new ItemCopperIngot();
  }
  
  public static class MachineParts
  {
    public static final ItemBase REDSTONE_CONNECTOR_HORIZONTAL = new ItemBase("redstone_connector_horizontal");
    public static final ItemBase REDSTONE_CONNECTOR_VERTICAL = new ItemBase("redstone_connector_vertical");
    public static final ItemBase REDSTONE_RING = new ItemBase("redstone_ring");
    public static final ItemBase LAPIS_WAFER = new ItemBase("lapis_wafer");
    public static final ItemBase GLOWSTONE_WAFER = new ItemBase("glowstone_wafer");
  }
  
  public static void init(ItemRegistry registry)
  {
    if (init)
    {
      return;
    }
    
    registry.addAll
    (
      Metals.INGOT_COPPER,
      
      MachineParts.REDSTONE_CONNECTOR_HORIZONTAL,
      MachineParts.REDSTONE_CONNECTOR_VERTICAL,
      MachineParts.REDSTONE_RING,
      MachineParts.LAPIS_WAFER,
      MachineParts.GLOWSTONE_WAFER
      

    );
    
    init = true;
  }
}
