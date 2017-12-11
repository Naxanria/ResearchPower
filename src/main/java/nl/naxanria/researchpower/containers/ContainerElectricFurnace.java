package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.researchpower.tile.machines.furnace.SmeltModule;
import nl.naxanria.researchpower.tile.machines.furnace.TileEntityElectricFurnace;

import javax.annotation.Nonnull;

public class ContainerElectricFurnace extends ContainerBase<TileEntityElectricFurnace>
{
  public ContainerElectricFurnace(TileEntityElectricFurnace tile, EntityPlayer player)
  {
    super(tile, player);
    
    int c = tile.moduleCount;
    int spacing = 160 / c;
    
    int xStart = spacing + 6;
    
    for (int i = 0; i < tile.moduleCount; i++)
    {
      SmeltModule m = tile.modules.get(i);
      
      int x = xStart + spacing * i - (spacing / 2) - 9;
  
      // syncing
      
      syncHelper.create
        (
          "progress" + i,
          (n) -> m.progress = n,
          () -> m.progress
        );
  
      syncHelper.create
        (
          "total" + i,
          (n) -> m.total = n,
          () -> m.total
        );
      
      // inventory slots
  
      addSlotToContainer
        (
          new SlotItemHandler(m.input, 0, x, 10)
          {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack)
            {
              return tile.validForSlot(0, stack);
            }
        
            @Override
            public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_)
            {
              tile.markDirty();
              super.onSlotChange(p_75220_1_, p_75220_2_);
            }
          }
        );
  
      addSlotToContainer
        (
          new SlotItemHandler(m.output, 0, x, 51)
          {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack)
            {
              return tile.validForSlot(1, stack);
            }
        
            @Override
            public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_)
            {
              tile.markDirty();
              super.onSlotChange(p_75220_1_, p_75220_2_);
            }
          }
        );
    }
  
    syncHelper.create
      (
        "power",
        tile.storage::setEnergyStored,
        tile.storage::getEnergyStored
      );
    
    INVENTORY_START = c * 2;
    
    createPlayerInventorySlots(player.inventory);
  }
}
