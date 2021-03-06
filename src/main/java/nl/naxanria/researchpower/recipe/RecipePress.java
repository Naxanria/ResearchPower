package nl.naxanria.researchpower.recipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import nl.naxanria.researchpower.research.ResearchDatabase;

public class RecipePress implements IRecipeBase
{
  public final ItemStack input;
  public final ItemStack output;
  public final int duration;
  
  public RecipePress(ItemStack input, ItemStack output, int duration)
  {
    this.input = input;
    this.output = output;
    this.duration = duration;
  }

  public boolean matches(ItemStack input, EntityPlayer player)
  {
    // check research
    return input.getItem() == this.input.getItem() &&
            input.getCount() >= this.input.getCount();
  }
  
  @Override
  public boolean canCraft(EntityPlayer player)
  {
    return ResearchDatabase.isUnlocked(this, player);
  }
  
  @Override
  public ItemStack getCraftingResult()
  {
    return output;
  }
  
  @Override
  public ItemStack getCraftingOutput()
  {
    return output.copy();
  }
}
