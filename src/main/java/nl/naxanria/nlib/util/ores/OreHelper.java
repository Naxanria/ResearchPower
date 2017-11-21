package nl.naxanria.nlib.util.ores;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.nlib.item.ItemBase;

public class OreHelper
{
  public static final String PFX_ORE = "ore";
  public static final String PFX_FULL_BLOCK = "block";
  public static final String PFX_INGOT = "ingot";
  public static final String PFX_NUGGET = "nugget";
  public static final String PFX_DUST = "dust";
  public static final String PFX_PLATE = "plate";

  public static void registerInOreDict(OreBuilder builder)
  {
    registerIfNotNull(PFX_ORE + builder.name, builder.ore);
    registerIfNotNull(PFX_FULL_BLOCK + builder.name, builder.fullBlock);
    registerIfNotNull(PFX_INGOT + builder.name, builder.ingot);
    registerIfNotNull(PFX_NUGGET + builder.name, builder.nugget);
    registerIfNotNull(PFX_DUST + builder.name, builder.dust);
    registerIfNotNull(PFX_PLATE + builder.name, builder.plate);
  }
  
  public static void registerIfNotNull(String name, BlockBase block)
  {
    if (block != null)
    {
      registerIfNotNull(name, new ItemStack(block));
    }
  }
  
  public static void registerIfNotNull(String name, ItemBase item)
  {
    if (item != null)
    {
      registerIfNotNull(name, new ItemStack(item));
    }
  }
  
  public static void registerIfNotNull(String name, ItemStack stack)
  {
    if (stack != null && !stack.isEmpty())
    {
      OreDictionary.registerOre(name, stack);
    }
  }
  
  
  
  
}
