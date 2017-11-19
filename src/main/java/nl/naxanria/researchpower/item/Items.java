package nl.naxanria.researchpower.item;

import nl.naxanria.nlib.Registy.ItemRegistry;
import nl.naxanria.nlib.item.IItemBase;

public class Items
{
  public static class MachineParts
  {
  
  }
  
  public static void init()
  {
  
  }
  
  private static void addItems(IItemBase... items)
  {
    for (IItemBase item :
      items)
    {
      ItemRegistry.addItem(item);
    }
  }
}
