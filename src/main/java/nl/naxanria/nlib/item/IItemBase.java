package nl.naxanria.nlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public interface IItemBase
{
  public void registerItemModel();
  
  public Item setCreativeTab(CreativeTabs tab);
  
  public Item getItem();
}
