package nl.naxanria.nlib.tile.fluid;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.util.CollectionUtil;
import nl.naxanria.nlib.util.EnumHelper;
import nl.naxanria.nlib.util.MathUtil;

public abstract class TileEntityFluidTankBase extends TileEntityBase implements IFluidSharingProvider
{
  public FluidTank tank;
  
  public TileEntityFluidTankBase()
  {
    tank = new FluidTank(Fluid.BUCKET_VOLUME * 4);
  }
  
  public TileEntityFluidTankBase(int capacity)
  {
    tank = new FluidTank(capacity);
  }
  
  @Override
  public int getFluidToShare()
  {
    return tank.getFluidAmount();
  }
  
  @Override
  public EnumFacing[] getFluidProvidingSides()
  {
    return EnumHelper.Facing.ALL;
  }
  
  @Override
  public boolean canShareFluidTo(TileEntity tile)
  {
    return true;
  }
  
  @Override
  public IFluidHandler getFluidHandler(EnumFacing facing)
  {
    if (CollectionUtil.contains(getFluidProvidingSides(), facing))
    {
      return tank;
    }
    
    return null;
  }
  
  public float getFluidPercent()
  {
    return MathUtil.getPercent(tank.getFluidAmount(), tank.getCapacity());
  }
}
