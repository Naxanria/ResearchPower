package nl.naxanria.researchpower.recipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import nl.naxanria.nlib.util.StackUtil;
import nl.naxanria.researchpower.research.ResearchDatabase;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class RecipeEmpowerer implements IRecipeBase
{
  protected ItemStack output;
  
  protected ItemStack minorInput0;
  protected ItemStack minorInput1;
  protected ItemStack minorInput2;
  protected ItemStack minorInput3;

  protected ItemStack mayorInput;
  
  protected int powerDrain;
  
  public RecipeEmpowerer(ItemStack output, ItemStack minorInput0, ItemStack minorInput1, ItemStack minorInput2, ItemStack minorInput3, ItemStack mayorInput, int powerDrain)
  {
    this.output = output;
    this.minorInput0 = minorInput0;
    this.minorInput1 = minorInput1;
    this.minorInput2 = minorInput2;
    this.minorInput3 = minorInput3;
    this.mayorInput = mayorInput;
    this.powerDrain = powerDrain;
  }
  
  public boolean hasIngredient(ItemStack input)
  {
    List<ItemStack> inputs = getNeededMinorInputs();
    inputs.add(mayorInput);
    return StackUtil.getPlaceAt(inputs, input, true) != -1;
  }
  
  public boolean matches(ItemStack minorInput0, ItemStack minorInput1, ItemStack minorInput2, ItemStack minorInput3, ItemStack mayorInput)
  {
    if (!StackUtil.canTakeFrom(mayorInput, this.mayorInput))
    {
      return false;
    }
    
    // check minors
    List<ItemStack> stillNeeded = getNeededMinorInputs();
    ItemStack[] items = new ItemStack[]{ minorInput0, minorInput1, minorInput2, minorInput3 };
    for (ItemStack item:
      items)
    {
      int p = StackUtil.getPlaceAt(stillNeeded, item, true);
      if (p == -1)
      {
        return false;
      }
      
      stillNeeded.remove(p);
    }
    
    return true;
  }
  
  @SuppressWarnings("unchecked")
  public List<ItemStack> getNeededMinorInputs()
  {
    return new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] { minorInput0, minorInput1, minorInput2, minorInput3 }));
  }
  
  @SuppressWarnings("ConstantConditions")
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
  
  public int getPowerDrain()
  {
    return powerDrain;
  }
  
  public ItemStack getMinorInput0()
  {
    return minorInput0;
  }
  
  public ItemStack getMinorInput1()
  {
    return minorInput1;
  }
  
  public ItemStack getMinorInput2()
  {
    return minorInput2;
  }
  
  public ItemStack getMinorInput3()
  {
    return minorInput3;
  }
  
  public ItemStack getMayorInput()
  {
    return mayorInput;
  }
}
