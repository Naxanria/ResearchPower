package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.researchpower.tile.machines.TileEntityPress;

public class ContainerPress extends Container
{
  public TileEntityPress entityPress;
  
  public ContainerPress(InventoryPlayer playerInv, final TileEntityPress press)
  {
    IItemHandler inventory = press.inventory;
    entityPress = press;
    
    addSlotToContainer(new SlotItemHandler(inventory, 0, 80, 35)
    {
      @Override
      public void onSlotChanged()
      {
        press.markDirty();
      }
    });
    
    createPlayerInventorySlots(playerInv);
  
    Log.warn("Container is created");
  }
  
  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
  {
    return super.transferStackInSlot(playerIn, index);
  }
  
  @Override
  public boolean canInteractWith(EntityPlayer player)
  {
    if (entityPress.hasFlags(TileEntityBase.Flags.HasOwner))
    {
      return entityPress.getOwnerID().equals(player.getUniqueID());
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
