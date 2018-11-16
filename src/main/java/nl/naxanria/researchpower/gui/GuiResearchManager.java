package nl.naxanria.researchpower.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import nl.naxanria.nlib.gui.GuiButtonBase;
import nl.naxanria.nlib.gui.GuiContainerBase;
import nl.naxanria.nlib.gui.Orientation;
import nl.naxanria.nlib.gui.PropertiesFactory;
import nl.naxanria.nlib.network.PacketHandler;
import nl.naxanria.nlib.network.PacketHelper;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.nlib.util.collections.ReadonlyList;
import nl.naxanria.researchpower.Constants;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.containers.ContainerResearchManager;
import nl.naxanria.researchpower.research.Research;
import nl.naxanria.researchpower.research.ResearchProgress;

import java.io.IOException;

public class GuiResearchManager extends GuiContainerBase<ContainerResearchManager>
{
  private class ResearchInteractableGui extends GuiButtonBase
  {
    private Research research;
    private PropertiesFactory.BarProperties properties;
    
    public ResearchInteractableGui(int buttonId, int x, int y, int widthIn, int heightIn, Research research)
    {
      super(buttonId, x, y, widthIn, heightIn, research.name);
      
      this.research = research;
      
      properties = PropertiesFactory.BarProperties.create(x + 3, y + heightIn - 4, widthIn - 6, 3)
        .setOrientation(Orientation.LeftToRight)
        .setColors(Color.BLUE, Color.BLACK);
    }
  
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
      super.drawButton(mc, mouseX, mouseY, partialTicks);
  
      ResearchProgress progress = ResearchProgress.getProgress(player);
      
      if (progress != null && progress.parent == research)
      {
        drawProgressBar(properties, progress.getProgressPercentage());
      }
    }
  }
  
  private ReadonlyList<Research> researches;
  
  public GuiResearchManager(ContainerResearchManager inventorySlots, EntityPlayer player)
  {
    super(inventorySlots, player);
    
    researches = Research.getAsList();
  }
  
  @Override
  protected void actionPerformed(GuiButton button) throws IOException
  {
    PacketHelper.sendButtonPacket(container.tile, button.id);
  }
  
  @Override
  public void initGui()
  {
    super.initGui();
  
    // draw researches
    int w = 50, h = 18, s = 3;
  
    int xstart = guiLeft + 12; //guiLeft + 5 + 2;
    int ystart = guiTop + 7 + 20;
  
    int x = xstart;
    int y = ystart;
  
    int i = 0;
    for (Research research :
      researches)
    {
      ResearchInteractableGui rig = new ResearchInteractableGui(i, x, y, w, h, research);
  
      x += w + s;
      if (x + w > guiLeft + xSize - 5 - 2)
      {
        y += h + s;
        x = xstart;
      }
      
      buttonList.add(rig);
      i++;
    }
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    drawNineSlice(guiLeft, guiTop, xSize, ySize, Constants.DEFAULT_BG);
    
    // draw center area
    
    int xp = guiLeft + 5;
    int yp = guiTop + 15;
    int rp = guiLeft + xSize - 5;
    int bp = guiTop + ySize - 5;
    
    drawRect(xp, yp, rp, bp, Color.DARK_GRAY.color);
  }
  
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    String name = Proxy.getLocal(BlocksInit.Machines.RESEARCH_MANAGER.getUnlocalizedName() + ".name");
    drawCenteredString(fontRenderer, name, xSize / 2, 6, Color.WHITE.color);
    
    // draw researches
//    int w = 24, h = 24, s = 3;
//
//    int xstart = 15; //guiLeft + 5 + 2;
//    int ystart = 7 + 20;
//
//    int x = xstart;
//    int y = ystart;
//
//    for (Research r :
//      researches)
//    {
//      drawRectWidthHeight(x, y, w, h, Color.BLACK.color);
//      drawCenteredString(fontRenderer, r.name, x + w / 2, y + h / 2, Color.WHITE.color);
//
//      x += w + s;
//      if (x + w > guiLeft + xSize - 5 - 2)
//      {
//        y += h + s;
//        x = xstart;
//      }
//    }

  
  
  }
  
  
}
