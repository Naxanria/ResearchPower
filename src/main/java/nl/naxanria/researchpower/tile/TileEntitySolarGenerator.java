package nl.naxanria.researchpower.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.power.GeneratorEntity;
import nl.naxanria.nlib.util.EnumHelper;

public class TileEntitySolarGenerator extends GeneratorEntity
{
  public static final String NBT_TIER = "TIER";
  
  protected EnumFacing[] providingFaces = EnumHelper.Facing.combine(EnumHelper.Facing.SIDES, EnumFacing.DOWN);
  
  public static final int base = 2;
  
  public int tier;
  
  public TileEntitySolarGenerator()
  {
    super(0);
  }
  
  public TileEntitySolarGenerator(int tier)
  {
    super(0);
    
    init(tier);
  }
  
  private void init(int tier)
  {
    produce = getProduce(tier);
    
    this.tier = tier;
    
    storage.setCapacity(getCapacity(tier))
      .setMaxExtract(getMaxExtract(tier));
  }
  
  public static int getMaxExtract(int tier)
  {
    return getProduce(tier) * 3;
  }
  
  public static int getCapacity(int tier)
  {
    return getProduce(tier) * 6000 + (tier - 1) * 2500;
  }
  
  public static int getProduce(int tier)
  {
    return (int) (base * Math.pow(9, tier - 1));
  }
  
  @Override
  public boolean canGenerate()
  {
    return world.canSeeSky(pos.up()) && world.isDaytime();
  }
  
  @Override
  public int getEnergyToShare()
  {
    return produce;
  }
  
  @Override
  public boolean doesShareEnergy()
  {
    return true;
  }
  
  @Override
  public EnumFacing[] getEnergyProvidingSides()
  {
    return providingFaces;
  }
  
  @Override
  public boolean canShareEnergyTo(TileEntity tile)
  {
    return true;
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    compound.setInteger(NBT_TIER, tier);
    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    tier = compound.getInteger(NBT_TIER);
    if (produce == 0)
    {
      init(tier);
    }
    super.readSyncableNBT(compound, type);
  }
}
