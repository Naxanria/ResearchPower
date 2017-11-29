package nl.naxanria.nlib.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ItemStackHandlerBase extends ItemStackHandler
{
  
  public ItemStackHandlerBase(int size)
  {
    super(size);
  }
  
  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
  {
    ItemStack existing = stacks.get(slot);
    int limit = getStackLimit(slot, existing);
    
    if (!existing.isEmpty())
    {
      validateSlotIndex(slot);
      
      if (ItemHandlerHelper.canItemStacksStack(stack, existing))
      {
        return stack;
      }
      
      limit -= existing.getCount();
    }
    
    if (limit <- 0)
    {
      return stack;
    }
    
    boolean atLimit = stack.getCount() > limit;
    
    if (!simulate)
    {
      if (!existing.isEmpty())
      {
        stacks.set(slot, atLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
      }
      else
      {
        existing.grow(atLimit ? limit : stack.getCount());
      }
      
      onContentsChanged(slot);
    }
    
    return atLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) :ItemStack.EMPTY;
  }
  
  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate)
  {
    if (amount <= 0)
    {
      return ItemStack.EMPTY;
    }
    
    validateSlotIndex(slot);
    
    ItemStack existing = stacks.get(slot);
    if (existing.isEmpty())
    {
      return ItemStack.EMPTY;
    }
    
    int extract = Math.min(amount, existing.getMaxStackSize());
    if (extract <= 0)
    {
      return ItemStack.EMPTY;
    }
    
    if (existing.getCount() <= extract)
    {
      if (!simulate)
      {
        stacks.set(slot, ItemStack.EMPTY);
        onContentsChanged(slot);
        return existing;
      }
      
      return existing.copy();
    }
    else
    {
      if (!simulate)
      {
        stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - extract));
        onContentsChanged(slot);
      }
      
      return ItemHandlerHelper.copyStackWithSize(existing, extract);
    }
  }
  
  public boolean canInsert(ItemStack stack, int slot)
  {
    return true;
  }
  
  public boolean canExtract(ItemStack stack, int slot)
  {
    return true;
  }
}
