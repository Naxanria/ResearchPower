package nl.naxanria.nlib.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;

public abstract class ContainerBase<TTE extends TileEntityBase> extends Container
{
  public final TTE tile;
  public final EntityPlayer player;
  
  public final ContainerSyncHelper syncHelper;
  
  public ContainerBase(TTE tile, EntityPlayer player)
  {
    this(tile, player, true);
  }
  
  public ContainerBase(TTE tile, EntityPlayer player, boolean generatePlayerSlots)
  {
    this.tile = tile;
    this.player = player;
    
    syncHelper = new ContainerSyncHelper(this, listeners);
    
    if (generatePlayerSlots)
    {
      createPlayerInventorySlots(player.inventory);
    }
  }
  
  @Override
  public boolean canInteractWith(EntityPlayer player)
  {
    return !tile.hasFlags(TileFlags.HasOwner) || tile.getOwner().equals(player);
  
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
}
