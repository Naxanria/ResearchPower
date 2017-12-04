package nl.naxanria.nlib.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.nlib.block.BlockTileBaseInternal;
import nl.naxanria.nlib.block.IBlockBase;
import nl.naxanria.nlib.util.logging.Log;

public class BlockRegistry extends Registry<IBlockBase, IForgeRegistry<Block>>
{
  @Override
  public void register(IForgeRegistry<Block> registry, IBlockBase iBlockBase)
  {
    if (iBlockBase == null)
    {
      Log.error("Tried to register a block that is null");
      
      new Exception().printStackTrace();
      return;
    }
    
    registry.register(iBlockBase.getBlock());
  
    if (iBlockBase instanceof BlockBase)
    {
      if (((BlockBase) iBlockBase).needsNBTClearRecipe())
      {
        RecipeRegistry.BLOCK_NBT_CLEAR.add((Block) iBlockBase);
      }
    }
    
  }
  
  public void registerItemBlocks(IForgeRegistry<Item> registry)
  {
    for (IBlockBase b :
      toRegister)
    {
      if (b == null)
      {
        continue;
      }
      
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
