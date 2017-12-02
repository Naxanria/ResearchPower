package nl.naxanria.researchpower.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.power.EnergyStorageBase;
import nl.naxanria.nlib.tile.power.GeneratorEntity;
import nl.naxanria.nlib.util.EnumHelper;

public class TileEntityVacuumGenerator extends GeneratorEntity
{
  public TileEntityVacuumGenerator()
  {
    super(20, new EnergyStorageBase(20000, 0, 80, true));
    
    enableFlag(TileFlags.KeepNBTData);
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
