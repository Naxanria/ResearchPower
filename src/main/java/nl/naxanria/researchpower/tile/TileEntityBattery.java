package nl.naxanria.researchpower.tile;

import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.power.EnergyStorageBase;

public class TileEntityBattery extends nl.naxanria.nlib.tile.power.TileEntityBattery
{
  public TileEntityBattery()
  {
    super(new EnergyStorageBase(1000000, 1000, 1000));
  }
  
  @Override
  protected TileFlags[] defaultFlags()
  {
    return new TileFlags[] { TileFlags.KeepNBTData };
  }
}
