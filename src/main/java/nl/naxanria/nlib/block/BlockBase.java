package nl.naxanria.nlib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.nlib.proxy.Proxy;

public class BlockBase extends Block implements IBlockBase
{
  protected String name;
  
  public BlockBase(Material blockMaterialIn, String name)
  {
    super(blockMaterialIn);
    
    this.name = name;
    
    setUnlocalizedName(name);
    setRegistryName(name);
    
    setCreativeTab(ResearchPower.tab);
  }
  
  @Override
  public BlockBase setCreativeTab(CreativeTabs tab)
  {
    super.setCreativeTab(tab);
    return this;
  }
  
  public void registerItemModel(Item itemBlock)
  {
    Proxy.registerItemRender(itemBlock, 0, name);
  }
  
  public void registerItemModel()
  {
    Proxy.registerItemRender(Item.getItemFromBlock(this), 0, name);
  }
  
  public Item createItemBlock()
  {
    return new ItemBlock(this).setRegistryName(getRegistryName());
  }
  
  @Override
  public BlockBase getBlock()
  {
    return this;
  }
}
