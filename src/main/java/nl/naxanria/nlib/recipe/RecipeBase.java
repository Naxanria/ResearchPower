package nl.naxanria.nlib.recipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;
import nl.naxanria.nlib.util.ReflectionUtil;

import java.lang.reflect.Field;

public abstract class RecipeBase extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
  private static final Field eventHandlerField = ReflectionHelper.findField(InventoryCrafting.class, "eventHandler");
  private static final Field containerPlayerPlayerField = ReflectionHelper.findField(ContainerPlayer.class, "player");
  private static final Field slotCraftingPlayerField = ReflectionHelper.findField(SlotCrafting.class, "player");
  
  protected int minWidth, minHeight, maxWidth, maxHeight;
  
  public RecipeBase(int size)
  {
    this(size, size, size, size);
  }
  
  public RecipeBase(int width, int height)
  {
    this(width, height, 0, 0);
  }
  
  public RecipeBase(int minWidth, int minHeight, int maxWidth, int maxHeight)
  {
    this.minWidth = minWidth;
    this.minHeight = minHeight;
    this.maxWidth = maxWidth;
    this.maxHeight = maxHeight;
  }
  
  @Override
  public boolean canFit(int width, int height)
  {
    return fit(width, minWidth, maxWidth) && fit(height, minHeight, maxHeight);
  }
  
  protected boolean fit(int val, int min, int max)
  {
    return val >= min && (max == 0 || val <= max);
  }
  
  @Override
  public final boolean matches(InventoryCrafting inv, World worldIn)
  {
    return canCraft(inv, worldIn) && doesMatch(inv, worldIn);
  }
  
  public abstract boolean doesMatch(InventoryCrafting inv, World world);
  
  public boolean canCraft(InventoryCrafting inv, World world)
  {
    return true;
  }
  
  protected Container getCraftingContainer(InventoryCrafting inv)
  {
    try
    {
      return (Container) eventHandlerField.get(inv);
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
    
    return null;
  }
  
  protected EntityPlayer getCraftingPlayer(InventoryCrafting inv)
  {
    Container container = getCraftingContainer(inv);
    return getCraftingPlayer(container);
  }
  
  protected EntityPlayer getCraftingPlayer(Container container)
  {
    if (container instanceof ContainerPlayer)
    {
      try
      {
        return (EntityPlayer) containerPlayerPlayerField.get(container);
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
    }
    else if (container instanceof ContainerWorkbench)
    {
      try
      {
        return (EntityPlayer) slotCraftingPlayerField.get(container.getSlot(0));
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      // try get a player field from the class
      try
      {
        EntityPlayer player = ReflectionUtil.getFirstField(container, EntityPlayer.class);
        if (player != null)
        {
          return player;
        }
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
    }
    
    return null;
  }
}
