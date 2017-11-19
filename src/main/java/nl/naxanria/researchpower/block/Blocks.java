package nl.naxanria.researchpower.block;

import nl.naxanria.nlib.Registy.BlockRegistry;
import nl.naxanria.nlib.block.IBlockBase;
import nl.naxanria.researchpower.block.machines.battery.BlockBattery;
import nl.naxanria.researchpower.block.machines.generators.vacuum.BlockVacuumGenerator;
import nl.naxanria.researchpower.block.machines.station.BlockResearchStation;

@SuppressWarnings("WeakerAccess")
public class Blocks
{
  public static class Machines
  {
    public static final BlockResearchStation researchStation = new BlockResearchStation();
    public static final BlockBattery battery = new BlockBattery();
    public static final BlockVacuumGenerator vacuumGenerator = new BlockVacuumGenerator();
  }
  
  public static void init(BlockRegistry registry)
  {
    registry.addAll
    (
      Machines.battery,
      Machines.researchStation,
      Machines.vacuumGenerator
    );
  }
}
