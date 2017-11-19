package nl.naxanria.nlib.proxy;

import net.minecraft.item.Item;

public abstract class Proxy
{
  private static Proxy instance;
  
  public Proxy()
  {
    instance = this;
  }
  
  private static Proxy getCurrent()
  {
    return instance;
  }
  
  public static void registerItemRender(Item item, int meta, String id)
  {
    getCurrent().registerItemRenderer(item, meta, id);
  }
  
  protected void registerItemRenderer(Item item, int meta, String id)
  {
  }
  
  public static String getLocal(String unlocalized, Object... args)
  {
    return getCurrent().getLocalization(unlocalized, args);
  }
  
  protected abstract String getLocalization(String unlocalized, Object... args);
  
}
