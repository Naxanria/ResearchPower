package nl.naxanria.nlib.tile.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class FluidFilteredTank extends FluidTank implements IFluidTankProperties
{
  protected Fluid filter;
  
  public FluidFilteredTank(int capacity, Fluid filter)
  {
    super(capacity);
    init(filter);
  }
  
  public FluidFilteredTank(@Nullable FluidStack fluidStack, int capacity, Fluid filter)
  {
    super(fluidStack, capacity);
    init(filter);
  }
  
  public FluidFilteredTank(Fluid fluid, int amount, int capacity, Fluid filter)
  {
    super(fluid, amount, capacity);
    init(filter);
  }
  
  protected void init(Fluid filter)
  {
    this.filter = filter;
  }
  
  @Nullable
  @Override
  public FluidStack getContents()
  {
    return this.fluid;
  }
  
  @Override
  public boolean canFillFluidType(FluidStack fluid)
  {
    return isFiltered(fluid) && super.canFillFluidType(fluid);
  }
  
  @Override
  public boolean canDrainFluidType(@Nullable FluidStack fluid)
  {
    return isFiltered(fluid) && super.canDrainFluidType(fluid);
  }
  
  public Fluid getFilter()
  {
    return filter;
  }
  
  public boolean isFiltered(FluidStack fluid)
  {
    return fluid != null && fluid.getFluid() == filter;
  }
}
