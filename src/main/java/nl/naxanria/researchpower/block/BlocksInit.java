package nl.naxanria.researchpower.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import nl.naxanria.nlib.Registy.BlockRegistry;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.researchpower.block.machines.BlockMachinePress;
import nl.naxanria.researchpower.block.machines.battery.BlockBattery;
import nl.naxanria.researchpower.block.machines.generators.solar.BlockSolarGenerator;
import nl.naxanria.researchpower.block.machines.generators.vacuum.BlockVacuumGenerator;
import nl.naxanria.researchpower.block.machines.miniature.BlockMiniatureController;
import nl.naxanria.researchpower.block.machines.station.BlockResearchStation;
import nl.naxanria.researchpower.block.ores.BlockCopperOre;

@SuppressWarnings("WeakerAccess")
public class BlocksInit
{
  private static boolean init = false;
  
  public static class Ores
  {
    public static final BlockCopperOre COPPER_ORE = new BlockCopperOre();
  }
  
  public static class Metals
  {
    public static final BlockBase COPPER_BLOCK = new BlockBase(Material.IRON, "copper_block");
  }
  
  public static class Machines
  {
    public static final BlockResearchStation RESEARCH_STATION = new BlockResearchStation();
    public static final BlockBattery BATTERY = new BlockBattery();
    public static final BlockVacuumGenerator VACUUM_GENERATOR = new BlockVacuumGenerator();
    public static final BlockBase MACHINE_FRAME_BASE = new BlockBase(Material.IRON, "machine_frame_base");
    public static final BlockBase MACHINE_FRAME_ADVANCED = new BlockBase(Material.IRON, "machine_frame_advanced");
    public static final BlockBase MACHINE_FRAME_REINVIGORATED = new BlockBase(Material.IRON, "machine_frame_reinvigorated");
    public static final BlockMachinePress MACHINE_PRESS = new BlockMachinePress();

    public static class Miniature
    {
      public static final BlockMiniatureController controller = new BlockMiniatureController();

      public static IBlockState[][][] miniatureStructure = new IBlockState[5][5][5];
      static
      {
        // pillars
        for (int y = 0; y <= 4; y++)
        {
          miniatureStructure[0][y][0] = Machines.MACHINE_FRAME_BASE.getDefaultState();
          miniatureStructure[0][y][4] = Machines.MACHINE_FRAME_BASE.getDefaultState();
          miniatureStructure[4][y][0] = Machines.MACHINE_FRAME_BASE.getDefaultState();
          miniatureStructure[4][y][4] = Machines.MACHINE_FRAME_BASE.getDefaultState();
        }

        // floor
        for(int x = 1; x <= 3; x++)
        {
          for (int z = 1; z <= 3; z++)
          {
            miniatureStructure[x][0][z] = Machines.MACHINE_FRAME_BASE.getDefaultState();
          }
        }

        // controller pos
        miniatureStructure[2][0][0] = controller.getDefaultState();

        // air!
        for(int x = 0; x <= 4; x += 4)
        {
          for(int y = 0; y <= 4; y += 4)
          {
            for(int z = 0; z <= 4; z += 4)
            {
              if (miniatureStructure[x][y][z] == null)
              {
                miniatureStructure[x][y][z] = Blocks.AIR.getDefaultState();
              }
            }
          }
        }
      }
    }

    public static final BlockSolarGenerator SOLAR_GENERATOR = new BlockSolarGenerator();
  }
  
  public static class Other
  {
    public static final BlockBase RAINBOW = new BlockBase(Material.IRON, "rainbow");
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
      Ores.COPPER_ORE,
      
      // metals
      Metals.COPPER_BLOCK,
      
      // machines
      Machines.BATTERY,
      Machines.RESEARCH_STATION,
      Machines.VACUUM_GENERATOR,
      Machines.MACHINE_FRAME_BASE,
      Machines.MACHINE_FRAME_ADVANCED,
      Machines.MACHINE_FRAME_REINVIGORATED,
      Machines.MACHINE_PRESS,
      Machines.Miniature.controller,
      
      // solar
      Machines.SOLAR_GENERATOR,
      
      Other.RAINBOW
    );
    
    init = true;
  }
}
