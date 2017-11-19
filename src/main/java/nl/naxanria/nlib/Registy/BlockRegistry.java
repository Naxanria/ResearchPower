package nl.naxanria.nlib.Registy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.nlib.block.IBlockBase;

import java.util.ArrayList;

public class BlockRegistry extends Registry<IBlockBase, IForgeRegistry<Block>>
{
  @Override
  public void register(IForgeRegistry<Block> registry, IBlockBase iBlockBase)
  {
    registry.register(iBlockBase.getBlock());
  }
  
  public void registerItemBlocks(IForgeRegistry<Item> registry)
  {
    for (IBlockBase b :
      toRegister)
    {
      registry.register(b.createItemBlock());

      if (b instanceof BlockTileBase)
      {
        ((BlockTileBase) b).registerTileEntity();
      }
    }
  }
  
  public void registerModels()
  {
    for (IBlockBase b :
      toRegister)
    {
      b.registerItemModel();
    }
  }
}
