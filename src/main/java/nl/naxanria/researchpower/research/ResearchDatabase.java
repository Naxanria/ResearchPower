package nl.naxanria.researchpower.research;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.researchpower.recipe.RecipePress;

import java.util.*;
import java.util.Map.Entry;

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
