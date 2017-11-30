package nl.naxanria.researchpower.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.gui.GuiContainerBase;
import nl.naxanria.nlib.gui.Orientation;
import nl.naxanria.nlib.gui.PropertiesFactory;
import nl.naxanria.nlib.gui.TextureInfo;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.researchpower.containers.ContainerEmpowerer;

public class GuiEmpowerer extends GuiContainerBase<ContainerEmpowerer>
{
  private static ResourceLocation[] progressAnim;
  private static TextureInfo background = new TextureInfo(new ResourceLocation(NMod.getModId(),"textures/gui/empowerer/empowerer_bg.png"));
  
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
    drawDefault(0, -22);
  }
  
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    float progress = container.tile.getProgressPercent();
    int idx = (int) (progress * progressAnim.length);
    
    if (idx == progressAnim.length)
    {
      idx--;
    }
    
    GlStateManager.color(1, 1, 1, 1);
    mc.getTextureManager().bindTexture(progressAnim[idx]);
    drawTexturedModalRect(7, -18, 0, 0, 256, 256);
  }
}
