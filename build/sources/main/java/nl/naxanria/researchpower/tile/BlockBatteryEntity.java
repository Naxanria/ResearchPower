package nl.naxanria.researchpower.tile;

public class BlockBatteryEntity extends BatteryEntity
{
  public BlockBatteryEntity()
  {
    super(new BaseEnergyStorage(1000000, 1000, 1000));
  }
}
