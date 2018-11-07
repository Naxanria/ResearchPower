package nl.naxanria.nlib.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemUtil
{
  public static int getBurnValue(Item item)
  {
    return TileEntityFurnace.getItemBurnTime(new ItemStack(item));
  }
  
  public static boolean hasBurnValue(Item item)
  {
    return getBurnValue(item) > 0;
  }
  
}
