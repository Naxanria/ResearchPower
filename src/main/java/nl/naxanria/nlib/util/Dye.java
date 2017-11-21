package nl.naxanria.nlib.util;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Dye
{
  public static Item getDyeItem(EnumDyeColor color)
  {
    return new ItemStack(Items.DYE, 1, color.getMetadata()).getItem();
  }
}
