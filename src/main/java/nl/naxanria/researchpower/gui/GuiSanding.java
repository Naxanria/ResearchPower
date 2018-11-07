package nl.naxanria.researchpower.gui;

import net.minecraft.entity.player.EntityPlayer;
import nl.naxanria.nlib.gui.GuiContainerBase;
import nl.naxanria.nlib.gui.Orientation;
import nl.naxanria.nlib.gui.PropertiesFactory;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.nlib.util.MathUtil;
import nl.naxanria.researchpower.Constants;
import nl.naxanria.researchpower.containers.ContainerSanding;
import nl.naxanria.researchpower.tile.machines.TileEntityMachineSanding;

public class GuiSanding extends GuiContainerBase<ContainerSanding>
{
  static PropertiesFactory.BarProperties powerBar = PropertiesFactory.BarProperties.create(15, 20, 20, 60).setColors(Color.RED, Color.BLACK).setOrientation(Orientation.BottomToTop);
  static PropertiesFactory.BarProperties fluidBar = PropertiesFactory.BarProperties.create(140, 20, 20, 60).setColors(Color.BLUE, Color.BLACK).setOrientation(Orientation.BottomToTop);
  static PropertiesFactory.BarProperties progressBar = PropertiesFactory.BarProperties.create(55, 40, 6, 12).setColors(Color.WHITE, Color.GRAY).setOrientation(Orientation.TopToBottom);
  static PropertiesFactory.BarProperties sandBar = PropertiesFactory.BarProperties.create(110, 20, 4, 60)
    .setColors(Color.color(200, 170, 100), Color.BLACK).setBorderWidth(1).setBorder(Color.BLACK).setUseBorder(true).setOrientation(Orientation.BottomToTop);
  
  public GuiSanding(ContainerSanding inventorySlots, EntityPlayer player)
  {
    super(inventorySlots, player);
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    int xPos = (width - xSize) >> 1;
    int yPos = ((height - ySize) >> 1);
  
    drawNineSlice(xPos - 8, yPos , xSize + 10, ySize, Constants.DEFAULT_BG);
    
    drawSlots(xPos, yPos);
    //drawDefault();
  }
  
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    TileEntityMachineSanding tile = container.tile;

    float energy = tile.storage.getStoredPercentage();
    float fluid = MathUtil.getPercent(tile.tank.getFluidAmount(), tile.tank.getCapacity());
    float progress = MathUtil.getPercent(tile.progress, tile.totalTime);
    float sand = MathUtil.getPercent(tile.sandAmount, tile.sandBuffer);
    
    drawProgressBar(powerBar, energy);
    drawProgressBar(fluidBar, fluid);
    drawProgressBar(progressBar, progress);
    drawProgressBar(sandBar, sand);
  }
}
