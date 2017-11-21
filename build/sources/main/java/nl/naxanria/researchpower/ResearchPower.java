package nl.naxanria.researchpower;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.nlib.util.ores.OreBuilder;
import nl.naxanria.nlib.util.ores.OreHelper;
import nl.naxanria.researchpower.block.Blocks;
import nl.naxanria.researchpower.item.Items;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.researchpower.recipe.Recipes;
import nl.naxanria.researchpower.research.ResearchDatabase;

@Mod
(
  modid = ResearchPower.MOD_ID,
  name = ResearchPower.MOD_NAME,
  version = ResearchPower.VERSION
)
public class ResearchPower extends NMod
{
  public static final String MOD_ID = "researchpower";
  public static final String MOD_NAME = "Research Power";
  public static final String VERSION = "1.0-SNAPSHOT";
  
  @Override
  public CreativeTabs defaultTab()
  {
    return tab;
  }
  
  @SidedProxy(clientSide = "nl.naxanria.nlib.proxy.ClientProxy", serverSide = "nl.naxanria.nlib.proxy.ServerProxy")
  public static Proxy proxy;
  
  public static ResearchPowerTab tab = new ResearchPowerTab();
  
  @Override
  protected Class getBlockClass()
  {
    return Blocks.class;
  }
  
  @Override
  protected Class getItemClass()
  {
    return Items.class;
  }
  
  @Override
  protected Class getRecipeClass()
  {
    return Recipes.class;
  }
  
  @Override
  public String modId()
  {
    return MOD_ID;
  }
  
  @Override
  public String modName()
  {
    return MOD_NAME;
  }
  
  @Override
  public String modVersion()
  {
    return VERSION;
  }
  
  @Override
  protected void onPreInit(FMLPreInitializationEvent event)
  {
    OreHelper.registerInOreDict
      (
        new OreBuilder("Copper")
        .ore(Blocks.Ores.copperOre)
        .ingot(Items.Metals.ingotCopper)
        .fullBlock(Blocks.Metals.copperBlock)
      );
  }
  
  @Override
  protected void onPostInit(FMLPostInitializationEvent event)
  {
    Log.info("Post Initialization");
    ResearchDatabase.initAll();
  }
  
  public static class ResearchPowerTab extends CreativeTabs
  {
    public ResearchPowerTab()
    {
      super(MOD_ID);
    }
  
    @Override
    public ItemStack getTabIconItem()
    {
      return new ItemStack(net.minecraft.init.Items.IRON_INGOT);
    }
  }
}
