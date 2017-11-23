package nl.naxanria.researchpower.tile.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import nl.naxanria.nlib.tile.inventory.IInventoryHolder;
import nl.naxanria.nlib.tile.power.BaseEnergyAcceptor;
import nl.naxanria.nlib.util.EnumHelper;

public class TileEntityPress extends BaseEnergyAcceptor implements IInventoryHolder
{
  public static final int CAPACITY = 20000;
  public static final int MAX_USE = 1500;
  
  public ItemStackHandler inventory = new ItemStackHandler(2);
  
  protected int progress = 0;
  protected int total_time = 100;
  
  public TileEntityPress()
  {
    super(CAPACITY, MAX_USE);
  }
  
  @Override
  public EnumFacing[] getInventorySides()
  {
    return EnumHelper.Facing.ALL;
  }
  
  @Override
  public IItemHandler getInventory(EnumFacing facing)
  {
    return inventory;
  }
  
  public float getProgressPercentage()
  {
    return  (float) progress / (float) total_time;
  }
  
  public int getProgress()
  {
    return progress;
  }
  
  public void setProgress(int progress)
  {
    this.progress = progress;
  }
  
  @Override
  public void update()
  {
    super.update();
    progress++;
    if (progress > total_time)
    {
      progress = 0;
    }
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    compound.setTag("inventory", inventory.serializeNBT());
    compound.setInteger("progress", progress);

    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    inventory.deserializeNBT(compound.getCompoundTag("inventory"));
    progress = compound.getInteger("progress");
    
    super.readSyncableNBT(compound, type);
  }
}
