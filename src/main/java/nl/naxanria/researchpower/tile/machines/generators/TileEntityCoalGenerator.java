package nl.naxanria.researchpower.tile.machines.generators;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.inventory.TileEntityInventoryBase;
import nl.naxanria.nlib.tile.power.EnergyStorageBase;
import nl.naxanria.nlib.tile.power.IEnergySharingProvider;
import nl.naxanria.nlib.util.EnumHelper;
import nl.naxanria.nlib.util.MathUtil;
import nl.naxanria.nlib.util.StackUtil;

public class TileEntityCoalGenerator extends TileEntityInventoryBase implements IEnergySharingProvider
{
  public static final int PRODUCE = 30;
  public static final int SLOT_FUEL = 0;
  
  public final EnergyStorageBase storage;
  
  public int currBurnTime;
  public int maxBurnTime;
  
  public int lastCompare;
  public int lastEnergy;
  public int lastCurrentBurnTime;
  public int lastMaxBurnTime;
  
  public TileEntityCoalGenerator()
  {
    super(1);
    
    storage = new EnergyStorageBase(50000, 0, 120, true);
  }
  
  @Override
  protected TileFlags[] defaultFlags()
  {
    return new TileFlags[]{ TileFlags.DropInventory, TileFlags.KeepNBTData };
  }
  
  @Override
  public boolean isItemValidForSlot(ItemStack stack, int slot)
  {
    return TileEntityFurnace.isItemFuel(stack);
  }
  
  @Override
  public boolean canExtractItem(ItemStack stack, int slot)
  {
    return true;
  }
  
  @Override
  public int getComparatorStrength()
  {
    return (int) (storage.getStoredPercentage() * 15);
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
  
//    Log.info(maxBurnTime + "<>" + world.isRemote);
    
    if (!world.isRemote)
    {
      boolean on = currBurnTime > 0;
      
      
      if (on)
      {
        currBurnTime--;
        storage.receiveEnergy(PRODUCE, false);
      }
  
      if(on != this.currBurnTime > 0 || this.lastCompare != this.getComparatorStrength())
      {
        lastCompare = getComparatorStrength();
        markDirty();
      }
      
      if (currBurnTime <= 0 && !inventory.getStackInSlot(SLOT_FUEL).isEmpty())
      {
        int time = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(SLOT_FUEL));
        
        if (!storage.isFull())
        {
          maxBurnTime = time;
          currBurnTime = time;
          
          //Log.warn(maxBurnTime + "");
          
          inventory.setStackInSlot(SLOT_FUEL, StackUtil.changeStackSize(inventory.getStackInSlot(SLOT_FUEL), -1));
        }
      }
  
      if((storage.getEnergyStored() != lastEnergy || currBurnTime != lastCurrentBurnTime || lastMaxBurnTime != maxBurnTime) && sendUpdateWithInterval())
      {
        lastEnergy = this.storage.getEnergyStored();
        lastCurrentBurnTime = currBurnTime;
        lastMaxBurnTime = maxBurnTime;
      }
    }
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    super.writeSyncableNBT(compound, type);
    
    if (type == NBTType.SAVE_BLOCK)
    {
      compound.setInteger("BurnTime", currBurnTime);
      compound.setInteger("MaxBurnTime", maxBurnTime);
    }
    
    storage.writeToNBT(compound);
    
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    super.readSyncableNBT(compound, type);
    
    if (type == NBTType.SAVE_BLOCK)
    {
      currBurnTime = compound.getInteger("BurnTime");
      maxBurnTime = compound.getInteger("MaxBurnTime");
    }
    
    storage.readFromNbt(compound);
    
  }
  
  @Override
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    return storage;
  }
  
  @Override
  public int getEnergyToShare()
  {
    return storage.getCapacity();
  }
  
  @Override
  public boolean doesShareEnergy()
  {
    return true;
  }
  
  @Override
  public EnumFacing[] getEnergyProvidingSides()
  {
    return EnumHelper.Facing.ALL;
  }
  
  @Override
  public boolean canShareEnergyTo(TileEntity tile)
  {
    return true;
  }
  
  public float getProgressPercentage()
  {
    return MathUtil.getPercent(currBurnTime, maxBurnTime);
  }
  
  @Override
  public boolean validForSlot(int slot, ItemStack stack)
  {
    return slot == SLOT_FUEL && TileEntityFurnace.isItemFuel(stack);
  }
}
