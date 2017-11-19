package nl.naxanria.nlib.Registy;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import nl.naxanria.nlib.item.IItemBase;

import java.util.ArrayList;

public class ItemRegistry
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
