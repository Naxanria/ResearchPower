package nl.naxanria.nlib.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class NBTHelper
{
  public static FluidTank getFluidTank(NBTTagCompound compound, String tankName)
  {
    if (!compound.hasKey(tankName))
    {
      return null;
    }
  
    NBTTagCompound tankTag = compound.getCompoundTag(tankName);
  
    FluidTank tank = new FluidTank(0);
    tank.readFromNBT(tankTag);
    
    return tank;
  }
  
  public static int getFluidAmount(NBTTagCompound compound, String tankName)
  {
    FluidTank tank = getFluidTank(compound, tankName);
    return tank == null ? 0 : tank.getFluidAmount();
  }
  
  public static FluidStack getFluid(NBTTagCompound compound, String tankName)
  {
    FluidTank tank = getFluidTank(compound, tankName);
    return tank == null ? new FluidStack(FluidRegistry.WATER, 0) : tank.getFluid();
  }
}
