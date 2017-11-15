package nl.naxanria.researchpower.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class BlocksRegistry
{
  private static ArrayList<IBlockBase> toRegister = new ArrayList<>();
  
  static void addBlock(IBlockBase block)
  {
    toRegister.add(block);
  }
  
  public static void register(IForgeRegistry<Block> registry)
  {
    for (IBlockBase b :
      toRegister)
    {
      registry.register(b.getBlock());
    }
  }

  public static void registerItemBlocks(IForgeRegistry<Item> registry)
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

  public static void registerModels()
  {
    for (IBlockBase b :
      toRegister)
    {
      b.registerItemModel();
    }
  }
}
