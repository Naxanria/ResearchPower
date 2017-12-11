package nl.naxanria.researchpower.tile.machines.generators;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.power.GeneratorEntity;
import nl.naxanria.nlib.util.EnumHelper;
import nl.naxanria.researchpower.ModConfig;
import nl.naxanria.researchpower.block.BlocksInit;

public class TileEntitySolarGenerator extends GeneratorEntity
{
  public static final String NBT_TIER = "TIER";
  public static final int BONUS = 2;
  
  protected EnumFacing[] providingFaces = EnumHelper.Facing.combine(EnumHelper.Facing.SIDES, EnumFacing.DOWN);
  
  public static final int base = 2;
  
  public int tier;
  public int bonusCount = 0;
  
  public TileEntitySolarGenerator()
  {
    super(0);
  }
  
  public TileEntitySolarGenerator(int tier)
  {
    super(0);
    
    init(tier);
  }
  
  @Override
  protected void entityUpdate()
  {
    // todo: base this of the config
    if (ticksPassed % 10 == 0)
    {
      bonusCount = 0;
      BlockPos npos = pos;
      while (!world.isOutsideBuildHeight(npos = npos.up()))
      {
        if (world.getBlockState(npos) == BlocksInit.Other.GLASS_FOCUS.getDefaultState())
        {
          bonusCount++;
        }
        else
        {
          break;
        }
      }
    }
    
    float b = (bonusCount * BONUS) / 100f + 1f;
    
    produce = Math.round(b * getProduce(tier));
    super.entityUpdate();
  }
  
  private void init(int tier)
  {
    this.tier = tier;
    
    ModConfig.SolarPanel config = getConfigValues(tier);
    
    produce = config.produce;
    
    storage.setCapacity(config.capacity)
      .setMaxExtract(config.transfer);
  }
  
  private ModConfig.SolarPanel getConfigValues(int tier)
  {
    switch (tier)
    {
      case 1:
      default:
        return ModConfig.SolarPanels.tier1;
        
      case 2:
        return ModConfig.SolarPanels.tier2;
        
      case 3:
        return ModConfig.SolarPanels.tier3;
        
      case 4:
        return ModConfig.SolarPanels.tier4;
        
      case 5:
        return ModConfig.SolarPanels.tier5;
        
      case 6:
        return ModConfig.SolarPanels.tier6;
        
      case 7:
        return ModConfig.SolarPanels.tier7;
    }
  }
  
  @Override
  protected TileFlags[] defaultFlags()
  {
    return new TileFlags[]{ TileFlags.KeepNBTData };
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
