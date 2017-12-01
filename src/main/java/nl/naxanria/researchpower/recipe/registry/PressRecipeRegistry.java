package nl.naxanria.researchpower.recipe.registry;

import net.minecraft.item.ItemStack;
import nl.naxanria.researchpower.recipe.RecipePress;

import java.util.ArrayList;
import java.util.Arrays;

public class PressRecipeRegistry
{
  public static final ArrayList<RecipePress> PRESS_RECIPES = new ArrayList<>();
  
  public static void addAll(RecipePress... recipes)
  {
    PRESS_RECIPES.addAll(Arrays.asList(recipes));
  }
  
  public static RecipePress getRecipeFromInput(ItemStack input)
  {
    for (RecipePress recipe :
      PRESS_RECIPES)
    {
      if (input.getItem() == recipe.input.getItem() && input.getCount() >= recipe.input.getCount())
      {
        return recipe;
      }
    }
    
    return null;
  }
}
