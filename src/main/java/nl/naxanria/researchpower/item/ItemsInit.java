package nl.naxanria.researchpower.item;

import nl.naxanria.nlib.Registy.ItemRegistry;
import nl.naxanria.nlib.item.IItemBase;
import nl.naxanria.nlib.item.ItemBase;
import nl.naxanria.researchpower.item.metals.ItemCopperIngot;

public class ItemsInit
{
  public static class Metals
  {
    public static final ItemCopperIngot ingotCopper = new ItemCopperIngot();
  }
  
  public static class MachineParts
  {
    public static final ItemBase redstoneConnectorHorizontal = new ItemBase("redstone_connector_horizontal");
    public static final ItemBase redstoneConnectorVertical = new ItemBase("redstone_connector_vertical");
    public static final ItemBase redstoneRing = new ItemBase("redstone_ring");
  }
  
  public static void init(ItemRegistry registry)
  {
    registry.addAll
    (
      Metals.ingotCopper,
      
      MachineParts.redstoneConnectorHorizontal,
      MachineParts.redstoneConnectorVertical,
      MachineParts.redstoneRing
    );
  }
}
