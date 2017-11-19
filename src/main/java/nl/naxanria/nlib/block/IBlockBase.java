package nl.naxanria.nlib.block;


import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IBlockBase
{
  public void registerItemModel(Item itemBlock);
  
  public void registerItemModel();
  
  public Item createItemBlock();
  
  public Block getBlock();
}
