package nl.naxanria.nlib.tile.power;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.util.CollectionUtil;

public abstract class GeneratorEntity extends TileEntityBase implements IEnergySharingProvider
{
  public int produce;
  
  public EnergyStorageBase storage;
  
  public GeneratorEntity(int produce)
  {
    this.produce = produce;
    storage = new EnergyStorageBase(1000, 0, 100, true);
  }
  
  public GeneratorEntity(int produce, EnergyStorageBase storage)
  {
    this.produce = produce;
    this.storage = storage;
  }
  
  public abstract boolean canGenerate();
  
  @Override
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    if (CollectionUtil.contains(getEnergyProvidingSides(), facing))
    {
      return storage;
    }
    
    return super.getEnergyStorage(facing);
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
    if (!world.isRemote)
    {
      if (storage != null)
      {
        storage.receiveEnergy(produce, false);
      }
    }
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    storage.writeToNBT(compound);
    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    storage.readFromNbt(compound);
    super.readSyncableNBT(compound, type);
  }
}
