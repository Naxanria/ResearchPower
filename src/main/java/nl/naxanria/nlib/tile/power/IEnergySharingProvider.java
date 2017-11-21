package nl.naxanria.nlib.tile.power;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IEnergySharingProvider
{
  int getEnergyToShare();
  
  boolean doesShareEnergy();
  
  EnumFacing[] getEnergyProvidingSides();
  
  boolean canShareEnergyTo(TileEntity tile);
}
