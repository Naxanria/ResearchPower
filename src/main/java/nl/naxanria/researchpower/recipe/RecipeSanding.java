package nl.naxanria.researchpower.recipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RecipeSanding implements IRecipeBase
{
  public final ItemStack input;
  public final ItemStack output;
  public final int sandAmount;
  public final int waterAmount;
  public final int time;
  
  public RecipeSanding(ItemStack input, ItemStack output, int sandAmount, int waterAmount, int time)
  {
    this.input = input;
    this.output = output;
    this.sandAmount = sandAmount;
    this.waterAmount = waterAmount;
    this.time = time;
  }
  
  public boolean matches(ItemStack input)
  {
    return this.input.isItemEqual(input);
  }
  
  @Override
  public boolean canCraft(EntityPlayer player)
  {
    return true;
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
