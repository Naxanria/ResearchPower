package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.researchpower.recipe.registry.PressRecipeRegistry;
import nl.naxanria.researchpower.tile.machines.TileEntityPress;

import javax.annotation.Nonnull;

public class ContainerPress extends ContainerBase<TileEntityPress>
{
  
  protected Slot input;
  protected Slot output;
  
  public ContainerPress(TileEntityPress tile, EntityPlayer player)
  {
    super(tile, player);
  
    IItemHandler inventory = tile.inventory;
    
    syncHelper.create
    (
      "Energy",
      tile.storage::setEnergyStored,
      tile.storage::getEnergyStored
    );
    
    syncHelper.create
    (
      "Progress",
      tile::setProgress,
      tile::getProgress
    );
  
    input = addSlotToContainer(new SlotItemHandler(inventory, TileEntityPress.SLOT_INPUT, 45, 35)
    {
      @Override
      public void onSlotChanged()
      {
        tile.markDirty();
      }
  
      @Override
      public boolean isItemValid(@Nonnull ItemStack stack)
      {
        return tile.validForSlot(TileEntityPress.SLOT_INPUT, stack);
      }
    });
  
    output = addSlotToContainer(new SlotItemHandler(inventory, TileEntityPress.SLOT_OUTPUT, 115, 35)
    {
      @Override
      public void onSlotChanged()
      {
        tile.markDirty();
      }
  
      @Override
      public boolean isItemValid(@Nonnull ItemStack stack)
      {
        return tile.validForSlot(TileEntityPress.SLOT_OUTPUT, stack);
      }
    });
    
    createPlayerInventorySlots(player.inventory);
  }
  
  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
  {
    int inventoryStart = 2;
    int inventoryEnd = inventoryStart + 26;
    int hotbarStart = inventoryEnd + 1;
    int hotbarEnd = hotbarStart + 8;
    
    ItemStack empty = ItemStack.EMPTY;
    
    Slot slot = inventorySlots.get(index);
    
    // want to move items?
    if (slot != null && slot.getHasStack())
    {
      ItemStack newStack = slot.getStack();
      ItemStack currentStack = newStack.copy();
      
      if (index >= inventoryStart)
      {
        if (PressRecipeRegistry.getRecipeFromInput(newStack) != null)
        {
          if (!mergeItemStack(newStack, TileEntityPress.SLOT_INPUT, TileEntityPress.SLOT_INPUT + 1, false))
          {
            return empty;
          }
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
}
