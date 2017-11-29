package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.researchpower.tile.machines.TileEntityCoalGenerator;

public class ContainerCoalGenerator extends ContainerBase<TileEntityCoalGenerator>
{
  public static final int ENERGY_SYNC = 0;
  public static final int BURN_SYNC = 1;
  public static final int MAX_BURN_SYNC = 2;
  
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
  }
}
