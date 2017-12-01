package nl.naxanria.researchpower.tile.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.inventory.IInventoryHolder;
import nl.naxanria.nlib.tile.power.TileEntityEnergyAcceptor;
import nl.naxanria.nlib.util.EnumHelper;
import nl.naxanria.researchpower.recipe.registry.PressRecipeRegistry;
import nl.naxanria.researchpower.recipe.RecipePress;

public class TileEntityPress extends TileEntityEnergyAcceptor implements IInventoryHolder
{
  public static final int CAPACITY = 25000;
  public static final int MAX_USE = 500;
  
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
    
    enableFlag(TileFlags.DropInventory);
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
      if (!currentRecipe.matches(inputStack, null))
      {
        progress = 0;
        currentRecipe = null;
        return;
      }
  
      // check for enough power
      int sim = storage.extractEnergy(MAX_USE, true);
      if (sim != MAX_USE)
      {
        return;
      }
  
      storage.extractEnergy(MAX_USE, false);
  
      progress++;
      if (progress >= totalTime)
      {
    
        progress = 0;
        ItemStack result = currentRecipe.getCraftingResult();
    
        inputStack.setCount(inputStack.getCount() - currentRecipe.input.getCount());
    
        if (outputStack.isEmpty())
        {
          outputStack = result;
          inventory.setStackInSlot(SLOT_OUTPUT, outputStack);
        } else
        {
          outputStack.setCount(result.getCount() + outputStack.getCount());
      
          inventory.setStackInSlot(SLOT_OUTPUT, outputStack);
        }
        currentRecipe = null;
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
        ItemStack res = recipe.getCraftingResult();
        if (outputStack.isEmpty() || outputStack.getItem() == res.getItem()  && res.getCount() + outputStack.getCount() <= outputStack.getMaxStackSize())
        {
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
  
  @Override
  public IItemHandler[] getAllInventories()
  {
    return new IItemHandler[] { inventory };
  }
}
