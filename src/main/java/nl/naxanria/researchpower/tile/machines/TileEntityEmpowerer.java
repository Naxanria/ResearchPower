package nl.naxanria.researchpower.tile.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import nl.naxanria.nlib.tile.inventory.TileEntityInventoryBase;
import nl.naxanria.nlib.tile.power.BaseEnergyStorage;
import nl.naxanria.nlib.tile.power.IEnergySharingProvider;
import nl.naxanria.nlib.util.EnumHelper;
import nl.naxanria.nlib.util.MathUtil;

public class TileEntityEmpowerer extends TileEntityInventoryBase implements IEnergySharingProvider
{
  public static final int ENERGY_USE_MAX = 4000;
  public static final int ENERGY_CAPACITY = 400000;
  
  public static final int SLOT_INPUT_MINOR_0 = 0;
  public static final int SLOT_INPUT_MINOR_1 = 1;
  public static final int SLOT_INPUT_MINOR_2 = 2;
  public static final int SLOT_INPUT_MINOR_3 = 3;
  public static final int SLOT_INPUT_MAYOR = 4;
  public static final int SLOT_OUTPUT = 5;
  
  public BaseEnergyStorage storage;
  
  public int progress = 0;
  public int totalTime = 1000;
  public int energyPerTick = 0;
  
  public TileEntityEmpowerer()
  {
    super(6);
    
    storage = new BaseEnergyStorage(ENERGY_CAPACITY, 0, ENERGY_USE_MAX, true);
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
    
    if (!world.isRemote)
    {
      progress++;
      if (progress >= totalTime)
      {
        progress = 0;
      }
    }
  }
  
  @Override
  public int getEnergyToShare()
  {
    return 0;
  }
  
  @Override
  public boolean doesShareEnergy()
  {
    return false;
  }
  
  @Override
  public EnumFacing[] getEnergyProvidingSides()
  {
    return EnumHelper.Facing.ALL;
  }
  
  @Override
  public boolean canShareEnergyTo(TileEntity tile)
  {
    return false;
  }
  
  public float getProgressPercent()
  {
    return MathUtil.clamp01((float) progress / (float) totalTime);
  }
}
