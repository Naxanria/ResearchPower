package nl.naxanria.researchpower.tile.machines.furnace;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.inventory.IInventoryHolder;
import nl.naxanria.nlib.tile.power.EnergyStorageBase;
import nl.naxanria.nlib.util.EnumHelper;

public class TileEntityElectricFurnace extends TileEntityBase implements IInventoryHolder
{
  public static final int SLOT_MODULE_0_INPUT = 0;
  public static final int SLOT_MODULE_0_OUTPUT = 1;
  public static final int SLOT_MODULE_1_INPUT = 2;
  public static final int SLOT_MODULE_1_OUTPUT = 3;
  
  public static final int POWER_USAGE = 20;
  
  public SmeltModule module1 = new SmeltModule(1.5f);
  public SmeltModule module2 = new SmeltModule(1.5f);
  
  public EnergyStorageBase storage = new EnergyStorageBase(50000, 5000, 0);
  
  @Override
  public EnumFacing[] getInventorySides()
  {
    return EnumHelper.Facing.ALL;
  }
  
  @Override
  public boolean validForSlot(int slot, ItemStack stack)
  {
    if (slot == SLOT_MODULE_0_OUTPUT || slot == SLOT_MODULE_1_OUTPUT)
    {
      return false;
    }
    
    if (slot == SLOT_MODULE_0_INPUT || slot == SLOT_MODULE_1_INPUT)
    {
      return SmeltModule.isValidInput(stack);
    }
    
    return false;
  }
  
  @Override
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    return storage;
  }
  
  @Override
  protected TileFlags[] defaultFlags()
  {
    return new TileFlags[] { TileFlags.DropInventory, TileFlags.KeepNBTData };
  }
  
  @Override
  public IItemHandler[] getAllInventories()
  {
    return new IItemHandler[] { module1.input, module1.output, module2.input, module2.output };
  }
  
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    NBTTagCompound energy = new NBTTagCompound();
    storage.writeToNBT(energy);
    compound.setTag("Energy", energy);
  
    NBTTagCompound mod1 = new NBTTagCompound();
    module1.writeToNBTCompound(mod1);
    compound.setTag("Module1", mod1);
  
    NBTTagCompound mod2 = new NBTTagCompound();
    module2.writeToNBTCompound(mod2);
    compound.setTag("Module2", mod2);
    
    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    storage.readFromNbt(compound.getCompoundTag("Energy"));
    module1.readFromNBTCompound(compound.getCompoundTag("Module1"));
    module2.readFromNBTCompound(compound.getCompoundTag("Module2"));
    
    super.readSyncableNBT(compound, type);
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
    int powerUsage = 0;
    if (module1.isBurning())
    {
      powerUsage += POWER_USAGE;
    }
    if (module2.isBurning())
    {
      powerUsage += POWER_USAGE;
    }
    
    if (storage.getEnergyStored() >= powerUsage)
    {
      storage.setEnergyStored(storage.getEnergyStored() - powerUsage);
      module1.update();
      module2.update();
    }
  }
}
