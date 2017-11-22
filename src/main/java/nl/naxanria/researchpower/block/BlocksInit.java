package nl.naxanria.researchpower.block;

import net.minecraft.block.material.Material;
import nl.naxanria.nlib.Registy.BlockRegistry;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.researchpower.block.machines.BlockMachinePress;
import nl.naxanria.researchpower.block.machines.battery.BlockBattery;
import nl.naxanria.researchpower.block.machines.generators.solar.BlockSolarGenerator;
import nl.naxanria.researchpower.block.machines.generators.vacuum.BlockVacuumGenerator;
import nl.naxanria.researchpower.block.machines.station.BlockResearchStation;
import nl.naxanria.researchpower.block.ores.BlockCopperOre;

@SuppressWarnings("WeakerAccess")
public class BlocksInit
{
  private static boolean init = false;
  
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
    public static final BlockBase machineFrameBase = new BlockBase(Material.IRON, "machine_frame_base");
    public static final BlockMachinePress machinePress = new BlockMachinePress();
    
    public static class Solar
    {
      public static final BlockSolarGenerator tier_1 = new BlockSolarGenerator(1);
      public static final BlockSolarGenerator tier_2 = new BlockSolarGenerator(2);
      public static final BlockSolarGenerator tier_3 = new BlockSolarGenerator(3);
      public static final BlockSolarGenerator tier_4 = new BlockSolarGenerator(4);
      public static final BlockSolarGenerator tier_5 = new BlockSolarGenerator(5);
      public static final BlockSolarGenerator tier_6 = new BlockSolarGenerator(6);
      public static final BlockSolarGenerator tier_7 = new BlockSolarGenerator(7);
    }
  }
  
  public static void init(BlockRegistry registry)
  {
    if (init)
    {
      return;
    }
    
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
      Machines.machineFrameBase,
      Machines.machinePress,
      
      // solar
      Machines.Solar.tier_1,
      Machines.Solar.tier_2,
      Machines.Solar.tier_3,
      Machines.Solar.tier_4,
      Machines.Solar.tier_5,
      Machines.Solar.tier_6,
      Machines.Solar.tier_7
    );
    
    init = true;
  }
}
