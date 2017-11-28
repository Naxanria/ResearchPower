package nl.naxanria.researchpower.research;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import nl.naxanria.researchpower.recipe.RecipePress;
import nl.naxanria.researchpower.research.capabilities.ResearchCapability;
import nl.naxanria.researchpower.research.capabilities.ResearchCapabilityPressRecipeUnlock;
import nl.naxanria.researchpower.research.capabilities.ResearchCapabilityRecipeUnlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResearchTree
{
  private EntityPlayer player;
  
  private HashMap<ResearchCapability<?>, List<Research>> capabilityResearchMap = new HashMap<>();
  private List<Research> researches = new ArrayList<>();
  
  public void addResearch(Research research)
  {
    for (ResearchCapability<?> capability :
      research.getCapabilities())
    {
      if (!capabilityResearchMap.containsKey(capability))
      {
        capabilityResearchMap.put(capability, new ArrayList<>());
      }
      
      capabilityResearchMap.get(capability).add(research);
    }
    
    researches.add(research);
  }
  
  public boolean isRecipeUnlocked(IRecipe recipe)
  {
    List<Research> researches = capabilityResearchMap.getOrDefault(ResearchCapabilityRecipeUnlock.CAPABILITY, new ArrayList<>());
  
    for (Research r :
      researches)
    {
      if (r.getStatus(player) == ResearchStatus.Completed)
      {
        if (r.getRecipeUnlocks().contains(recipe))
        {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public boolean isRecipeUnlocked(RecipePress recipe)
  {
    List<Research> researches = capabilityResearchMap.getOrDefault(ResearchCapabilityPressRecipeUnlock.CAPABILITY, new ArrayList<>());
    
    for (Research r :
      researches)
    {
      if (r.getStatus(player) == ResearchStatus.Completed)
      {
        if (r.getPressRecipeUnlocks().contains(recipe))
        {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public List<Research> getCompletedResearch()
  {
    return getResearchWithStatus(ResearchStatus.Completed);
  }
  
  public List<Research> getAvailableResearch()
  {
    return getResearchWithStatus(ResearchStatus.Enabled);
  }
  
  public List<Research> getDisabledResearch()
  {
    return getResearchWithStatus(ResearchStatus.Disabled);
  }
  
  public List<Research> getCurrentResearch()
  {
    return getResearchWithStatus(ResearchStatus.Researching);
  }
  
  @SuppressWarnings("unchecked")
  protected List<Research> getResearchWithStatus(ResearchStatus status)
  {
    List out = new ArrayList();
    for (Research research :
      researches)
    {
      if (research.getStatus(player) == status)
      {
        out.add(research);
      }
    }
    
    return out;
  }
  
  
}
