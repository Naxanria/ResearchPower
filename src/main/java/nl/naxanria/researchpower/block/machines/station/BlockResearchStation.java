package nl.naxanria.researchpower.block.machines.station;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockTileBase;
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
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    ResearchStationTileEntity tile = getTileEntity(world, pos);
    
    if (player.isSneaking())
    {
      tile.storage.extractEnergy(tile.storage.getCapacity(), false);
    }
    
    player.sendStatusMessage(new TextComponentString("Power: " + tile.storage.getEnergyStored() + " " + (tile.storage.getStoredPercentage() * 100) + "%"), true);
    
    return true;
  }
}
