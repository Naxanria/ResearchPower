package nl.naxanria.researchpower.block;

import net.minecraft.block.material.Material;
import nl.naxanria.nlib.Registy.BlockRegistry;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.nlib.block.IBlockBase;
import nl.naxanria.researchpower.block.machines.battery.BlockBattery;
import nl.naxanria.researchpower.block.machines.generators.solar.SolarGenerator;
import nl.naxanria.researchpower.block.machines.generators.vacuum.BlockVacuumGenerator;
import nl.naxanria.researchpower.block.machines.station.BlockResearchStation;
import nl.naxanria.researchpower.block.ores.BlockCopperOre;

@SuppressWarnings("WeakerAccess")
public class Blocks
{
  public static class Ores
  {
    public static final BlockCopperOre copperOre = new BlockCopperOre();
  }
  
  public static class Metals
  {
    public static final BlockBase copperBlock = new BlockBase(Material.IRON, "copper_block");
  }
  
  public static class Machines
  {
    public static final BlockResearchStation researchStation = new BlockResearchStation();
    public static final BlockBattery battery = new BlockBattery();
    public static final BlockVacuumGenerator vacuumGenerator = new BlockVacuumGenerator();
    
    public static class Solar
    {
      public static final SolarGenerator tier_1 = new SolarGenerator(1);
      public static final SolarGenerator tier_2 = new SolarGenerator(2);
      public static final SolarGenerator tier_3 = new SolarGenerator(3);
      public static final SolarGenerator tier_4 = new SolarGenerator(4);
      public static final SolarGenerator tier_5 = new SolarGenerator(5);
      public static final SolarGenerator tier_6 = new SolarGenerator(6);
      public static final SolarGenerator tier_7 = new SolarGenerator(7);
    }
  }
  
  public static void init(BlockRegistry registry)
  {
    registry.addAll
    (
      // ores
      Ores.copperOre,
      
      // metals
      Metals.copperBlock,
      
      // machines
      Machines.battery,
      Machines.researchStation,
      Machines.vacuumGenerator,
      
      // solar
      Machines.Solar.tier_1,
      Machines.Solar.tier_2,
      Machines.Solar.tier_3,
      Machines.Solar.tier_4,
      Machines.Solar.tier_5,
      Machines.Solar.tier_6,
      Machines.Solar.tier_7
    );
  }
}
