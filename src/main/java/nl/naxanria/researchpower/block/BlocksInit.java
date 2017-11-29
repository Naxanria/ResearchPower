package nl.naxanria.researchpower.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
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

    public enum FRAMES implements IStringSerializable
    {
      BASE("base"),
      ADVANCED("advanced"),
      REINVIGORATED("reinvigorated");

      final String name;

      FRAMES(String name)
      {
        this.name = name;
      }

      @Override
      public String getName()
      {
        return name;
      }
    }

    public static final BlockBase MACHINE_FRAME = BlockBase.createStateVersion(Material.IRON, "machine_frame", PropertyEnum.create("type", FRAMES.class));
    public static final BlockMachinePress MACHINE_PRESS = new BlockMachinePress();

    public static class Miniature
    {
      public static final BlockMiniatureController controller = new BlockMiniatureController();

      public static IBlockState[][][] miniatureStructure = new IBlockState[5][5][5];
      static
      {
        IBlockState floor = Machines.MACHINE_FRAME.getDefaultState().withProperty(MACHINE_FRAME.PROPERTY, FRAMES.BASE);
        IBlockState pillars = Machines.MACHINE_FRAME.getDefaultState().withProperty(MACHINE_FRAME.PROPERTY, FRAMES.ADVANCED);
        IBlockState pillarTop = Machines.BATTERY.getDefaultState();

        // pillars
        for (int y = 0; y <= 3; y++)
        {
          miniatureStructure[0][y][0] = pillars;
          miniatureStructure[0][y][4] = pillars;
          miniatureStructure[4][y][0] = pillars;
          miniatureStructure[4][y][4] = pillars;
        }

        // pillar top
        miniatureStructure[0][4][0] = pillarTop;
        miniatureStructure[0][4][4] = pillarTop;
        miniatureStructure[4][4][0] = pillarTop;
        miniatureStructure[4][4][4] = pillarTop;

        // floor
        for(int x = 1; x <= 3; x++)
        {
          for (int z = 1; z <= 3; z++)
          {
            miniatureStructure[x][0][z] = floor;
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

    public static final BlockSolarGenerator SOLAR_GENERATOR = (BlockSolarGenerator)BlockSolarGenerator.createStateVersion(PropertyInteger.create("tier", 1, 7));
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
      Machines.MACHINE_FRAME,
      Machines.MACHINE_PRESS,
      Machines.Miniature.controller,
      
      // solar
      Machines.SOLAR_GENERATOR,
      
      Other.RAINBOW
    );
    
    init = true;
  }
}
