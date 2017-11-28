package nl.naxanria.researchpower.research;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import nl.naxanria.researchpower.recipe.RecipePress;
import nl.naxanria.researchpower.research.capabilities.ResearchCapability;
import nl.naxanria.researchpower.research.capabilities.ResearchCapabilityPressRecipeUnlock;
import nl.naxanria.researchpower.research.capabilities.ResearchCapabilityRecipeUnlock;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Research
{
  private static HashMap<String, Research> entries = new HashMap<>();
  
  private List<ResearchCapability<?>> capabilities = new ArrayList<>();
  
  public final String name;
  public final String description;

  private Research parent;
  
  private String parentName;
  
  public Research(String name, String description, @Nullable String parent)
  {
    this.name = name;
    this.description = description;
    
    entries.put(name, this);
    
    parentName = parent;
  }
  
  public Research addCapability(ResearchCapability<?> capability)
  {
    capabilities.add(capability);
    
    return this;
  }
  
  public List<ResearchCapability<?>> getCapabilities()
  {
    return capabilities;
  }
  
  @SuppressWarnings("unchecked")
  public <T extends ResearchCapability> T getCapability(ResearchCapability<T> capability)
  {
    if (capability.equals(ResearchCapabilityRecipeUnlock.CAPABILITY))
    {
      return (T) getRecipeUnlocks();
    }
    
    if (capability.equals(ResearchCapabilityPressRecipeUnlock.CAPABILITY))
    {
      return (T) getPressRecipeUnlocks();
    }
    
    return null;
  }
  
  public List<IRecipe> getRecipeUnlocks()
  {
    return new ArrayList<>();
  }
  
  public List<RecipePress> getPressRecipeUnlocks()
  {
    return new ArrayList<>();
  }
  
  public boolean hasCapability(ResearchCapability capability)
  {
    return getCapability(capability) != null;
  }
  
  public ResearchStatus getStatus(EntityPlayer player)
  {
    return ResearchStatus.Disabled;
  }
  
  public static Research getResearch(String name)
  {
    return entries.getOrDefault(name, null);
  }
}
