package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.researchpower.tile.machines.TileEntityCoalGenerator;

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
      }
    );
    
    createPlayerInventorySlots(player.inventory);
  }
  
  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
  {
    return super.transferStackInSlot(playerIn, index);
  }
}
