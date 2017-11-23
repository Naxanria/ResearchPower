package nl.naxanria.researchpower.recipe;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nl.naxanria.nlib.recipe.RecipeBase;
import nl.naxanria.researchpower.containers.ContainerPress;

public class RecipePress extends RecipeBase
{
  private final ItemStack input;
  private final ItemStack output;
  private final int duration;
  
  public RecipePress(ItemStack input, ItemStack output, int duration)
  {
    super(1);
    this.input = input;
    this.output = output;
    this.duration = duration;
  }
  
  public int getDuration()
  {
    return duration;
  }
  
  @Override
  public boolean doesMatch(InventoryCrafting inv, World world)
  {
    Container container = getCraftingContainer(inv);
    if (container instanceof ContainerPress)
    {
      // check for researched recipe or not
      
      // check if inv contains correct item
      
      ItemStack stack = inv.getStackInSlot(0);
      
      return  (stack.getItem() == input.getItem() && stack.getCount() >= input.getCount());
    }
    return false;
  }
  
  @Override
  public ItemStack getCraftingResult(InventoryCrafting inv)
  {
    return output;
  }
  
  @Override
  public ItemStack getRecipeOutput()
  {
    return output.copy();
  }
}
