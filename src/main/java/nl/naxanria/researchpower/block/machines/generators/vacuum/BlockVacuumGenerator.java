package nl.naxanria.researchpower.block.machines.generators.vacuum;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import nl.naxanria.researchpower.block.BlockTileBase;
import nl.naxanria.researchpower.tile.VacuumGenerator;

import javax.annotation.Nullable;

public class BlockVacuumGenerator extends BlockTileBase<VacuumGenerator>
{
  public BlockVacuumGenerator()
  {
    super(Material.IRON, "generator_vacuum");
  }
  
  @Nullable
  @Override
  public VacuumGenerator createTileEntity(World world, IBlockState state)
  {
    return new VacuumGenerator();
  }
  
  @Override
  public Class<VacuumGenerator> getTileEntityClass()
  {
    return VacuumGenerator.class;
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    VacuumGenerator tile = getTileEntity(world, pos);
    
    if (player.isSneaking())
    {
      tile.storage.extractEnergy(tile.storage.getCapacity(), false);
    }
    
    player.sendStatusMessage(new TextComponentString("Power: " + tile.storage.getEnergyStored() + " " + (tile.storage.getStoredPercentage() * 100) + "%"), true);
    
    return true;
  }
}
