package nl.naxanria.nlib.tile.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import nl.naxanria.nlib.inventory.ItemStackHandlerBase;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.util.CollectionUtil;
import nl.naxanria.nlib.util.EnumHelper;

public class TileEntityInventoryBase extends TileEntityBase implements IInventoryHolder
{
  public final ItemStackHandlerBase inventory;
  
  public TileEntityInventoryBase(int slots)
  {
    this(slots, true);
  }
  
  public TileEntityInventoryBase(int slots, boolean dropInventory)
  {
    if (dropInventory)
    {
      enableFlag(TileFlags.DropInventory);
    }
    inventory = new ItemStackHandlerBase(slots)
    {
      @Override
      public boolean canInsert(ItemStack stack, int slot)
      {
        return TileEntityInventoryBase.this.isItemValidForSlot(stack, slot);
      }
  
      @Override
      public boolean canExtract(ItemStack stack, int slot)
      {
        return TileEntityInventoryBase.this.canExtractItem(stack, slot);
      }
  
      @Override
      protected void onContentsChanged(int slot)
      {
        super.onContentsChanged(slot);
        TileEntityInventoryBase.this.markDirty();
      }
  
      @Override
      public int getSlotLimit(int slot)
      {
        return TileEntityInventoryBase.this.getMaxStackSize(slot);
      }
    };
  }
  
  @Override
  public EnumFacing[] getInventorySides()
  {
    return EnumHelper.Facing.ALL;
  }
  
  public boolean isItemValidForSlot(ItemStack stack, int slot)
  {
    return true;
  }
  
  public boolean canExtractItem(ItemStack stack, int slot)
  {
    return true;
  }
  
  public int getMaxStackSize(int slot)
  {
    return 64;
  }
  
  @Override
  public IItemHandler getInventory(EnumFacing facing)
  {
    return (CollectionUtil.contains(getInventorySides(), facing)) ? inventory : null;
  }
  
  @Override
  public int getComparatorStrength()
  {
    return ItemHandlerHelper.calcRedstoneFromInventory(inventory);
  }
  
  @Override
  public IItemHandler[] getAllInventories()
  {
    return new IItemHandler[] { inventory };
  }
  
  public static void saveSlots(IItemHandler slots, NBTTagCompound compound)
  {
    if(slots != null && slots.getSlots() > 0)
    {
      NBTTagList tagList = new NBTTagList();
      for(int i = 0; i < slots.getSlots(); i++)
      {
        ItemStack slot = slots.getStackInSlot(i);
        NBTTagCompound tagCompound = new NBTTagCompound();
        if(!slot.isEmpty())
        {
          slot.writeToNBT(tagCompound);
        }
        
        tagList.appendTag(tagCompound);
      }
      compound.setTag("Items", tagList);
    }
  }
  
  public static void loadSlots(IItemHandlerModifiable slots, NBTTagCompound compound)
  {
    if(slots != null && slots.getSlots() > 0)
    {
      NBTTagList tagList = compound.getTagList("Items", 10);
      for(int i = 0; i < slots.getSlots(); i++)
      {
        NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
        slots.setStackInSlot(i, tagCompound.hasKey("id") ? new ItemStack(tagCompound) : ItemStack.EMPTY);
      }
    }
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    super.writeSyncableNBT(compound, type);
    
    if(type == NBTType.SAVE_TILE || (type == NBTType.SYNC && shouldSyncSlots()))
    {
      saveSlots(inventory, compound);
    }
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    super.readSyncableNBT(compound, type);
    if(type == NBTType.SAVE_TILE || (type == NBTType.SYNC && shouldSyncSlots()))
    {
      loadSlots(inventory, compound);
    }
  }
  
  public boolean shouldSyncSlots()
  {
    return false;
  }
}
