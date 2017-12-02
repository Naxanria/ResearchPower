package nl.naxanria.researchpower.tile.machines;

import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.power.TileEntityEnergyAcceptor;

public class TileEntityResearchStation extends TileEntityEnergyAcceptor
{
  public TileEntityResearchStation()
  {
    super(100000, 300);
    
    enableFlag(TileFlags.KeepNBTData);
  }
}
