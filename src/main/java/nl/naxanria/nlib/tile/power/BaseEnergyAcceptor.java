package nl.naxanria.nlib.tile.power;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.util.CollectionUtil;
import nl.naxanria.nlib.util.EnumHelper;

public class BaseEnergyAcceptor extends TileEntityBase
{
  public BaseEnergyStorage storage;
  
  public BaseEnergyAcceptor(int capacity, int maxUse)
  {
    storage = new BaseEnergyStorage(capacity, maxUse, maxUse, false);
  }
  
  public BaseEnergyAcceptor(BaseEnergyStorage storage)
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
}
