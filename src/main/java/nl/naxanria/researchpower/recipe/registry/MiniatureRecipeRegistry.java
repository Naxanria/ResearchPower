package nl.naxanria.researchpower.recipe.registry;

import net.minecraft.block.state.IBlockState;
import nl.naxanria.researchpower.recipe.RecipeMiniature;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class MiniatureRecipeRegistry
{
  public static final List<RecipeMiniature> MINIATURE_RECIPES = new ArrayList<>();
  
  @SuppressWarnings("unchecked")
  public static void addAll(RecipeMiniature... recipes)
  {
    MINIATURE_RECIPES.addAll(Arrays.asList(recipes));
  }
  
  public static RecipeMiniature getRecipeFromInput(IBlockState[] blocks)
  {
    for (RecipeMiniature recipe :
      MINIATURE_RECIPES)
    {
      if (recipe.matches(blocks))
      {
        return recipe;
      }
    }
    
    return null;
  }
  
}
