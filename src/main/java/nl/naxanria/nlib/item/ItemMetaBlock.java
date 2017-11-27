package nl.naxanria.nlib.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemMetaBlock extends ItemBlock
{
  public ItemMetaBlock(Block block)
  {
    super(block);
    setHasSubtypes(true);
  }

  public int getMetadata(int damage)
  {
    return damage;
  }

/*  public String getUnlocalizedName(ItemStack stack)
  {
    return super.getUnlocalizedName() + "." + stack.getMetadata();
  }*/ //TODO: See if neccessary
}
