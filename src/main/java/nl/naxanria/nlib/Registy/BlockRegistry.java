package nl.naxanria.nlib.Registy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.nlib.block.BlockTileBaseInternal;
import nl.naxanria.nlib.block.IBlockBase;

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

      if (b instanceof BlockTileBaseInternal)
      {
        ((BlockTileBaseInternal) b).registerTileEntity();
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
