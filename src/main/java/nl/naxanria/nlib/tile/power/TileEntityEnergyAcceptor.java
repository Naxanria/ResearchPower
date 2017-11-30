package nl.naxanria.nlib.tile.power;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.util.CollectionUtil;
import nl.naxanria.nlib.util.EnumHelper;

public class TileEntityEnergyAcceptor extends TileEntityBase
{
  public EnergyStorageBase storage;
  
  public TileEntityEnergyAcceptor(int capacity, int maxUse)
  {
    storage = new EnergyStorageBase(capacity, maxUse, maxUse, false);
  }
  
  public TileEntityEnergyAcceptor(EnergyStorageBase storage)
  {
    this.storage = storage;
  }
  
  @Override
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    if (CollectionUtil.contains(getAcceptingSides(), facing))
    {
      return storage;
    }
    
    return null;
  }
  
  public EnumFacing[] getAcceptingSides()
  {
    return EnumHelper.Facing.ALL;
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
