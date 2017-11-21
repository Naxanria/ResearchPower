package nl.naxanria.researchpower.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.Registy.RecipeRegistry;
import nl.naxanria.nlib.util.Dye;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.item.ItemsInit;

public class RecipesInit
{
  private static int r = 0;
  
  public static void init(RecipeRegistry registry)
  {
    final Item LAPIS = Dye.getDyeItem(EnumDyeColor.BLUE);

    GameRegistry.addSmelting
    (
      BlocksInit.Ores.copperOre,
      new ItemStack(ItemsInit.Metals.ingotCopper),
      0.5f
    );
    
    addShapeless(
      new ItemStack(BlocksInit.Metals.copperBlock),
      Ingredient.fromStacks(new ItemStack(ItemsInit.Metals.ingotCopper, 9))
    );
    
    addShaped(
      new ItemStack(BlocksInit.Metals.copperBlock),
      "AAA",
      "AAA",
      "AAA",
      'A', new ItemStack(ItemsInit.Metals.ingotCopper)
    );
  
    addShaped(
      new ItemStack(ItemsInit.MachineParts.redstoneConnectorHorizontal),
      "RRR",
      "LIL",
      "RRR",
      'R' , Items.REDSTONE,
      'L', LAPIS,
      'I', Items.IRON_INGOT
    );
  
    addShaped(
      new ItemStack(ItemsInit.MachineParts.redstoneConnectorVertical),
      "RLR",
      "RIR",
      "RLR",
      'R' , Items.REDSTONE,
      'L', LAPIS,
      'I', Items.IRON_INGOT
    );
    
    addShaped(
      new ItemStack(ItemsInit.MachineParts.redstoneRing),
      "BHB",
      "V V",
      "BHB",
      'B', net.minecraft.init.Blocks.REDSTONE_BLOCK,
      'H', ItemsInit.MachineParts.redstoneConnectorHorizontal,
      'V', ItemsInit.MachineParts.redstoneConnectorVertical
    );

    registry.addAll
    (
    
    );
  }
  
  private static void addShapeless(ItemStack result, Ingredient... ingredients)
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
  
  private static void addShaped(ItemStack result, Object... params)
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
