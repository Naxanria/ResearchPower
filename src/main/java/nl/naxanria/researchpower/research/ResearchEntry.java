package nl.naxanria.researchpower.research;

import net.minecraft.item.crafting.IRecipe;

import java.util.Map.Entry;

public class ResearchEntry implements Entry<IRecipe, Research>
{
  private Research research;
  private IRecipe recipe;
  
  public ResearchEntry(Research research, IRecipe recipe)
  {
    this.research = research;
    this.recipe = recipe;
  }
  
  @Override
  public IRecipe getKey()
  {
    return recipe;
  }
  
  @Override
  public Research getValue()
  {
    return research;
  }
  
  @Override
  public Research setValue(Research value)
  {
    return research;
  }
}
