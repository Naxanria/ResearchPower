package nl.naxanria.nlib.Registy;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import nl.naxanria.nlib.item.IItemBase;

public class ItemRegistry extends Registry<IItemBase, IForgeRegistry<Item>>
{
  @Override
  public void register(IForgeRegistry<Item> registry, IItemBase item)
  {
    registry.register(item.getItem());
  }
  
  public void registerModels()
  {
    for (IItemBase item :
      toRegister)
    {
      item.registerItemModel();
    }
  }
}
