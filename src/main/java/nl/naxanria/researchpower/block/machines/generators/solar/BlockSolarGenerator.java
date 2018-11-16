package nl.naxanria.researchpower.block.machines.generators.solar;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockTileBaseProperties;
import nl.naxanria.nlib.util.Numbers;
import nl.naxanria.researchpower.tile.machines.generators.TileEntitySolarGenerator;

import javax.annotation.Nullable;

public class BlockSolarGenerator extends BlockTileBaseProperties<PropertyInteger, TileEntitySolarGenerator>
{
  protected int base = 2;
  
  protected static boolean needRegistration = true;
  
  private BlockSolarGenerator(PropertyInteger prop)
  {
    super(Material.IRON, "solar_generator", prop);
    
    needsNBTClearRecipe = true;
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!world.isRemote && !player.isSneaking())
    {
      TileEntitySolarGenerator tile = getTileEntity(world, pos);
      player.sendStatusMessage
        (
          new TextComponentString("Tier: " + tile.tier + " Producing: " + Numbers.format(tile.produce) +
            " Active: " + tile.canGenerate() +
            " Storage: " + Numbers.format(tile.storage.getEnergyStored()) + "/" + Numbers.format(tile.storage.getMaxEnergyStored()) + " " +
            Numbers.round(tile.storage.getStoredPercentage(), 2) * 100 + "% Bonus: " + tile.bonusCount * TileEntitySolarGenerator.BONUS),
          true
        );
      //return true;
    }
    
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
    return new TileEntitySolarGenerator(state.getValue((PropertyInteger)PROPERTY));
  }

  @Override
  public Class<TileEntitySolarGenerator> getTileEntityClass()
  {
    return TileEntitySolarGenerator.class;
  }

  //TODO: Rather than having a method like this in any class which has a sub property, currently have two, maybe have some reflection logic to handle it.
  public static BlockSolarGenerator createStateVersion(PropertyInteger property)
  {
    tempProperty = property;
    BlockSolarGenerator block = new BlockSolarGenerator(property);
    tempProperty = null;
    return block;
  }

}
