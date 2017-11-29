package nl.naxanria.nlib.util;

import net.minecraft.item.ItemStack;

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
}
