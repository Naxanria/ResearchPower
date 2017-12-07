package nl.naxanria.researchpower.recipe.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nl.naxanria.researchpower.recipe.RecipeSanding;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class SandingRecipeRegistry
{
  public static final List<RecipeSanding> SANDING_RECIPES = new ArrayList<>();
  
  @SuppressWarnings("unchecked")
  public static void addAll(RecipeSanding... recipes)
  {
    SANDING_RECIPES.addAll(Arrays.asList(recipes));
  }
  
  public static RecipeSanding getRecipeFromInput(ItemStack input)
  {
    for (RecipeSanding recipe :
      SANDING_RECIPES)
    {
      if (recipe.matches(input))
      {
        return recipe;
      }
    }
    
    return null;
  }
}
