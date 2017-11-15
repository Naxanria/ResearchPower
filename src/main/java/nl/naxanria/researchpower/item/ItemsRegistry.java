package nl.naxanria.researchpower.item;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class ItemsRegistry
{
  private static ArrayList<IItemBase> toRegister = new ArrayList<>();
  
  public static void addItem(IItemBase item)
  {
    toRegister.add(item);
  }
  
  public static void registerItems(IForgeRegistry<Item> registry)
  {
    for (IItemBase item :
      toRegister)
    {
      registry.register(item.getItem());
    }
  }
  
  public static void registerModels()
  {
    for (IItemBase item :
      toRegister)
    {
      item.registerItemModel();
    }
  }
}
