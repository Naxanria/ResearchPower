package nl.naxanria.researchpower.recipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nl.naxanria.nlib.recipe.RecipeBase;
import nl.naxanria.researchpower.containers.ContainerPress;

public class RecipePress
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
    
    return  (input.getItem() == this.input.getItem() && input.getCount() >= this.input.getCount());
  }

  public ItemStack getCraftingResult()
  {
    return output;
  }

  public ItemStack getRecipeOutput()
  {
    return output.copy();
  }
}
