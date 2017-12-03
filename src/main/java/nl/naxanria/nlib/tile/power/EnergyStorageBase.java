package nl.naxanria.nlib.tile.power;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;
import nl.naxanria.nlib.util.MathUtil;

public class EnergyStorageBase extends EnergyStorage
{
  public static final String NBT_ENERGY = "ENERGY";
  
  protected boolean internalStorageOnly = false;
  
  public EnergyStorageBase(int capacity, int maxReceive, int maxExtract)
  {
    super(capacity, maxReceive, maxExtract);
  }
  
  public EnergyStorageBase(int capacity, int maxReceive, int maxExtract, boolean internalStorageOnly)
  {
    super(capacity, maxReceive, maxExtract);
    
    this.internalStorageOnly = internalStorageOnly;
  }
  
  public EnergyStorageBase setMaxReceive(int max)
  {
    maxReceive = max;
    
    return this;
  }
  
  public int getMaxReceive()
  {
    return maxReceive;
  }
  
  public EnergyStorageBase setMaxExtract(int max)
  {
    maxExtract = max;
    
    return this;
  }
  
  public EnergyStorageBase setCapacity(int capacity)
  {
    if (capacity < this.capacity)
    {
      if (energy > capacity)
      {
        energy = capacity;
      }
    }
    this.capacity = capacity;
    
    return this;
  }
  
  public float getStoredPercentage()
  {
    return getStoredPercentage(energy);
  }
  
  public float getStoredPercentage(int storage)
  {
    return MathUtil.getPercent(storage, capacity);
  }
  
  public int getCapacity()
  {
    return capacity;
  }
  
  public boolean isFull()
  {
    return energy == capacity;
  }
  
  @Override
  public int receiveEnergy(int maxReceive, boolean simulate)
  {
    if (internalStorageOnly)
    {
      int received = Math.min(capacity - energy, maxReceive);
      if (!simulate)
      {
        energy += received;
      }
      
      return received;
    }
    else
    {
      return super.receiveEnergy(maxReceive, simulate);
    }
  }
  
  public void setEnergyStored(int amount)
  {
    if (amount > capacity)
    {
      energy = capacity;
    }
    else
    {
      energy = amount;
    }
  }
  
  public boolean isInternalStorageOnly()
  {
    return internalStorageOnly;
  }
  
  public void setInternalStorageOnly(boolean internalStorageOnly)
  {
    this.internalStorageOnly = internalStorageOnly;
  }
  
  public void writeToNBT(NBTTagCompound compound)
  {
    compound.setInteger(NBT_ENERGY, energy);
  }
  
  public void readFromNbt(NBTTagCompound compound)
  {
    setEnergyStored(compound.getInteger(NBT_ENERGY));
  }
  
  public int getMaxExtract()
  {
    return maxExtract;
  }
  
  
}
