package nl.naxanria.researchpower.tile;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import nl.naxanria.researchpower.util.CollectionUtil;

public abstract class GeneratorEntity extends TileEntityBase implements IEnergySharingProvider
{
  public int produce;
  
  public BaseEnergyStorage storage;
  
  public GeneratorEntity(int produce)
  {
    this.produce = produce;
    storage = new BaseEnergyStorage(1000, 0, 100, true);
  }
  
  public GeneratorEntity(int produce, BaseEnergyStorage storage)
  {
    this.produce = produce;
    this.storage = storage;
  }
  
  public abstract boolean canGenerate();
  
  @Override
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    if (CollectionUtil.contains(getProvidingSides(), facing))
    {
      return storage;
    }
    
    return super.getEnergyStorage(facing);
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
    if (!world.isRemote)
    {
      storage.receiveEnergy(produce, false);
    }
  }
}
