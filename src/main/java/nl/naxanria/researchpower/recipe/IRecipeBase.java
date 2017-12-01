package nl.naxanria.researchpower.recipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IRecipeBase
{
  public boolean canCraft(EntityPlayer player);
  
  public ItemStack getCraftingResult();
  
  public ItemStack getCraftingOutput();
}
