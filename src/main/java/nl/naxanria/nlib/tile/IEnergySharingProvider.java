package nl.naxanria.nlib.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IEnergySharingProvider
{
  public int getEnergyToShare();
  
  public boolean doesShare();
  
  public EnumFacing[] getProvidingSides();
  
  boolean canShareTo(TileEntity tile);
}
