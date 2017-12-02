package nl.naxanria.researchpower.tile;

import net.minecraft.nbt.NBTTagCompound;
import nl.naxanria.nlib.tile.fluid.TileEntityFluidTankBase;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.researchpower.ModConfig;

public class TileEntityDrum extends TileEntityFluidTankBase
{
  public static final String NBT_TIER = "Tier";
  
  public int capacity;
  public int tier;
  
  public TileEntityDrum()
  {
    this(0);
  }
  
  public TileEntityDrum(int tier)
  {
    super(0);
    
    this.tier = tier;
    init(tier);
  }
  
  private static int getCapacity(int tier)
  {
    tier = tier - 1;
    if (tier == -1)
    {
      tier = 0;
    }
    
    return ModConfig.fluidDrumSize[tier];
  }
  
  private void init(int tier)
  {
    capacity = getCapacity(tier);
    tank.setCapacity(capacity);
  }
  
  
  
  @Override
  public boolean doesShareFluid()
  {
    return false;
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    NBTTagCompound c = new NBTTagCompound();
    tank.writeToNBT(c);
    compound.setTag("tank", c);
    compound.setInteger(NBT_TIER, tier);
    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    tier = compound.getInteger(NBT_TIER);
    
    tank.readFromNBT(compound.getCompoundTag("tank"));
    if (capacity == 0)
    {
      init(tier);
    }
    
    if (tank.getCapacity() != getCapacity(tier))
    {
      init(tier);
    }
  
    Log.info(type + " " + tier + " " + capacity);
    
    super.readSyncableNBT(compound, type);
  }
}