package nl.naxanria.researchpower.block.machines.station;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import nl.naxanria.researchpower.block.BlockTileBase;
import nl.naxanria.researchpower.tile.ResearchStationTileEntity;

import javax.annotation.Nullable;

public class BlockResearchStation extends BlockTileBase<ResearchStationTileEntity>
{
  public BlockResearchStation()
  {
    super(Material.IRON, "research_station");
  }
  
  @Nullable
  @Override
  public ResearchStationTileEntity createTileEntity(World world, IBlockState state)
  {
    return new ResearchStationTileEntity();
  }
  
  @Override
  public Class<ResearchStationTileEntity> getTileEntityClass()
  {
    return ResearchStationTileEntity.class;
  }
}
