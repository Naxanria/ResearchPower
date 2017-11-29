package nl.naxanria.researchpower.block.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.researchpower.tile.machines.TileEntityEmpowerer;

import javax.annotation.Nullable;

public class BlockMachineEmpowerer extends BlockTileBase<TileEntityEmpowerer>
{
  public BlockMachineEmpowerer()
  {
    super(Material.IRON, "machine_empowerer");
  }
  
  @Nullable
  @Override
  public TileEntityEmpowerer createTileEntity(World world, IBlockState state)
  {
    return new TileEntityEmpowerer();
  }
  
  @Override
  public Class<TileEntityEmpowerer> getTileEntityClass()
  {
    return TileEntityEmpowerer.class;
  }
}
