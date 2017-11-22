package nl.naxanria.researchpower.block.machines.generators.solar;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.nlib.util.LogColor;
import nl.naxanria.researchpower.tile.TileEntitySolarGenerator;

import javax.annotation.Nullable;

public class BlockSolarGenerator extends BlockTileBase<TileEntitySolarGenerator>
{
  protected int tier;
  protected int base = 2;
  
  protected static boolean needRegistration = true;
  
  public BlockSolarGenerator(int tier)
  {
    super(Material.IRON, "solar_generator_tier_" + tier);
    
    this.tier = tier;
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    TileEntitySolarGenerator tile = getTileEntity(world, pos);
    player.sendStatusMessage
      (
        new TextComponentString("Producing: " + tile.produce +
        " Active: " + tile.canGenerate() +
        " Storage: " + tile.storage.getEnergyStored() + "/" + tile.storage.getMaxEnergyStored() + " " + tile.storage.getStoredPercentage() * 100 + "%"),
        true
      );
    
    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }
  
  @Override
  protected boolean needTileEntityRegistration()
  {
    if (needRegistration)
    {
      needRegistration = false;
      return true;
    }
    
    return false;
  }
  
  @Nullable
  @Override
  public TileEntitySolarGenerator createTileEntity(World world, IBlockState state)
  {
    return new TileEntitySolarGenerator(tier);
  }
  
  @Override
  public Class<TileEntitySolarGenerator> getTileEntityClass()
  {
    return TileEntitySolarGenerator.class;
  }
}
