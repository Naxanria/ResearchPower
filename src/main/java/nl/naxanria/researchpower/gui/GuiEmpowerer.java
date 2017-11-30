package nl.naxanria.researchpower.gui;

import net.minecraft.entity.player.EntityPlayer;
import nl.naxanria.nlib.gui.GuiContainerBase;
import nl.naxanria.nlib.gui.Orientation;
import nl.naxanria.nlib.gui.PropertiesFactory;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.researchpower.containers.ContainerEmpowerer;

public class GuiEmpowerer extends GuiContainerBase<ContainerEmpowerer>
{
  private PropertiesFactory.BarProperties baseBar = PropertiesFactory.BarProperties.create().setBackground(Color.LIGHT_GRAY).setForeground(Color.WHITE).setOrientation(Orientation.LeftToRight);
  private PropertiesFactory.BarProperties bar0 = baseBar.copy();
  private PropertiesFactory.BarProperties bar1 = baseBar.copy();
  private PropertiesFactory.BarProperties bar2 = baseBar.copy();
  private PropertiesFactory.BarProperties bar3 = baseBar.copy();
  
  public GuiEmpowerer(ContainerEmpowerer inventorySlots, EntityPlayer player)
  {
    super(inventorySlots, player);
  
    int x = 45;
    int y = 20;
    int step = 2 * 18;
    int offset = 2;
    int w = 5;
    int l = (step - offset * 2) / 2;
    
    bar0.setPosition(x - step + offset + l, y - w / 2 + 9).setDimensions(l, w).setOrientation(Orientation.LeftToRight);
    bar1.setPosition(x + step - offset - l, y - w / 2 + 9).setDimensions(l, w).setOrientation(Orientation.RightToLeft);
    bar2.setPosition(x - w / 2 + 9, y - step + offset + l).setDimensions(w, l).setOrientation(Orientation.TopToBottom);
    bar3.setPosition(x - w / 2 + 9, y + step - offset - l).setDimensions(w, l).setOrientation(Orientation.BottomToTop);
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    drawDefault();
  }
  
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    float progress = container.tile.getProgressPercent();
    
    drawProgressBar(bar0, progress);
    drawProgressBar(bar1, progress);
    drawProgressBar(bar2, progress);
    drawProgressBar(bar3, progress);
  }
}
