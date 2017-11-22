package nl.naxanria.researchpower.research;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class ResearchDatabase
{
  protected static List<ResearchEntry> researchEntries = new ArrayList<>();
  
  public static boolean isUnlocked(IRecipe recipe, EntityPlayer player)
  {
    return true;
  }
  
  public static void initAll()
  {
    Log.info("Loading research");
  
    for (IRecipe recipe : CraftingManager.REGISTRY)
    {
      addEntry(recipe, recipe.getRegistryName());
    }
  }
  
  protected static void addEntry(IRecipe recipe, ResourceLocation resourceLocation)
  {
    researchEntries.add(new ResearchEntry(Research.createOrUpdate(resourceLocation.getResourceDomain(), recipe), recipe));
  }
  
  
}
