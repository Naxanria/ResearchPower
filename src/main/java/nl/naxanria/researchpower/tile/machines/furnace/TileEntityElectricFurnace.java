package nl.naxanria.researchpower.tile.machines.furnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import nl.naxanria.nlib.tile.IButtonResponder;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.inventory.IInventoryHolder;
import nl.naxanria.nlib.tile.power.EnergyStorageBase;
import nl.naxanria.nlib.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class TileEntityElectricFurnace extends TileEntityBase implements IInventoryHolder, IButtonResponder
{
  public static final int POWER_USAGE = 20;
  
  public List<SmeltModule> modules = new ArrayList<>();
  
  public int tier = -1;
  
  public float speed = 1;
  public int moduleCount = 1;
  
  public EnergyStorageBase storage = new EnergyStorageBase(50000, 5000, 0);
  
  public boolean autoSort;
  public boolean lastSort;
  
  public TileEntityElectricFurnace()
  {  }
  
  public TileEntityElectricFurnace(int tier)
  {
    this.tier = tier;
    
    init(tier);
  }
  
  private void init(int tier)
  {
    
    switch (tier)
    {
      case 1:
      default:
        moduleCount = 1;
        speed = 1.25f;
        break;
        
      case 2:
        moduleCount = 2;
        speed = 1.35f;
        break;
        
      case 3:
        moduleCount = 4;
        speed = 1.5f;
        break;
        
      case 4:
        moduleCount = 8;
        speed = 2.2f;
        break;
    }
    
    for (int i = 0; i < moduleCount; i++)
    {
      modules.add(new SmeltModule(speed));
    }
  }
  
  @Override
  public EnumFacing[] getInventorySides()
  {
    return EnumHelper.Facing.ALL;
  }
  
  @Override
  public boolean validForSlot(int slot, ItemStack stack)
  {
    return slot % 2 == 0 && SmeltModule.isValidInput(stack);
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
    IItemHandler[] out = new IItemHandler[modules.size() * 2];
    for (int i = 0; i < modules.size(); i++)
    {
      out[i] = modules.get(i).input;
      out[i + 1] = modules.get(i).output;
    }
    
    return out;
  }
  
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    NBTTagCompound energy = new NBTTagCompound();
    storage.writeToNBT(energy);
    compound.setTag("Energy", energy);
    
    compound.setBoolean("AutoSort", autoSort);
    
    compound.setInteger("Tier", tier);
  
    for (int i = 0; i < moduleCount; i++)
    {
      SmeltModule module = modules.get(i);
      NBTTagCompound c = new NBTTagCompound();
      module.writeToNBTCompound(c);
      compound.setTag("Mod" + i, c);
    }
    
    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    storage.readFromNbt(compound.getCompoundTag("Energy"));
    
    autoSort = compound.getBoolean("AutoSort");
    
    int t = tier;
    tier = compound.getInteger("Tier");
    
    
    if (t != tier || modules.size() == 0)
    {
      init(tier);
    }
    
    for (int i = 0; i < moduleCount; i++)
    {
      SmeltModule m = modules.get(i);
      m.readFromNBTCompound(compound.getCompoundTag("Mod" + i));
    }
    
    super.readSyncableNBT(compound, type);
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
    
    if (!world.isRemote)
    {
      int powerUsage = 0;
  
      for (SmeltModule m :
        modules)
      {
        if (m.isBurning())
        {
          powerUsage += POWER_USAGE;
        }
      }
  
      if (storage.getEnergyStored() >= powerUsage)
      {
        storage.setEnergyStored(storage.getEnergyStored() - powerUsage);
    
        for (SmeltModule m :
          modules)
        {
          m.update();
        }
    
      }
      
      if (autoSort && ticksPassed % 20 == 0)
      {
        sort();
      }
      
      if (autoSort != lastSort && sendUpdateWithInterval())
      {
        lastSort = autoSort;
      }
      
    }
  }
  
  protected void sort()
  {
  
    // do le autosort!
  
    List<Integer> sorted = new ArrayList<>();
  
    for (int i = 0; i < moduleCount; i++)
    {
      if (sorted.contains(i))
      {
        continue;
      }
    
      SmeltModule mod = modules.get(i);
      ItemStack in = mod.input.getStackInSlot(0);
    
      if (in.isEmpty())
      {
        continue;
      }
    
      List<Integer> toSort = new ArrayList<>();
      toSort.add(i);
      int total = in.getCount();
    
      for (int t = 0; t < moduleCount; t++)
      {
        if (i == t || sorted.contains(t))
        {
          continue;
        }
      
        SmeltModule m = modules.get(t);
        ItemStack stack = m.input.getStackInSlot(0);
      
        if (stack.isEmpty() || stack.isItemEqual(in))
        {
          total += stack.getCount();
          toSort.add(t);
        }
      }
    
      int per = (int) Math.ceil(total / (float) toSort.size());
      //Log.info("Spreading " + total + " over " + toSort.size() + " each: " + per);
    
      for (Integer aToSort : toSort)
      {
        int amount = per;
        if (amount > total)
        {
          amount = total;
        }
      
        SmeltModule m = modules.get(aToSort);
        ItemStack s = m.input.getStackInSlot(0);
        s.setCount(amount);
        if (s.isEmpty())
        {
          s = new ItemStack(in.getItem(), amount);
        }
      
        //Log.info(s.getCount() + " " + s.getItem().getRegistryName());
      
        m.input.setStackInSlot(0, s);
      
        total -= amount;
      }
    
      sorted.addAll(toSort);
    }
  
  }
  
  
  @Override
  public void onButtonPressed(int id, EntityPlayer player)
  {
    if (id == 0)
    {
      autoSort = !autoSort;
    }
  }
  
  @Override
  public boolean isButtonEnabled(int id, EntityPlayer player)
  {
    if (id == 0)
    {
      return moduleCount > 1;
    }
    else
    {
      return true;
    }
  }
}
