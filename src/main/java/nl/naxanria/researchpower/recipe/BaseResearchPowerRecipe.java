package nl.naxanria.researchpower.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nl.naxanria.nlib.recipe.RecipeBase;
import nl.naxanria.researchpower.research.ResearchDatabase;

public class BaseResearchPowerRecipe extends RecipeBase
{
  public BaseResearchPowerRecipe(int size)
  {
    super(size);
  }
  
  @Override
  public boolean doesMatch(InventoryCrafting inv, World world)
  {
    return false;
  }
  
  @Override
  public ItemStack getCraftingResult(InventoryCrafting inv)
  {
    return null;
  }
  
  @Override
  public ItemStack getRecipeOutput()
  {
    return null;
  }
  
  @Override
  public boolean canCraft(InventoryCrafting inv, World world)
  {
    return ResearchDatabase.isUnlocked(this, getCraftingPlayer(inv));
  }
}
