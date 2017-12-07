package nl.naxanria.nlib.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class StackUtil
{
  public static ItemStack changeStackSize(ItemStack stack, int change)
  {
    return changeStackSize(stack, change, true);
  }
  
  public static ItemStack changeStackSize(ItemStack stack, int change, boolean makeEmpty)
  {
    int nsize = stack.getCount() + change;
    if (nsize > stack.getMaxStackSize())
    {
      nsize = stack.getMaxStackSize();
    }
    else if (nsize <= 0)
    {
      return ItemStack.EMPTY;
    }
  
    return setStackSize(stack, nsize, makeEmpty);
  }
  
  public static ItemStack setStackSize(ItemStack stack, int newSize)
  {
    return setStackSize(stack, newSize, true);
  }
  
  public static ItemStack setStackSize(ItemStack stack, int newSize, boolean makeEmpty)
  {
    if (newSize == 0 && makeEmpty)
    {
      return ItemStack.EMPTY;
    }
  
    stack.setCount(newSize);
    
    return stack;
  }
  
  public static boolean canTakeFrom(ItemStack from, ItemStack toTake)
  {
    return  (from.getItem().equals(toTake.getItem()) && toTake.getCount() <= from.getCount());
  }
  
  public static int getPlaceAt(List<ItemStack> stacks, ItemStack search, boolean oreDict)
  {
    if (stacks != null && stacks.size() > 0 && search != null && !search.isEmpty())
    {
      for (int i = 0; i < stacks.size(); i++)
      {
        ItemStack stack = stacks.get(i);
        if (areItemsEqual(stack, search, oreDict))
        {
          return i;
        }
      }
    }
    
    return -1;
  }
  
  public static boolean areItemsEqual(ItemStack a, ItemStack b, boolean oreDict)
  {
    return !a.isEmpty() && !b.isEmpty() &&
    (
      a.isItemEqual(b) ||
      (
        oreDict && a.getItem() == b.getItem() &&
        (
          a.getItemDamage() == OreDictionary.WILDCARD_VALUE || b.getItemDamage() == OreDictionary.WILDCARD_VALUE
        )
      )
    );
  }
  
  public static boolean matchesItemAndHasSpace(ItemStack toInsert, ItemStack stack, boolean oreDict)
  {
    return areItemsEqual(toInsert, stack, oreDict) && toInsert.getCount() + stack.getCount() <= stack.getMaxStackSize();
  }
}
