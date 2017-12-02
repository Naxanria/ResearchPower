package nl.naxanria.researchpower.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

public class GuiFactoryResearchPower implements IModGuiFactory
{
  @Override
  public void initialize(Minecraft instance)
  {  }
  
  @Override
  public boolean hasConfigGui()
  {
    return true;
  }
  
  @Override
  public GuiScreen createConfigGui(GuiScreen parent)
  {
    return new GuiConfigResearchPower(parent);
  }
  
  @Override
  public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
  {
    return null;
  }
}
