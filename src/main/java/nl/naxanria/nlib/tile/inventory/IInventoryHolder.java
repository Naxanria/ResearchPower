package nl.naxanria.nlib.tile.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IInventoryHolder
{
  EnumFacing[] getInventorySides();
  
  boolean validForSlot(int slot, ItemStack stack);
}
