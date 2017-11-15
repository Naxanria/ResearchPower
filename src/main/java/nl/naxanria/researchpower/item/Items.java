package nl.naxanria.researchpower.item;

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
      ItemsRegistry.addItem(item);
    }
  }
}
