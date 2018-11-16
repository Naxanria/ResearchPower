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
import nl.naxanria.nlib.registry.RecipeRegistry;
import nl.naxanria.nlib.util.Dye;
import nl.naxanria.nlib.util.Numbers;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.item.ItemsInit;
import nl.naxanria.researchpower.recipe.registry.EmpowererRecipeRegistry;
import nl.naxanria.researchpower.recipe.registry.MiniatureRecipeRegistry;
import nl.naxanria.researchpower.recipe.registry.PressRecipeRegistry;
import nl.naxanria.researchpower.recipe.registry.SandingRecipeRegistry;
import nl.naxanria.researchpower.tile.machines.TileEntityMachineSanding;

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
    
    public static final RecipePress GLOWSTONE_WAFER = new RecipePress
    (
      new ItemStack(Blocks.GLOWSTONE),
      new ItemStack(ItemsInit.MachineParts.GLOWSTONE_WAFER, 2),
      120
    );
  }
  
  public static class Empowerer
  {
    public static final RecipeEmpowerer STONE_DRUM = new RecipeEmpowerer
    (
      new ItemStack(BlocksInit.FLUID_DRUM, 1, 0),
      new ItemStack(Blocks.STONE),
      new ItemStack(Blocks.STONE),
      new ItemStack(Blocks.STONE_SLAB),
      new ItemStack(Blocks.STONE_SLAB),
      new ItemStack(Items.CAULDRON),
      1000
    ).setDuration(15);
    
    public static final RecipeEmpowerer METAL_DRUM = new RecipeEmpowerer
    (
      new ItemStack(BlocksInit.FLUID_DRUM, 1, 1),
      new ItemStack(ItemsInit.Metals.INGOT_COPPER),
      new ItemStack(BlocksInit.Metals.COPPER_BLOCK),
      new ItemStack(ItemsInit.Metals.INGOT_COPPER),
      new ItemStack(BlocksInit.Metals.COPPER_BLOCK),
      new ItemStack(BlocksInit.FLUID_DRUM),
      Numbers.K(100)
    ).setDuration(40);
    
    public static final RecipeEmpowerer OBSIDIAN_DRUM = new RecipeEmpowerer
    (
      new ItemStack(BlocksInit.FLUID_DRUM, 1, 2),
      new ItemStack(BlocksInit.FLUID_DRUM, 1, 1),
      new ItemStack(BlocksInit.FLUID_DRUM, 1, 1),
      new ItemStack(Blocks.OBSIDIAN),
      new ItemStack(Blocks.OBSIDIAN),
      new ItemStack(BlocksInit.FLUID_DRUM, 1, 0),
      Numbers.M(10)
    ).setDuration(250);
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
  
  public static class Sanding
  {
    public static final RecipeSanding CLEANED_SAND = new RecipeSanding
      (
        new ItemStack(Blocks.SAND),
        new ItemStack(BlocksInit.Other.CLEANED_SAND),
        TileEntityMachineSanding.getSandAmount(BlocksInit.Other.CLEANED_SAND) - TileEntityMachineSanding.getSandAmount(Blocks.SAND),
        250,
        10
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
      
      GameRegistry.addSmelting
      (
        BlocksInit.Other.CLEANED_SAND,
        new ItemStack(BlocksInit.Other.GLASS_FOCUS),
        0.5f
      );

      registry.addShapeless(
        new ItemStack(ItemsInit.Metals.INGOT_COPPER, 9),
        Ingredient.fromStacks(new ItemStack(BlocksInit.Metals.COPPER_BLOCK))
      );
  
      registry.addShaped(
        new ItemStack(BlocksInit.Metals.COPPER_BLOCK),
        "AAA",
        "AAA",
        "AAA",
        'A', new ItemStack(ItemsInit.Metals.INGOT_COPPER)
      );
  
      registry.addShaped(
        new ItemStack(ItemsInit.MachineParts.REDSTONE_CONNECTOR_HORIZONTAL),
        "RRR",
        "LIL",
        "RRR",
        'R', Items.REDSTONE,
        'L', LAPIS,
        'I', Items.IRON_INGOT
      );
  
      registry.addShaped(
        new ItemStack(ItemsInit.MachineParts.REDSTONE_CONNECTOR_VERTICAL),
        "RLR",
        "RIR",
        "RLR",
        'R', Items.REDSTONE,
        'L', LAPIS,
        'I', Items.IRON_INGOT
      );
  
      registry.addShaped(
        new ItemStack(ItemsInit.MachineParts.REDSTONE_RING),
        "BHB",
        "V V",
        "BHB",
        'B', net.minecraft.init.Blocks.REDSTONE_BLOCK,
        'H', ItemsInit.MachineParts.REDSTONE_CONNECTOR_HORIZONTAL,
        'V', ItemsInit.MachineParts.REDSTONE_CONNECTOR_VERTICAL
      );
  
      registry.addShaped(
        new ItemStack(BlocksInit.Machines.MACHINE_FRAME),
        "ICI",
        "C C",
        "ICI",
        'I', Items.IRON_INGOT,
        'C', ItemsInit.Metals.INGOT_COPPER
      );
  
      registry.addShaped(
        new ItemStack(BlocksInit.Machines.MACHINE_PRESS),
        "IPI",
        "RFR",
        "IPI",
        'I', Items.IRON_INGOT,
        'P', Blocks.PISTON,
        'R', ItemsInit.MachineParts.REDSTONE_RING,
        'F', BlocksInit.Machines.MACHINE_FRAME
      );
  
      registry.addShaped(
        new ItemStack(BlocksInit.Machines.SOLAR_GENERATOR, 1, 0),
        "WWW",
        "wRw",
        "www",
        'W', ItemsInit.MachineParts.LAPIS_WAFER,
        'w', Blocks.PLANKS,
        'R', Items.REDSTONE
      );
  
      registry.addShaped(
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
        Press.LAPIS_WAFER_2,
        Press.GLOWSTONE_WAFER
      );

      MiniatureRecipeRegistry.addAll
      (
        Miniature.RAINBOW
      );
  
      EmpowererRecipeRegistry.addAll
      (
        Empowerer.STONE_DRUM,
        Empowerer.METAL_DRUM,
        Empowerer.OBSIDIAN_DRUM
      );
  
      SandingRecipeRegistry.addAll
      (
        Sanding.CLEANED_SAND
      );
      
      registry.registerNBTClearRecipes();
      
    }
    catch(Throwable t)
    {
      t.printStackTrace();
      throw t;
    }
  }
  
}
