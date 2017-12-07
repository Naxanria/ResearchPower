package nl.naxanria.researchpower.tile.machines;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import nl.naxanria.nlib.inventory.ItemStackHandlerBase;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.fluid.FluidFilteredTank;
import nl.naxanria.nlib.tile.inventory.IInventoryHolder;
import nl.naxanria.nlib.tile.power.EnergyStorageBase;
import nl.naxanria.nlib.util.EnumHelper;
import nl.naxanria.nlib.util.Numbers;
import nl.naxanria.nlib.util.StackUtil;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.nlib.util.logging.LogColor;
import nl.naxanria.researchpower.recipe.RecipeSanding;
import nl.naxanria.researchpower.recipe.registry.SandingRecipeRegistry;

import java.util.HashMap;

public class TileEntityMachineSanding extends TileEntityBase implements IInventoryHolder
{
  private static final HashMap<Block, Integer> SAND_AMOUNT_MAP = new HashMap<>();
  
  public static int getSandAmount(ItemStack stack)
  {
    
    Block b = Block.getBlockFromItem(stack.getItem());
    if (b == Blocks.AIR)
    {
      return 0;
    }
    
    return getSandAmount(b);
  }
  
  public static int getSandAmount(Block block)
  {
    return SAND_AMOUNT_MAP.getOrDefault(block, 0);
  }
  
  static
  {
    SAND_AMOUNT_MAP.put(Blocks.SAND, 10);
    SAND_AMOUNT_MAP.put(Blocks.SANDSTONE, 40);
    SAND_AMOUNT_MAP.put(Blocks.SOUL_SAND, 6);
    SAND_AMOUNT_MAP.put(Blocks.RED_SANDSTONE, 40);
    SAND_AMOUNT_MAP.put(Blocks.GRAVEL, 2);
    SAND_AMOUNT_MAP.put(Blocks.DIRT, 5);
  }
  
  public static void registerSandAmount(Block block, int amount)
  {
    SAND_AMOUNT_MAP.put(block, amount);
  }
  
  public static final int SLOT_SAND = 0;
  public static final int SLOT_INPUT = 1;
  public static final int SLOT_OUTPUT = 2;
  
  public EnergyStorageBase storage = new EnergyStorageBase(50000, 1200, Numbers.billion(2), false);
  public ItemStackHandlerBase sandInput = new ItemStackHandlerBase(1);
  public ItemStackHandlerBase itemInput = new ItemStackHandlerBase(1);
  public ItemStackHandlerBase itemOutput = new ItemStackHandlerBase(1);
  public FluidFilteredTank tank = new FluidFilteredTank(10000, FluidRegistry.WATER);
  
  public int sandAmount = 0;
  public int sandBuffer = 250;
  
  public int progress = 0;
  public int totalTime = 0;
  
  private int lastSand, lastBuffer, lastProgress, lastTotalTime, lastEnergy, lastWater;
  
  private RecipeSanding currentRecipe;
  
  public EnumFacing direction = EnumFacing.NORTH;
  
  public TileEntityMachineSanding()
  {
    super(true);
  }
  
