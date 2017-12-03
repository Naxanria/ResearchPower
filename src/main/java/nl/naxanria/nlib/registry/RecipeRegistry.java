package nl.naxanria.nlib.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import nl.naxanria.nlib.NMod;

import java.util.ArrayList;
import java.util.List;

public class RecipeRegistry extends Registry<IRecipe, IForgeRegistry<IRecipe>>
{
  public static final List<Block> BLOCK_NBT_CLEAR = new ArrayList<>();
  public static final List<Item> ITEM_NBT_CLEAR = new ArrayList<>();
  private int r = 0;
  
  public static RecipeRegistry instance;
  
  public RecipeRegistry()
  {
    instance = this;
  }
  
  @SuppressWarnings("ConstantConditions")
  public void registerNBTClearRecipes()
  {
    NonNullList<ItemStack> sub = NonNullList.create();
    
    for (Block b :
      BLOCK_NBT_CLEAR)
    {
      b.getSubBlocks(b.getCreativeTabToDisplayOn(), sub);
    }
    
    for (Item i :
      ITEM_NBT_CLEAR)
    {
      i.getSubItems(i.getCreativeTab(), sub);
    }
  
    for (ItemStack stack :
      sub)
    {
      instance.addShapeless(stack, Ingredient.fromStacks(stack));
    }
  }
  
  @Override
  public void register(IForgeRegistry<IRecipe> registry, IRecipe recipe)
  {
    registry.register(recipe);
  }
  
  public void addShapeless(ItemStack result, Ingredient... ingredients)
  {
    String name = NMod.getModId() + ":recipe_" + r++;
    GameRegistry.addShapelessRecipe
      (
        new ResourceLocation(name),
        new ResourceLocation(name),
        result,
        ingredients
      );
  }
  
  public void addShaped(ItemStack result, Object... params)
  {
    String name = NMod.getModId() + ":recipe_" + r++;
    GameRegistry.addShapedRecipe
      (
        new ResourceLocation(name),
        new ResourceLocation(name),
        result,
        params
      );
  }
}
