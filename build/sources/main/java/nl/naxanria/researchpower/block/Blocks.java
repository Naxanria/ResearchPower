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
  
  public static void init()
  {
    addBlocks
    (
      Machines.battery,
      Machines.researchStation,
      Machines.vacuumGenerator
    );
  }
  
  private static void addBlocks(IBlockBase... blocks)
  {
    for (IBlockBase b :
      blocks)
    {
      BlockRegistry.addBlock(b);
    }
  }
}
