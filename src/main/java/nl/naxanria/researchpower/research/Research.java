package nl.naxanria.researchpower.research;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import nl.naxanria.nlib.util.collections.ReadonlyList;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.nlib.util.logging.LogColor;
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
  private static List<Research> researches = new ArrayList<>();
  private static ReadonlyList<Research> researchReadonlyList = new ReadonlyList<>(researches);
  
  private static void register(Research research)
  {
    //todo(nax): check for duplicates and stuffs
    
    researches.add(research);
    entries.put(research.name, research);
  }
  
  public static ReadonlyList<Research> getAsList()
  {
    return researchReadonlyList;
  }
  
  private List<ResearchCapability<?>> capabilities = new ArrayList<>();
  
  public final String name;
  public final String description;
  public final int requiredProgress;

  private Research parent;
  private String parentName;
  
  private final ItemStack catalyst;
  
  public Research(String name, String description, int requiredProgress, @Nullable String parent, ItemStack catalyst)
  {
    this.name = name;
    this.description = description;
    this.requiredProgress = requiredProgress;
    this.catalyst = catalyst;
  
    entries.put(name, this);
    
    parentName = parent;
    
    register(this);
  }
  
  private void initialize()
  {
  
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
  
  //todo(nax): proper checking for status and what not.
  public void start(EntityPlayer player)
  {
    ResearchProgress.remove(player);
    
    ResearchProgress progress = new ResearchProgress(this, player, requiredProgress);
    
    ResearchProgress.addProgress(player, progress);
  }
  
  public ItemStack getCatalyst()
  {
    return catalyst.copy();
  }
  
  public void finished(EntityPlayer player)
  {
    ResearchProgress.remove(player);
    Log.info(LogColor.PURPLE, "Finished research: " + name);
  }
}
