package nl.naxanria.researchpower.research;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import nl.naxanria.researchpower.recipe.RecipePress;

import java.util.*;

public class ResearchDatabase
{
  private static HashMap<EntityPlayer, ResearchTree> trees = new HashMap<>();
  
  public static boolean isUnlocked(IRecipe recipe, EntityPlayer player)
  {
    
    
    return true;
  }
  
  public static boolean isUnlocked(RecipePress recipe, EntityPlayer player)
  {
    if (player == null)
    {
      return true;
    }
    
    return true;
  }

  
  
}
