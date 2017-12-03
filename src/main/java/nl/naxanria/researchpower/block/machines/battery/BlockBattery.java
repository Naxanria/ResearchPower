package nl.naxanria.researchpower.block.machines.battery;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.researchpower.tile.TileEntityBattery;

import javax.annotation.Nullable;

public class BlockBattery extends BlockTileBase<TileEntityBattery>
{
  public BlockBattery()
  {
    super(Material.IRON, "BATTERY");
  }
  
  @Nullable
  @Override
  public TileEntityBattery createTileEntity(World world, IBlockState state)
  {
    return new TileEntityBattery();
  }
  
  @Override
  public Class<TileEntityBattery> getTileEntityClass()
  {
    return TileEntityBattery.class;
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    TileEntityBattery tile = getTileEntity(world, pos);
    
    player.sendStatusMessage(new TextComponentString("Power: " + tile.storage.getEnergyStored() + " " + (tile.storage.getStoredPercentage() * 100)), true);
    
    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }
  
  
}
