package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.nlib.gui.PropertiesFactory;
import nl.naxanria.nlib.inventory.ItemStackHandlerBase;
import nl.naxanria.researchpower.tile.machines.TileEntityEmpowerer;

public class ContainerEmpowerer extends ContainerBase<TileEntityEmpowerer>
{
  public ContainerEmpowerer(TileEntityEmpowerer tile, EntityPlayer player)
  {
    super(tile, player);
    
    syncHelper.create
    (
      "Energy",
      tile.storage::setEnergyStored,
      tile.storage::getEnergyStored
    );
    
    syncHelper.create
    (
      "Progress",
      (i) -> tile.progress = i,
      () -> tile.progress
    );
    
    syncHelper.create
    (
      "TotalTime",
      (i) -> tile.totalTime = i,
      () -> tile.totalTime
    );
    
    ItemStackHandlerBase inventory = tile.inventory;
    
    int x = 45;
    int y = 20;
    int step = 2 * 18;
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityEmpowerer.SLOT_INPUT_MINOR_0, x - step, y)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityEmpowerer.SLOT_INPUT_MINOR_1, x, y - step)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityEmpowerer.SLOT_INPUT_MINOR_2, x + step, y)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityEmpowerer.SLOT_INPUT_MINOR_3, x, y + step)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityEmpowerer.SLOT_INPUT_MAYOR, x, y)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
  
    addSlotToContainer
      (
        new SlotItemHandler(inventory, TileEntityEmpowerer.SLOT_OUTPUT, x + step * 3, y)
        {
          @Override
          public void onSlotChanged()
          {
            tile.markDirty();
          }
        }
      );
    
    createPlayerInventorySlots(player.inventory);
  }
  
  
}
