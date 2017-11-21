package nl.naxanria.researchpower.item;

import nl.naxanria.nlib.Registy.ItemRegistry;
import nl.naxanria.nlib.item.IItemBase;
import nl.naxanria.researchpower.item.metals.ItemCopperIngot;

public class Items
{
  public static class Metals
  {
    public static final ItemCopperIngot ingotCopper = new ItemCopperIngot();
  }
  
  public static class MachineParts
  {
  
  }
  
  public static void init(ItemRegistry registry)
  {
    registry.addAll
    (
      Metals.ingotCopper
    );
  }
}
