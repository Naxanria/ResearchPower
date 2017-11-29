package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.researchpower.tile.machines.TileEntityPress;

public class ContainerPress extends Container
{
  public TileEntityPress entityPress;

  private static final int ENERGY_SYNC = 0;
  private static final int PROGRESS_SYNC = 1;
  
  private int energyValue;
  private int progress;
  
  public ContainerPress(InventoryPlayer playerInv, final TileEntityPress press)
  {
    IItemHandler inventory = press.inventory;
    entityPress = press;
  
    addSlotToContainer(new SlotItemHandler(inventory, 0, 45, 35)
    {
      @Override
      public void onSlotChanged()
      {
        press.markDirty();
      }
    });
  
    addSlotToContainer(new SlotItemHandler(inventory, 1, 115, 35)
    {
      @Override
      public void onSlotChanged()
      {
        press.markDirty();
      }
    });
    
    createPlayerInventorySlots(playerInv);
  }


  @Override
  public void detectAndSendChanges()
  {
    super.detectAndSendChanges();

    for (int i = 0; i < listeners.size(); ++i)
    {
      IContainerListener containerListener = listeners.get(i);
      if (energyValue != entityPress.storage.getEnergyStored())
      {
        containerListener.sendWindowProperty(this, ENERGY_SYNC, entityPress.storage.getEnergyStored());
      }
      if (progress != entityPress.getProgress())
      {
        containerListener.sendWindowProperty(this, PROGRESS_SYNC, entityPress.getProgress());
      }
      
    }

    energyValue = entityPress.storage.getEnergyStored();
    progress = entityPress.getProgress();
  }

  @Override
  public void updateProgressBar(int id, int data)
  {
    switch(id)
    {
      case ENERGY_SYNC:
        entityPress.storage.setEnergyStored(data);
        break;
        
      case PROGRESS_SYNC:
        entityPress.setProgress(data);
        break;
        
      default:
        break;
    }
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
  {
    return super.transferStackInSlot(playerIn, index);
  }
  
  @Override
  public boolean canInteractWith(EntityPlayer player)
  {
    if (entityPress.hasFlags(TileFlags.HasOwner))
    {
      return entityPress.getOwner().equals(player);
    }

    return true;
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
}
