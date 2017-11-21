package nl.naxanria.researchpower.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import nl.naxanria.nlib.tile.power.BaseEnergyStorage;
import nl.naxanria.nlib.tile.power.GeneratorEntity;
import nl.naxanria.nlib.util.EnumHelper;

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
}
