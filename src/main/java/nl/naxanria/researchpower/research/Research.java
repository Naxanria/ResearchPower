package nl.naxanria.researchpower.research;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.HashMap;

public class Research
{
  private static HashMap<String, Research> entries = new HashMap<>();
  
  public final String name;
  public final String description;
  
  public final ArrayList<IRecipe> enableRecipes = new ArrayList<>();
  public final ArrayList<Research> unlockResearch = new ArrayList<>();
  private Research parent;
  
  public ResearchStatus getStatus(EntityPlayer player)
  {
    return ResearchStatus.Disabled;
  }
  
  private Research(String name)
  {
    this(name, "UNKNOWN DESCRIPTION");
  }
  
  private Research(String name, String description)
  {
    this.name = name;
    this.description = description;
  }
  
  public Research getParent()
  {
    return parent;
  }
  
  public static Research createOrUpdate(String name, IRecipe recipe)
  {
    Research research;
    if (entries.containsKey(name))
    {
      research = entries.get(name);
    }
    else
    {
      research = new Research(name);
      entries.put(name, research);
    }
    
    research.enableRecipes.add(recipe);
    
    return research;
  }
  
  public static Research getResearch(String name)
  {
    return entries.getOrDefault(name, null);
  }
}
