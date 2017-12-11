package nl.naxanria.nlib.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;

public abstract class ContainerBase<TTE extends TileEntityBase> extends Container
{
  public final TTE tile;
  public final EntityPlayer player;
  
  public final ContainerSyncHelper syncHelper;
  
  public int INVENTORY_START;
  
  public ContainerBase(TTE tile, EntityPlayer player)
  {
    this.tile = tile;
    this.player = player;
    
    syncHelper = new ContainerSyncHelper(this, listeners);
  }
  
  @Override
  public boolean canInteractWith(EntityPlayer player)
  {
    return !tile.hasFlags(TileFlags.HasOwner) || (tile.getOwner() == null || tile.getOwner().equals(player));
  
  }
  
  @Override
  public void detectAndSendChanges()
  {
    super.detectAndSendChanges();
    syncHelper.compareAll();
  }
  
  @Override
  public void updateProgressBar(int id, int data)
  {
    // update the data in the sync helper
    syncHelper.getUpdate(id, data);
  }
  
  protected void createPlayerInventorySlots(InventoryPlayer playerInv)
  {
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }
    
    for (int k = 0; k < 9; k++)
    {
      addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
    }
  }
  
  protected CustomSlot addCustomSlot(CustomSlot slot)
  {
    addSlotToContainer(slot);
    INVENTORY_START++;
    
    return slot;
  }
  
  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
  {
    ItemStack empty = ItemStack.EMPTY;
    
    int inventoryStart = INVENTORY_START;
    int inventoryEnd = inventoryStart + 26;
    int hotbarStart = inventoryEnd + 1;
    int hotbarEnd = hotbarStart + 8;
    
    Slot slot = inventorySlots.get(index);
    
    if (slot != null && slot.getHasStack())
    {
      ItemStack newStack = slot.getStack();
      ItemStack currentStack = newStack.copy();
      
      if (index >= inventoryStart)
      {
        SlotHandleState state = handleSpecialSlots(playerIn, newStack);
        
        if (state == SlotHandleState.FAILURE)
        {
          return empty;
        }
        else if (state == SlotHandleState.SUCCESS)
        {
          slot.onSlotChanged();
  
          if (currentStack.getCount() == newStack.getCount())
          {
            return empty;
          }
  
          slot.onTake(player, newStack);
  
          return currentStack;
        }
        else if (index <= inventoryEnd && !mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false))
        {
          return empty;
        }
        else if (index >= hotbarStart + 1 && index < hotbarEnd + 1 && !mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false))
        {
          return empty;
        }
      }
      else if (mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false))
      {
        return empty;
      }
      
      slot.onSlotChanged();
      
      if (currentStack.getCount() == newStack.getCount())
      {
        return empty;
      }
      
      slot.onTake(player, newStack);
      
      return currentStack;
    }
    
    return empty;
  }
  
  public SlotHandleState handleSpecialSlots(EntityPlayer player, ItemStack stack)
  {
    for (int i = 0; i < INVENTORY_START; i++)
    {
      Slot insert = inventorySlots.get(i);
      if (insert.isItemValid(stack))
      {
        return mergeItemStack(stack, i, i + 1, false) ? SlotHandleState.SUCCESS : SlotHandleState.FAILURE;
      }
    }
    
    return SlotHandleState.INVALID;
  }
  
  
  
}
