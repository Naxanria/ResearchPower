package nl.naxanria.researchpower.block;

import net.minecraft.block.material.Material;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.researchpower.tile.machines.TileEntityMachineSanding;

public class BlockCleanedSand extends BlockBase
{
  public BlockCleanedSand()
  {
    super(Material.SAND, "sand_cleaned");
  
    TileEntityMachineSanding.registerSandAmount(this, 35);
  }
}
