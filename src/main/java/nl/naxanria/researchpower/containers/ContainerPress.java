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
import nl.naxanria.researchpower.tile.machines.TileEntityPress;

public class ContainerPress extends ContainerBase<TileEntityPress>
{
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
  
    addSlotToContainer(new SlotItemHandler(inventory, 0, 45, 35)
    {
      @Override
      public void onSlotChanged()
      {
        tile.markDirty();
      }
    });
  
    addSlotToContainer(new SlotItemHandler(inventory, 1, 115, 35)
    {
      @Override
      public void onSlotChanged()
      {
        tile.markDirty();
      }
    });
  }
}
