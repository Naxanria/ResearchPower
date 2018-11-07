package nl.naxanria.nlib.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import nl.naxanria.nlib.tile.TileEntityBase;

import java.util.function.Function;

public class CustomSlot extends Slot
{
  private TileEntityBase tile;
  protected Function<ItemStack, Boolean> isValid;
  
  public CustomSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, TileEntityBase tile, Function<ItemStack, Boolean> isValid)
  {
    super(inventoryIn, index, xPosition, yPosition);
    this.tile = tile;
    this.isValid = isValid;
  }
  
  @Override
  public boolean isItemValid(ItemStack stack)
  {
    return isValid.apply(stack);
  }
  
  @Override
  public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_)
  {
    tile.markDirty();
    super.onSlotChange(p_75220_1_, p_75220_2_);
  }
}
