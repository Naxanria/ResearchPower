package nl.naxanria.researchpower.tile.machines;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
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
  
  
}