  @Override
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    return storage;
  }
  
  @Override
  public IFluidHandler getFluidHandler(EnumFacing facing)
  {
    
    // fluids in on the right side
    if (facing == EnumHelper.Facing.right(direction))
    {
      return tank;
    }
    
    return null;
  }
  
  @Override
  public IItemHandler getInventory(EnumFacing facing)
  {
    // sand in on the left, items on the bottom, output is back
    EnumFacing left = EnumHelper.Facing.left(facing);
    EnumFacing bottom = EnumFacing.DOWN;
    EnumFacing back = direction;
    
    if (facing == left)
    {
      return sandInput;
    }
    else if (facing == bottom)
    {
      return itemInput;
    }
    else if(facing == back)
    {
      return itemOutput;
    }
    
    return null;
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
    
    if (!world.isRemote)
    {
      ItemStack sand = sandInput.getStackInSlot(0);
      if (!sand.isEmpty())
      {
    
        int amount = getSandAmount(sand);
    
        Log.info("found sand with " + LogColor.PURPLE.getColored(amount + "") + " sand value (" + sandAmount + "/" + sandBuffer + ")");
        if (amount > 0)
        {
          if (sandAmount + amount <= sandBuffer)
          {
            Log.info("Using sand to fill the buffer");
            sandAmount += amount;
            sand.shrink(1);
            sandInput.setStackInSlot(0, sand);
          }
        }
      }
      
      ItemStack input = itemInput.getStackInSlot(0);
      ItemStack output = itemOutput.getStackInSlot(0);
      RecipeSanding recipe = SandingRecipeRegistry.getRecipeFromInput(input);
      
      if (recipe == null || recipe != currentRecipe)
      {
        currentRecipe = recipe;
        progress = 0;
      }
      
      if (recipe != null)
      {
        if (progress == 0) // start of recipe
        {
          if ((output.isEmpty() || StackUtil.matchesItemAndHasSpace(recipe.getCraftingResult(), output, true)) && sandAmount >= recipe.sandAmount && tank.getFluidAmount() >= recipe.waterAmount)
          {
            sandAmount -= recipe.sandAmount;
            tank.drain(recipe.waterAmount, true);
            
            totalTime = recipe.time;
            
            progress = 1;
          }
        }
        else
        {
          progress++;
          
          if (progress >= totalTime)
          {
            if (output.isEmpty())
            {
              output = recipe.getCraftingOutput();
            }
            else
            {
              output.grow(recipe.getCraftingOutput().getCount());
            }
            
            itemOutput.setStackInSlot(0, output);
            
            progress = 0;
            currentRecipe = null;
          }
        }
      }
      
      if (
        (
          lastProgress != progress || lastTotalTime != totalTime ||
            lastSand != sandAmount || lastBuffer != sandBuffer ||
            lastEnergy != storage.getEnergyStored() || lastWater != tank.getFluidAmount()
        )
          && sendUpdateWithInterval())
      {
        lastProgress = progress;
        lastTotalTime = totalTime;
    
        lastSand = sandAmount;
        lastBuffer = sandBuffer;
    
        lastWater = tank.getFluidAmount();
        lastEnergy = storage.getEnergyStored();
      }
    }
  }
  
 
  
  @Override
  public EnumFacing[] getInventorySides()
  {
    return new EnumFacing[]{ EnumHelper.Facing.left(direction), direction, EnumFacing.DOWN };
  }
  
  @Override
  public boolean validForSlot(int slot, ItemStack stack)
  {
    if (slot == SLOT_OUTPUT)
    {
      return false;
    }
    
    if (slot == SLOT_SAND)
    {
      return getSandAmount(stack) > 0;
    }
    
    if (slot == SLOT_INPUT)
    {
      // check recipe
      return  (SandingRecipeRegistry.getRecipeFromInput(stack) != null);
    }
    
    return false;
  }
  
  @Override
  protected TileFlags[] defaultFlags()
  {
    return new TileFlags[] { TileFlags.DropInventory, TileFlags.KeepNBTData };
  }
  
  @Override
  public IItemHandler[] getAllInventories()
  {
    return new IItemHandler[] { sandInput, itemOutput, itemInput };
  }
  
  @Override
  public IItemHandler[] getAllInventoriesToDrop()
  {
    return new IItemHandler[] { itemInput, itemOutput };
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    compound.setInteger("Progress", progress);
    compound.setInteger("TotalTime", totalTime);
    compound.setInteger("SandAmount", sandAmount);
    compound.setInteger("SandBuffer", sandBuffer);
    
    compound.setTag("Input", itemInput.serializeNBT());
    compound.setTag("Output", itemOutput.serializeNBT());
    compound.setTag("Sand", sandInput.serializeNBT());
    
    storage.writeToNBT(compound);
    tank.writeToNBT(compound);
    
    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    progress = compound.getInteger("Progress");
    totalTime = compound.getInteger("TotalTime");
    sandAmount = compound.getInteger("SandAmount");
    sandBuffer = compound.getInteger("SandBuffer");
    
    itemInput.deserializeNBT(compound.getCompoundTag("Input"));
    itemOutput.deserializeNBT(compound.getCompoundTag("Output"));
    sandInput.deserializeNBT(compound.getCompoundTag("Sand"));
    
    storage.readFromNbt(compound);
    tank.readFromNBT(compound);
    
    super.readSyncableNBT(compound, type);
  }
  
  @Override
  public String getInfo()
  {
    return "sand: " + sandAmount + "/" + sandBuffer + " Progress: " + progress + "/" + totalTime + " Water: " + tank.getFluidAmount() + "/" + tank.getCapacity()
      + " Power: " + storage.getEnergyStored() + "/" + storage.getCapacity();
  }
}
