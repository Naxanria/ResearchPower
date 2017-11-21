package nl.naxanria.nlib.tile.fluid;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IFluidSharingProvider
{
  int getFluidToShare();
  
  boolean doesShareFluid();
  
  EnumFacing[] getFluidProvidingSides();
  
  boolean canShareFluidTo(TileEntity tile);
}
