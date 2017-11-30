package nl.naxanria.researchpower.recipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.nlib.util.logging.LogColor;

import java.util.HashMap;

public class RecipeMiniature
{
  private ItemStack output;
  
  private HashMap<Character, IBlockState> patternMap = new HashMap<>();
  private String pattern;
  
  public RecipeMiniature(ItemStack output,
                         String line0, String line1, String line2,
                         String line3, String line4, String line5,
                         String line6, String line7, String line8,
                         Object... arguments)
  {
    this.output = output;
    
    pattern = line0 + line1 + line2 + line3 + line4 + line5 + line6 + line7 + line8;
    
    for (int i = 0; i < arguments.length - 1; i += 2)
    {
      char c = (char) arguments[i];
      Object obj = arguments[i + 1];
      IBlockState b;
  
      if (obj instanceof IBlockState)
      {
        b = (IBlockState) obj;
      }
      else if (obj instanceof Block)
      {
        b = ((Block) obj).getDefaultState();
      }
      else
      {
        Log.error("Given argument is not a " + LogColor.CYAN.CODE + "Block" + LogColor.RED.CODE + " or a " + LogColor.CYAN.CODE + " IBlockState " + LogColor.RED.CODE + " But found instead: " + LogColor.PURPLE.CODE + obj.getClass().getName());
        continue;
      }
      
      patternMap.put(c, b);
    }
  }
  
  public boolean matches(IBlockState[] blocks, EntityPlayer player)
  {
    for (int i = 0; i < pattern.length(); i++)
    {
      char c = pattern.charAt(i);
      if (c == ' ')
      {
        continue;
      }
      IBlockState b = patternMap.getOrDefault(c, null);
      if (b != blocks[i])
      {
        return false;
      }
    }
    
    return true;
  }
  
  public ItemStack getCraftingResult()
  {
    return output;
  }
  
  public ItemStack getRecipeOutput()
  {
    return output.copy();
  }
}
