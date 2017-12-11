package nl.naxanria.researchpower.tile.machines.furnace;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import nl.naxanria.nlib.inventory.ItemStackHandlerBase;
import nl.naxanria.nlib.util.ItemUtil;
import nl.naxanria.nlib.util.MathUtil;

public class SmeltModule
{
  public static final int BASE_SMELT_TIME = (ItemUtil.getBurnValue(Items.COAL) / 8);
  
  public ItemStackHandlerBase input = new ItemStackHandlerBase(1);
  public ItemStackHandlerBase output = new ItemStackHandlerBase(1);
  
  public int progress;
  public int total = 1;
  public ItemStack burning = null;
  
  public SmeltModule(float speed)
  {
    setSmeltSpeed(speed);
  }
  
  public void setSmeltSpeed(float speed)
  {
    total = Math.round(BASE_SMELT_TIME / speed);
  }
  
  public void update()
  {
    if (burning != null)
    {
      
      progress++;
      if (progress >= total)
      {
        // we finished
        ItemStack result = getBurningResult(burning);
        ItemStack out = output.getStackInSlot(0);
        if (out.isEmpty())
        {
          output.setStackInSlot(0, result.copy());
        }
        else
        {
          out.grow(result.getCount());
          output.setStackInSlot(0, out);
        }
        
        progress = 0;
        burning = null;
      }
    }
    
    // see if we can start next item to burn
    if (burning == null)
    {
      ItemStack in = input.getStackInSlot(0);
      if (in.isEmpty())
      {
        
        return;
      }
      
      ItemStack result = getBurningResult(in);
      if (result.isEmpty())
      {
        
        return;
      }
      ItemStack out = output.getStackInSlot(0);
     
      if (out.isEmpty() || out.isItemEqual(result) && out.getCount() + result.getCount() <= out.getMaxStackSize())
      {
        // we start
        progress = 0;
        
        burning = new ItemStack(in.getItem());
        in.shrink(1);
        input.setStackInSlot(0, in);
      }
    }
  }
  
  public SmeltModule writeToNBTCompound(NBTTagCompound compound)
  {
    compound.setInteger("Progress", progress);
    compound.setInteger("Total", total);
    
    compound.setTag("Input", input.serializeNBT());
    compound.setTag("Output", output.serializeNBT());
    
    if (burning != null)
    {
      compound.setTag("Burn", burning.writeToNBT(new NBTTagCompound()));
    }
    
    return this;
  }
  
  public SmeltModule readFromNBTCompound(NBTTagCompound compound)
  {
    progress = compound.getInteger("Progress");
    total = compound.getInteger("Total");
    
    input.deserializeNBT(compound.getCompoundTag("Input"));
    output.deserializeNBT(compound.getCompoundTag("Output"));
    
    burning = ItemStack.EMPTY;
    burning.deserializeNBT(compound.getCompoundTag("Burn"));
    if (burning.isEmpty())
    {
      burning = null;
    }
    
    return this;
  }
  
  public float getProgressPercentage()
  {
    return MathUtil.getPercent(progress, total);
  }
  
  public boolean isBurning()
  {
    return burning != null && !burning.isEmpty();
  }
  
  public static boolean isValidInput(ItemStack stack)
  {
    return getBurningResult(stack) != ItemStack.EMPTY;
  }
  
  public static ItemStack getBurningResult(ItemStack stack)
  {
    return FurnaceRecipes.instance().getSmeltingResult(stack);
  }
}
