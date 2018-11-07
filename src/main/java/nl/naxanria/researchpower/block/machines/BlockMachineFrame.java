package nl.naxanria.researchpower.block.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.researchpower.block.BlocksInit;

public class BlockMachineFrame extends BlockBase<PropertyEnum<BlocksInit.Machines.FRAMES>>
{
  public BlockMachineFrame(PropertyEnum<BlocksInit.Machines.FRAMES> property)
  {
    super(Material.IRON, "machine_frame", property);
  }
}
