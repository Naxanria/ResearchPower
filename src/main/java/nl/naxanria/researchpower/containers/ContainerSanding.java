package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.researchpower.tile.machines.TileEntityMachineSanding;

import javax.annotation.Nonnull;

public class ContainerSanding extends ContainerBase<TileEntityMachineSanding>
{
  @SuppressWarnings("ConstantConditions")
  public ContainerSanding(TileEntityMachineSanding tile, EntityPlayer player)
  {
    super(tile, player);
    
    syncHelper.create(
      "power",
      tile.storage::setEnergyStored,
      tile.storage::getEnergyStored
    );
  
    syncHelper.create(
      "sandAmount",
      (i) -> tile.sandAmount = i,
      () -> tile.sandAmount
    );
  
    syncHelper.create(
      "sandBuffer",
      (i) -> tile.sandBuffer = i,
      () -> tile.sandBuffer
    );
    
    syncHelper.create(
      "water",
      (i) -> tile.tank.getContents().amount = i,
      tile.tank::getFluidAmount
    );
    
    syncHelper.create(
      "progress",
      (i) -> tile.progress = i,
      () -> tile.progress
    );
    
    syncHelper.create(
      "totalTime",
      (i) -> tile.totalTime = i,
      () -> tile.totalTime
    );
    
    addSlotToContainer
      (
        new SlotItemHandler(tile.sandInput, 0, 80, 20)
        {
          @Override
          public boolean isItemValid(@Nonnull ItemStack stack)
          {
            return tile.validForSlot(TileEntityMachineSanding.SLOT_SAND, stack);
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
        new SlotItemHandler(tile.itemInput, 0, 50, 20)
        {
          @Override
          public boolean isItemValid(@Nonnull ItemStack stack)
          {
            return tile.validForSlot(TileEntityMachineSanding.SLOT_INPUT, stack);
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
        new SlotItemHandler(tile.itemOutput, 0, 50, 60)
        {
          @Override
          public boolean isItemValid(@Nonnull ItemStack stack)
          {
            return tile.validForSlot(TileEntityMachineSanding.SLOT_OUTPUT, stack);
          }
  
          @Override
          public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_)
          {
            tile.markDirty();
            super.onSlotChange(p_75220_1_, p_75220_2_);
          }
        }
      );
    
    INVENTORY_START = 3;
    
    createPlayerInventorySlots(player.inventory);
  }
  
  
}
