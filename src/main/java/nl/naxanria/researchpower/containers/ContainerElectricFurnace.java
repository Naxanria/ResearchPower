package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.researchpower.tile.machines.furnace.TileEntityElectricFurnace;

import javax.annotation.Nonnull;

public class ContainerElectricFurnace extends ContainerBase<TileEntityElectricFurnace>
{
  public ContainerElectricFurnace(TileEntityElectricFurnace tile, EntityPlayer player)
  {
    super(tile, player);
  
    syncHelper.create
      (
        "progress1",
        (i) -> tile.module1.progress = i,
        () -> tile.module1.progress
      );
  
    syncHelper.create
      (
        "total1",
        (i) -> tile.module1.total = i,
        () -> tile.module1.total
      );
  
    syncHelper.create
      (
        "progress2",
        (i) -> tile.module2.progress = i,
        () -> tile.module2.progress
      );
  
    syncHelper.create
      (
        "total2",
        (i) -> tile.module2.total = i,
        () -> tile.module2.total
      );
  
    syncHelper.create
      (
        "power",
        tile.storage::setEnergyStored,
        tile.storage::getEnergyStored
      );
  
    addSlotToContainer
      (
        new SlotItemHandler(tile.module1.input, 0, 40, 10)
        {
          @Override
          public boolean isItemValid(@Nonnull ItemStack stack)
          {
            return tile.validForSlot(TileEntityElectricFurnace.SLOT_MODULE_0_INPUT, stack);
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
        new SlotItemHandler(tile.module1.output, 0, 40, 51)
        {
          @Override
          public boolean isItemValid(@Nonnull ItemStack stack)
          {
            return tile.validForSlot(TileEntityElectricFurnace.SLOT_MODULE_0_OUTPUT, stack);
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
        new SlotItemHandler(tile.module2.input, 0, 80, 10)
        {
          @Override
          public boolean isItemValid(@Nonnull ItemStack stack)
          {
            return tile.validForSlot(TileEntityElectricFurnace.SLOT_MODULE_1_INPUT, stack);
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
        new SlotItemHandler(tile.module2.output, 0, 80, 51)
        {
          @Override
          public boolean isItemValid(@Nonnull ItemStack stack)
          {
            return tile.validForSlot(TileEntityElectricFurnace.SLOT_MODULE_1_OUTPUT, stack);
          }
        
          @Override
          public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_)
          {
            tile.markDirty();
            super.onSlotChange(p_75220_1_, p_75220_2_);
          }
        }
      );
      
    INVENTORY_START = 4;
    
    createPlayerInventorySlots(player.inventory);
  }
}
