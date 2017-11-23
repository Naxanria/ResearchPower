package nl.naxanria.researchpower.tile.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import nl.naxanria.nlib.tile.inventory.IInventoryHolder;
import nl.naxanria.nlib.tile.power.BaseEnergyAcceptor;
import nl.naxanria.nlib.util.EnumHelper;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.researchpower.recipe.PressRecipeRegistry;
import nl.naxanria.researchpower.recipe.RecipePress;

public class TileEntityPress extends BaseEnergyAcceptor implements IInventoryHolder
{
  public static final int CAPACITY = 20000;
  public static final int MAX_USE = 1500;
  
  public static final int SLOT_INPUT = 0;
  public static final int SLOT_OUTPUT = 1;
  
  protected ItemStack inputStack;
  protected ItemStack outputStack;
  
  public ItemStackHandler inventory = new ItemStackHandler(2);
  
  protected int progress = 0;
  protected int totalTime = 1;
  
  protected RecipePress currentRecipe;
  
  public TileEntityPress()
  {
    super(CAPACITY, MAX_USE);
    
    inputStack = ItemStack.EMPTY;
    outputStack = ItemStack.EMPTY;
  }
  
  @Override
  public EnumFacing[] getInventorySides()
  {
    return EnumHelper.Facing.ALL;
  }
  
  @Override
  public IItemHandler getInventory(EnumFacing facing)
  {
    return inventory;
  }
  
  public float getProgressPercentage()
  {
    return  (float) progress / (float) totalTime;
  }
  
  public int getProgress()
  {
    return progress;
  }
  
  public void setProgress(int progress)
  {
    this.progress = progress;
  }
  
  @Override
  public void update()
  {
    super.update();
    // if we have a recipe, check if the stack hasn't changed
    if (currentRecipe != null)
    {
      Log.info("Recipe is not null, checking validity");
      if (inputStack != inventory.getStackInSlot(SLOT_INPUT))
      {
        // stack has changed, check if recipe is still valid
        Log.info("Input stack changed");
        if (!currentRecipe.matches(inputStack, null))
        {
          Log.info("Invalid recipe, resetting it");
          // reset recipe to null, reset progress
          progress = 0;
          currentRecipe = null;
  
          inputStack = inventory.getStackInSlot(SLOT_INPUT);
          outputStack = inventory.getStackInSlot(SLOT_OUTPUT);
        }
      }
      
      if (currentRecipe != null)
      {
        Log.info("Updating progress");
        progress++;
        if (progress >= totalTime)
        {
          Log.info("Finished a product!");
          progress = 0;
          ItemStack result = currentRecipe.getCraftingResult();
      
          inputStack.setCount(inputStack.getCount() - currentRecipe.input.getCount());
      
          if (outputStack.isEmpty())
          {
            outputStack = result;
            inventory.setStackInSlot(SLOT_OUTPUT, outputStack);
          }
          else
          {
            outputStack.setCount(result.getCount() + outputStack.getCount());
          }
          currentRecipe = null;
        }
      }
  
    }
  
    inputStack = inventory.getStackInSlot(SLOT_INPUT);
    outputStack = inventory.getStackInSlot(SLOT_OUTPUT);
    
    // check for recipe
    if (currentRecipe == null)
    {
      RecipePress recipe = PressRecipeRegistry.getRecipeFromInput(inputStack);
      if (recipe != null)
      {
        Log.info("Found a recipe!");
        ItemStack res = recipe.getRecipeOutput();
        if (outputStack.isEmpty() || outputStack.getItem() == res.getItem()  && res.getCount() + outputStack.getCount() <= outputStack.getMaxStackSize())
        {
          Log.info("recipe started");
          currentRecipe = recipe;
          progress = 0;
          totalTime = recipe.duration;
        }
      }
    }
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    compound.setTag("inventory", inventory.serializeNBT());
    compound.setInteger("progress", progress);

    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    inventory.deserializeNBT(compound.getCompoundTag("inventory"));
    progress = compound.getInteger("progress");
    
    if (progress >= totalTime)
    {
      progress = 0;
    }
    
    inputStack = inventory.getStackInSlot(SLOT_INPUT);
    outputStack = inventory.getStackInSlot(SLOT_OUTPUT);
    
    super.readSyncableNBT(compound, type);
  }
}
