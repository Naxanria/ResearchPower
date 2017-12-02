package nl.naxanria.researchpower.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.gui.*;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.researchpower.Constants;
import nl.naxanria.researchpower.containers.ContainerEmpowerer;

public class GuiEmpowerer extends GuiContainerBase<ContainerEmpowerer>
{
  private static ResourceLocation[] progressAnim;
  private static TextureInfo background = new TextureInfo(new ResourceLocation(NMod.getModId(),"textures/gui/empowerer/empowerer_bg.png"));
  private static PropertiesFactory.BarProperties powerBar = PropertiesFactory.BarProperties.create()
    .setColors(Color.RED, Color.BLACK).setOrientation(Orientation.BottomToTop).setDimensions(5, 20);
  
  static
  {
    int steps = 17;
    String base = "textures/gui/empowerer/empower_progress_";
    
    progressAnim = new ResourceLocation[steps];
    for (int i = 0; i < steps; i++)
    {
      progressAnim[i] = new ResourceLocation(NMod.getModId(), base + i + ".png");
    }
  }
  
  public GuiEmpowerer(ContainerEmpowerer inventorySlots, EntityPlayer player)
  {
    super(inventorySlots, player);
    backgroundImage = background;
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    //drawDefault(0, -22);
    int xPos = (width - xSize) >> 1;
    int yPos = ((height - ySize) >> 1);
    
    drawNineSlice(xPos - 10, yPos - 22 , xSize + 10, ySize + 22, Constants.DEFAULT_BG);
  
    float progress = container.tile.getProgressPercent();
    int idx = (int) (progress * progressAnim.length);
  
    if (idx == progressAnim.length)
    {
      idx--;
    }
  
    GlStateManager.color(1, 1, 1, 1);
    mc.getTextureManager().bindTexture(progressAnim[idx]);
    drawTexturedModalRect(xPos + 7, yPos - 18, 0, 0, 256, 256);
    
    drawSlots(xPos, yPos);
  }
  
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    powerBar.setPosition(-6, 0).setDimensions(10, 30);
    float perc = container.tile.storage.getStoredPercentage();
    Log.info("Perc: " + perc + " " + container.tile.storage.getEnergyStored() + " " + container.tile.storage.getCapacity());
    drawProgressBar(powerBar, perc);
  }
}
