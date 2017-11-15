package nl.naxanria.researchpower.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import nl.naxanria.researchpower.util.EnumHelper;

public class BatteryEntity extends TileEntityBase implements IEnergySharingProvider
{
  public BaseEnergyStorage storage;
  
  public BatteryEntity(BaseEnergyStorage storage)
  {
    this.storage = storage;
  }
  
  @Override
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    return storage;
  }
  
  @Override
  public int getEnergyToShare()
  {
    return storage.getEnergyStored();
  }
  
  @Override
  public boolean doesShare()
  {
    return true;
  }
  
  @Override
  public EnumFacing[] getProvidingSides()
  {
    return EnumHelper.Facing.ALL;
  }
  
  @Override
  public boolean canShareTo(TileEntity tile)
  {
    return true;
  }
}
