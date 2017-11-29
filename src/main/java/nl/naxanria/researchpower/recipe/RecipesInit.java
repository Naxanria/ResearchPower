package nl.naxanria.researchpower.recipe;

import net.minecraft.init.Blocks;
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
  private static final Item LAPIS = Dye.getDyeItem(EnumDyeColor.BLUE);
  
  public static class Press
  {
    public static final RecipePress LAPIS_WAFER = new RecipePress
    (
      new ItemStack(LAPIS, 2),
      new ItemStack(ItemsInit.MachineParts.LAPIS_WAFER),
      120
    );
    
    public static final RecipePress LAPIS_WAFER_2 = new RecipePress
    (
      new ItemStack(Blocks.LAPIS_BLOCK),
      new ItemStack(ItemsInit.MachineParts.LAPIS_WAFER, 5),
      240
    );
  }
  
  public static class Miniature
  {
    public static final RecipeMiniature RAINBOW = new RecipeMiniature(
      new ItemStack(BlocksInit.Other.RAINBOW),
      "RLR", "LRL", "RRR",
      "LRL", "RLR", "LRL",
      "LLL", "LRL", "RLR",
      'R', Blocks.REDSTONE_BLOCK,
      'L', Blocks.LAPIS_BLOCK
    );
  }
  
  private static int r = 0;
  
  public static void init(RecipeRegistry registry)
  {
    // because we call this with reflection and errors often get eaten, I'm going to wrap this in a try catch
    try
    {
      GameRegistry.addSmelting
        (
          BlocksInit.Ores.COPPER_ORE,
          new ItemStack(ItemsInit.Metals.INGOT_COPPER),
          0.5f
        );

      addShapeless(
        new ItemStack(BlocksInit.Metals.COPPER_BLOCK),
        Ingredient.fromStacks(new ItemStack(ItemsInit.Metals.INGOT_COPPER, 9))
      );

      addShaped(
        new ItemStack(BlocksInit.Metals.COPPER_BLOCK),
        "AAA",
        "AAA",
        "AAA",
        'A', new ItemStack(ItemsInit.Metals.INGOT_COPPER)
      );

      addShaped(
        new ItemStack(ItemsInit.MachineParts.REDSTONE_CONNECTOR_HORIZONTAL),
        "RRR",
        "LIL",
        "RRR",
        'R', Items.REDSTONE,
        'L', LAPIS,
        'I', Items.IRON_INGOT
      );

      addShaped(
        new ItemStack(ItemsInit.MachineParts.REDSTONE_CONNECTOR_VERTICAL),
        "RLR",
        "RIR",
        "RLR",
        'R', Items.REDSTONE,
        'L', LAPIS,
        'I', Items.IRON_INGOT
      );

      addShaped(
        new ItemStack(ItemsInit.MachineParts.REDSTONE_RING),
        "BHB",
        "V V",
        "BHB",
        'B', net.minecraft.init.Blocks.REDSTONE_BLOCK,
        'H', ItemsInit.MachineParts.REDSTONE_CONNECTOR_HORIZONTAL,
        'V', ItemsInit.MachineParts.REDSTONE_CONNECTOR_VERTICAL
      );

      addShaped(
        new ItemStack(BlocksInit.Machines.MACHINE_FRAME),
        "ICI",
        "C C",
        "ICI",
        'I', Items.IRON_INGOT,
        'C', ItemsInit.Metals.INGOT_COPPER
      );

      addShaped(
        new ItemStack(BlocksInit.Machines.MACHINE_PRESS),
        "IPI",
        "RFR",
        "IPI",
        'I', Items.IRON_INGOT,
        'P', Blocks.PISTON,
        'R', ItemsInit.MachineParts.REDSTONE_RING,
        'F', BlocksInit.Machines.MACHINE_FRAME
      );

      addShaped(
        new ItemStack(BlocksInit.Machines.SOLAR_GENERATOR, 1, 0),
        "WWW",
        "wRw",
        "www",
        'W', ItemsInit.MachineParts.LAPIS_WAFER,
        'w', Blocks.PLANKS,
        'R', Items.REDSTONE
      );

      addShaped(
        new ItemStack(BlocksInit.Machines.SOLAR_GENERATOR, 1, 1),
        "111",
        "1F1",
        "111",
        '1', (new ItemStack(BlocksInit.Machines.SOLAR_GENERATOR, 1, 0).getItem()),
        'F', BlocksInit.Machines.MACHINE_FRAME
      );

      PressRecipeRegistry.addAll
        (
          Press.LAPIS_WAFER,
          Press.LAPIS_WAFER_2
        );

      MiniatureRecipeRegistry.addAll(
        Miniature.RAINBOW
      );
    }
    catch(Throwable t)
    {
      t.printStackTrace();
      throw t;
    }
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
