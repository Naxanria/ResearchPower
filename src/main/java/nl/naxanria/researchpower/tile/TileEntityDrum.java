package nl.naxanria.researchpower.tile;

import net.minecraft.nbt.NBTTagCompound;
import nl.naxanria.nlib.tile.fluid.TileEntityFluidTankBase;

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
    switch (tier)
    {
      case 1:
      default:
        return 16000;
        
      case 2:
        return 50000;
        
      case 3:
        return 160000;
    }
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
    if (capacity == 0)
    {
      init(tier);
    }
    
    tank.readFromNBT(compound.getCompoundTag("tank"));
    super.readSyncableNBT(compound, type);
  }
}
