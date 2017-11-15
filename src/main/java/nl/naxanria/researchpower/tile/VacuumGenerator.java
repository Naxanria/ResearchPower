package nl.naxanria.researchpower.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import nl.naxanria.researchpower.util.EnumHelper;

public class VacuumGenerator extends GeneratorEntity
{
  public VacuumGenerator()
  {
    super(20, new BaseEnergyStorage(20000, 0, 80, true));
  }
  
  @Override
  public boolean canGenerate()
  {
    return true;
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
