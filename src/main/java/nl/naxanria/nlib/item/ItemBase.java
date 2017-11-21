package nl.naxanria.nlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import nl.naxanria.nlib.NMod;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.nlib.proxy.Proxy;

public class ItemBase extends Item implements IItemBase
{
  protected String name;
  
  public ItemBase(String name)
  {
    this.name = name;
    
    setUnlocalizedName(name);
    setRegistryName(name);
  
    setCreativeTab(NMod.getInstance().defaultTab());
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
  
  public Ingredient getAsIngredient()
  {
    return Ingredient.fromItem(this);
  }
}
