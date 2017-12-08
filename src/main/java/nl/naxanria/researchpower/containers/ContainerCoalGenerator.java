package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.researchpower.tile.machines.generators.TileEntityCoalGenerator;

import javax.annotation.Nonnull;

public class ContainerCoalGenerator extends ContainerBase<TileEntityCoalGenerator>
{
  public ContainerCoalGenerator(TileEntityCoalGenerator tile, EntityPlayer player)
  {
    super(tile, player);
    
    syncHelper.create
    (
      "BurnTime",
      (i) -> tile.currBurnTime = i,
      () -> tile.currBurnTime
    );
    
    syncHelper.create
    (
      "MaxBurnTime",
      (i) -> tile.maxBurnTime = i,
      () -> tile.maxBurnTime
    );
    
    syncHelper.create
    (
      "Energy",
      tile.storage::setEnergyStored,
      tile.storage::getEnergyStored
    );

    addSlotToContainer
    (
      new SlotItemHandler(tile.inventory, TileEntityCoalGenerator.SLOT_FUEL, 80, 35)
      {
       @Override
       public void onSlotChanged()
       {
         tile.markDirty();
       }
  
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack)
        {
          return tile.isItemValidForSlot(stack, TileEntityCoalGenerator.SLOT_FUEL);
        }
      }
    );
    
    INVENTORY_START = 1;
    
    createPlayerInventorySlots(player.inventory);
  }
  
//  @Override
//  public boolean handleSpecialSlots(EntityPlayer player, ItemStack stack, int index)
//  {
//    if (tile.isItemValidForSlot(stack, TileEntityCoalGenerator.SLOT_FUEL))
//    {
//      if (!mergeItemStack(stack, 0, 1, false))
//      {
//        return false;
//      }
//    }
//
//    return true;
//  }
  
  //  @Override
//  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
//  {
//    ItemStack empty = ItemStack.EMPTY;
//
//    int inventoryStart = 1;
//    int inventoryEnd = inventoryStart + 26;
//    int hotbarStart = inventoryEnd + 1;
//    int hotbarEnd = hotbarStart + 8;
//
//    Slot slot = inventorySlots.get(index);
//
//    if (slot != null && slot.getHasStack())
//    {
//      ItemStack newStack = slot.getStack();
//      ItemStack currentStack = newStack.copy();
//
//      if (index >= inventoryStart)
//      {
//        if (tile.isItemValidForSlot(newStack, TileEntityCoalGenerator.SLOT_FUEL))
//        {
//          if (!mergeItemStack(newStack, 0, 1, false))
//          {
//            return empty;
//          }
//        }
//        else if (index <= inventoryEnd && !mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false))
//        {
//          return empty;
//        }
//        else if (index >= hotbarStart + 1 && index < hotbarEnd + 1 && !mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false))
//        {
//          return empty;
//        }
//      }
//      else if (mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false))
//      {
//        return empty;
//      }
//
//      slot.onSlotChanged();
//
//      if (currentStack.getCount() == newStack.getCount())
//      {
//        return empty;
//      }
//
//      slot.onTake(player, newStack);
//
//      return currentStack;
//    }
//
//    return empty;
//  }
}
