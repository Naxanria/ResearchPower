package nl.naxanria.nlib.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class WorldUtil
{
  public static void doEnergyInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing sideTo, int maxTransfer)
  {
    if (maxTransfer > 0)
    {
      EnumFacing opp = sideTo == null ? null : sideTo.getOpposite();
      if (tileFrom == null || tileTo == null)
      {
        return;
      }
      if (tileFrom.hasCapability(CapabilityEnergy.ENERGY, sideTo) && tileTo.hasCapability(CapabilityEnergy.ENERGY, opp))
      {
        IEnergyStorage handlerFrom = tileFrom.getCapability(CapabilityEnergy.ENERGY, sideTo);
        IEnergyStorage handlerTo = tileTo.getCapability(CapabilityEnergy.ENERGY, opp);
  
        if (handlerFrom != null && handlerTo != null)
        {
          int drain = handlerFrom.extractEnergy(maxTransfer, true);
          if (drain > 0)
          {
            int filled = handlerTo.receiveEnergy(drain, false);
            handlerFrom.extractEnergy(filled, false);
            //return;
          }
        }
      }
  
    }
  }
  
  public static void doFluidInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing sideTo, int maxTransfer)
  {
    if(maxTransfer > 0)
    {
      if (tileFrom == null || tileTo == null)
      {
        return;
      }
      if(tileFrom.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo)
        && tileTo.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo.getOpposite()))
      {
        IFluidHandler handlerFrom = tileFrom.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo);
        IFluidHandler handlerTo = tileTo.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo.getOpposite());
        FluidStack drain = handlerFrom.drain(maxTransfer, false);
        if(drain != null)
        {
          int filled = handlerTo.fill(drain.copy(), true);
          handlerFrom.drain(filled, true);
        }
      }
    }
  }
}