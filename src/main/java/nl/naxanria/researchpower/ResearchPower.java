package nl.naxanria.researchpower;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.nlib.util.ores.OreBuilder;
import nl.naxanria.nlib.util.ores.OreHelper;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.gui.ModGuiHandler;
import nl.naxanria.researchpower.item.ItemsInit;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.researchpower.recipe.RecipesInit;
import nl.naxanria.researchpower.research.ResearchDatabase;

public class ResearchPower extends NMod
{
  public static final String MOD_ID = "researchpower";
  public static final String MOD_NAME = "Research Power";
  public static final String VERSION = "1.0-SNAPSHOT";

  public ResearchPower(Object mod)
  {
    super(mod);
  }

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
    return BlocksInit.class;
  }
  
  @Override
  protected Class getItemClass()
  {
    return ItemsInit.class;
  }
  
  @Override
  protected Class getRecipeClass()
  {
    return RecipesInit.class;
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
        .ore(BlocksInit.Ores.copperOre)
        .ingot(ItemsInit.Metals.ingotCopper)
        .fullBlock(BlocksInit.Metals.copperBlock)
      );
  
    NetworkRegistry.INSTANCE.registerGuiHandler(mod, new ModGuiHandler());
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
