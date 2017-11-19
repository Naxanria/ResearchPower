package nl.naxanria.nlib.Registy;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class RecipeRegistry extends Registry<IRecipe, IForgeRegistry<IRecipe>>
{
  public static RecipeRegistry instance;
  
  public RecipeRegistry()
  {
    instance = this;
  }
  
  @Override
  public void register(IForgeRegistry<IRecipe> registry, IRecipe recipe)
  {
    registry.register(recipe);
  }
}
