package nl.naxanria.researchpower.recipe.registry;

import net.minecraft.item.ItemStack;
import nl.naxanria.researchpower.recipe.RecipeEmpowerer;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class EmpowererRecipeRegistry
{
  public static final List<RecipeEmpowerer> EMPOWERER_RECIPES = new ArrayList<>();
  
  @SuppressWarnings("unchecked")
  public static void addAll(RecipeEmpowerer... recipes)
  {
    EMPOWERER_RECIPES.addAll(Arrays.asList(recipes));
  }
  
  public static List<RecipeEmpowerer> getFromInput(ItemStack input)
  {
    List<RecipeEmpowerer> output = new ArrayList<>();
    
    for (RecipeEmpowerer recipe :
      EMPOWERER_RECIPES)
    {
      if (recipe.hasIngredient(input))
      {
        output.add(recipe);
      }
    }
    
    return output;
  }
  
  public static RecipeEmpowerer getFromInput(ItemStack minor0, ItemStack minor1, ItemStack minor2, ItemStack minor3, ItemStack mayor)
  {
    for (RecipeEmpowerer recipe :
      EMPOWERER_RECIPES)
    {
      if (recipe.matches(minor0, minor1, minor2, minor3, mayor))
      {
        return recipe;
      }
    }
    
    return null;
  }
}
