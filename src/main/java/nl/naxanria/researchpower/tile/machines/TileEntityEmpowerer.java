package nl.naxanria.researchpower.tile.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.inventory.TileEntityInventoryBase;
import nl.naxanria.nlib.tile.power.EnergyStorageBase;
import nl.naxanria.nlib.tile.power.IEnergySharingProvider;
import nl.naxanria.nlib.util.EnumHelper;
import nl.naxanria.nlib.util.MathUtil;
import nl.naxanria.nlib.util.StackUtil;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.researchpower.recipe.RecipeEmpowerer;
import nl.naxanria.researchpower.recipe.registry.EmpowererRecipeRegistry;

import java.util.List;

public class TileEntityEmpowerer extends TileEntityInventoryBase implements IEnergySharingProvider
{
  public static final int ENERGY_USE_MAX = 4000;
  public static final int ENERGY_CAPACITY = 400000;
  
  public static final int SLOT_INPUT_MINOR_0 = 0;
  public static final int SLOT_INPUT_MINOR_1 = 1;
  public static final int SLOT_INPUT_MINOR_2 = 2;
  public static final int SLOT_INPUT_MINOR_3 = 3;
  public static final int SLOT_INPUT_MAYOR = 4;
  public static final int SLOT_OUTPUT = 5;
  
  public EnergyStorageBase storage;
  
  public int progress = 0;
  public int totalTime = 1000;
  public int energyPerTick = 0;
  
  public RecipeEmpowerer currentRecipe;
  
  public TileEntityEmpowerer()
  {
    super(6);
    
    storage = new EnergyStorageBase(ENERGY_CAPACITY, ENERGY_USE_MAX * 2, ENERGY_USE_MAX, false);
  }
  
  @Override
  protected TileFlags[] defaultFlags()
  {
    return new TileFlags[]{ TileFlags.KeepNBTData };
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
  
    //Log.info(storage.getStoredPercentage() + " " + storage.getEnergyStored() + " server: " + world.isRemote  + " RF/t" + storage.getMaxReceive());

    if (!world.isRemote)
    {
      
      
      RecipeEmpowerer recipe = EmpowererRecipeRegistry
        .getFromInput
        (
          inventory.getStackInSlot(SLOT_INPUT_MINOR_0),
          inventory.getStackInSlot(SLOT_INPUT_MINOR_1),
          inventory.getStackInSlot(SLOT_INPUT_MINOR_2),
          inventory.getStackInSlot(SLOT_INPUT_MINOR_3),
          inventory.getStackInSlot(SLOT_INPUT_MAYOR)
        );
      
      if (currentRecipe != recipe)
      {
        currentRecipe = recipe;
        progress = 0;
      }
      
      if (currentRecipe == null)
      {
        progress = 0;
      }
  
      if (currentRecipe != null)
      {
        ItemStack craftOutput = currentRecipe.getCraftingOutput();
        ItemStack output = inventory.getStackInSlot(SLOT_OUTPUT);
  
        //Log.info("Updating recipe");
  
        if (!(output.isEmpty() || StackUtil.areItemsEqual(craftOutput, output, true) && output.getCount() < output.getMaxStackSize() - craftOutput.getCount()))
        {
          progress = 0;
          currentRecipe = null;
          return;
        }
        
        totalTime = currentRecipe.getDuration();
        energyPerTick = currentRecipe.getPowerDrain() / totalTime;
        
        if (storage.getEnergyStored() >= energyPerTick)
        {
          storage.extractEnergy(energyPerTick, false);
          
          progress++;
          
          if (progress >= totalTime)
          {
            ItemStack craftingOutput = currentRecipe.getCraftingOutput();
            
            // finish it up
            if (output.isEmpty())
            {
              output = craftingOutput;
              inventory.setStackInSlot(SLOT_OUTPUT, output);
            }
            else
            {
              output.setCount(output.getCount() + craftingOutput.getCount());
              inventory.setStackInSlot(SLOT_OUTPUT, output);
            }
            inventory.extractItem(SLOT_INPUT_MINOR_0, currentRecipe.getMinorInput0().getCount(), false);
            inventory.extractItem(SLOT_INPUT_MINOR_1, currentRecipe.getMinorInput1().getCount(), false);
            inventory.extractItem(SLOT_INPUT_MINOR_2, currentRecipe.getMinorInput2().getCount(), false);
            inventory.extractItem(SLOT_INPUT_MINOR_3, currentRecipe.getMinorInput3().getCount(), false);
            inventory.extractItem(SLOT_INPUT_MAYOR, currentRecipe.getMayorInput().getCount(), false);
            
            progress = 0;
          }
        }
      }
    }
  }

  @Override
  public int getEnergyToShare()
  {
    return 0;
  }
  
  @Override
  public boolean doesShareEnergy()
  {
    return false;
  }
  
  @Override
  public EnumFacing[] getEnergyProvidingSides()
  {
    return EnumHelper.Facing.ALL;
  }
  
  @Override
  public boolean canShareEnergyTo(TileEntity tile)
  {
    return false;
  }
  
  public float getProgressPercent()
  {
    return MathUtil.getPercent(progress, totalTime);
  }
  
  @Override
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    return storage;
  }
  
  @Override
  public boolean validForSlot(int slot, ItemStack stack)
  {
    if (stack == null || stack.isEmpty())
    {
      return false;
    }
    
    if (slot == SLOT_OUTPUT)
    {
      return false;
    }

    if (currentRecipe == null)
    {
      List<RecipeEmpowerer> recipe = EmpowererRecipeRegistry.getFromInput(stack);
      return  !(recipe.isEmpty());
    }
    
    return false;
  }
}
