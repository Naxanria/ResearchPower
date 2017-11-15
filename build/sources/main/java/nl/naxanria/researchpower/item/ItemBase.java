package nl.naxanria.researchpower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.researchpower.proxy.Proxy;

public class ItemBase extends Item implements IItemBase
{
  protected String name;
  
  public ItemBase(String name)
  {
    this.name = name;
    
    setUnlocalizedName(name);
    setRegistryName(name);
    
    setCreativeTab(ResearchPower.tab);
  }
  
  public void registerItemModel()
  {
    Proxy.registerItemRender(this, 0, name);
  }
  
  @Override
  public ItemBase setCreativeTab(CreativeTabs tab)
  {
    super.setCreativeTab(tab);
    return this;
  }
  
  @Override
  public ItemBase getItem()
  {
    return this;
  }
}
