package nl.naxanria.researchpower.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.Registy.RecipeRegistry;
import nl.naxanria.researchpower.block.Blocks;
import nl.naxanria.researchpower.item.Items;

public class Recipes
{
  public static void init(RecipeRegistry registry)
  {
    GameRegistry.addSmelting
    (
      Blocks.Ores.copperOre,
      new ItemStack(Items.Metals.ingotCopper),
      0.5f
    );
    
    GameRegistry.addShapelessRecipe(
      new ResourceLocation(NMod.getModId()),
      new ResourceLocation(NMod.getModId()),
      new ItemStack(Blocks.Metals.copperBlock),
      Ingredient.fromStacks(new ItemStack(Items.Metals.ingotCopper, 9))
    );
    
    GameRegistry.addShapedRecipe(
      new ResourceLocation(NMod.getModId()),
      new ResourceLocation(NMod.getModId()),
      new ItemStack(Blocks.Metals.copperBlock),
      "AAA",
      "AAA",
      "AAA",
      'A', new ItemStack(Items.Metals.ingotCopper)
    );

    registry.addAll
    (
    
    );
  }
}
