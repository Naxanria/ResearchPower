package nl.naxanria.researchpower.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import nl.naxanria.nlib.registry.BlockRegistry;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.researchpower.block.fluid.BlockFluidDrum;
import nl.naxanria.researchpower.block.machines.*;
import nl.naxanria.researchpower.block.machines.battery.BlockBattery;
import nl.naxanria.researchpower.block.machines.generators.BlockCoalGenerator;
import nl.naxanria.researchpower.block.machines.generators.solar.BlockSolarGenerator;
import nl.naxanria.researchpower.block.machines.generators.vacuum.BlockVacuumGenerator;
import nl.naxanria.researchpower.block.machines.miniature.BlockMiniatureController;
import nl.naxanria.researchpower.block.machines.station.BlockResearchStation;
import nl.naxanria.researchpower.block.ores.BlockCopperOre;
import nl.naxanria.researchpower.block.redstone.BlockEmitter;

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
  
  public static class Redstone
  {
    public static final BlockEmitter EMITTER = (BlockEmitter) BlockEmitter.stateVersion(BlockEmitter.class, PropertyInteger.create("power", 0, 15));
  }
  
  
  public static class Machines
  {
    public static final BlockResearchStation RESEARCH_STATION = new BlockResearchStation();
    public static final BlockBattery BATTERY = new BlockBattery();
    public static final BlockVacuumGenerator VACUUM_GENERATOR = new BlockVacuumGenerator();
    public static final BlockCoalGenerator COAL_GENERATOR = new BlockCoalGenerator();
    public static final BlockMachineSanding MACHINE_SANDING = new BlockMachineSanding();
    public static final BlockElectricFurnace ELECTRIC_FURNACE = new BlockElectricFurnace();

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

    public static final BlockBase MACHINE_FRAME = BlockMachineFrame.stateVersion(BlockMachineFrame.class, PropertyEnum.create("type", FRAMES.class));
    public static final BlockMachinePress MACHINE_PRESS = new BlockMachinePress();
    public static final BlockMachineEmpowerer MACHINE_EMPOWERER = new BlockMachineEmpowerer();
    
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
          for(int y = 0; y <= 4; y += 4)
            for(int z = 0; z <= 4; z += 4)
              if (miniatureStructure[x][y][z] == null)
                miniatureStructure[x][y][z] = Blocks.AIR.getDefaultState();
      }
    }

    //(BlockSolarGenerator) BlockSolarGenerator.stateVersion(BlockSolarGenerator.class, PropertyInteger.create("tier", 1, 7));
    public static final BlockSolarGenerator SOLAR_GENERATOR = (BlockSolarGenerator.createStateVersion(PropertyInteger.create("tier", 1, 7)));
  }
  
  public static final BlockFluidDrum FLUID_DRUM = (BlockFluidDrum) BlockFluidDrum.stateVersion(BlockFluidDrum.class, PropertyInteger.create("tier", 1, 3)); //BlockFluidDrum.createStateVersion(PropertyInteger.create("tier", 1, 3));
  
  public static class Other
  {
    public static final BlockBase RAINBOW = new BlockBase(Material.IRON, "rainbow");
    public static final BlockCleanedSand CLEANED_SAND = new BlockCleanedSand();
    public static final BlockFocusGlass GLASS_FOCUS = new BlockFocusGlass();
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
      Machines.COAL_GENERATOR,
      Machines.MACHINE_FRAME,
      Machines.MACHINE_PRESS,
      Machines.MACHINE_EMPOWERER,
      Machines.MACHINE_SANDING,
      Machines.ELECTRIC_FURNACE,
      
      Machines.Miniature.controller,
      
      // solar
      Machines.SOLAR_GENERATOR,
      
      FLUID_DRUM,
      
      Redstone.EMITTER,
      
      Other.RAINBOW,
      Other.CLEANED_SAND,
      
      Other.GLASS_FOCUS
    );
    
    init = true;
  }
}
