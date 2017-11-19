package nl.naxanria.researchpower.tile;

import nl.naxanria.nlib.tile.BaseEnergyStorage;
import nl.naxanria.nlib.tile.BatteryEntity;

public class BlockBatteryEntity extends BatteryEntity
{
  public BlockBatteryEntity()
  {
    super(new BaseEnergyStorage(1000000, 1000, 1000));
  }
}
