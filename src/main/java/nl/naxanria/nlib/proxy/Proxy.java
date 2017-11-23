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

  public static void registerItemRenderWithVariant(Item item, int meta, String id, String variant)
  {
    getCurrent().registerItemRendererWithVariant(item, meta, id, variant);
  }

  public void registerItemRendererWithVariant(Item item, int meta, String id, String variant)
  {
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
