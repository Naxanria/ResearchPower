package nl.naxanria.nlib.registry;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import nl.naxanria.nlib.item.IItemBase;
import nl.naxanria.nlib.item.ItemBase;

public class ItemRegistry extends Registry<IItemBase, IForgeRegistry<Item>>
{
  @Override
  public void register(IForgeRegistry<Item> registry, IItemBase item)
  {
    registry.register(item.getItem());
    
    if (item instanceof ItemBase)
    {
      if (((ItemBase) item).needsNBTClearRecipe())
      {
        RecipeRegistry.ITEM_NBT_CLEAR.add((Item) item);
      }
    }
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
