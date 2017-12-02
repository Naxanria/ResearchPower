package nl.naxanria.researchpower.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import nl.naxanria.researchpower.ResearchPower;
import scala.actors.threadpool.Arrays;

import java.util.List;

public class GuiConfigResearchPower extends GuiConfig
{
  public GuiConfigResearchPower(GuiScreen parent)
  {
    super
    (
      parent,
      getConfigList(ConfigElement.from(ModConfig.class)),
      ResearchPower.MOD_ID,
      null,
      false,
      false,
      ResearchPower.MOD_NAME + " config",
      null
    );
  
    //this(parentScreen, collectConfigElements(configClasses), modID, null, allRequireWorldRestart, allRequireMcRestart, title, null);
    //ResearchPower.MOD_ID, ResearchPower.MOD_NAME + " config"
  }
  
  @SuppressWarnings("unchecked")
  private static List<IConfigElement> getConfigList(IConfigElement... elements)
  {
    return Arrays.asList(elements);
  }
  
  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
