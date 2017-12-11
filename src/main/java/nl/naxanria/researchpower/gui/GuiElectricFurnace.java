package nl.naxanria.researchpower.gui;

import net.minecraft.entity.player.EntityPlayer;
import nl.naxanria.nlib.gui.GuiContainerBase;
import nl.naxanria.nlib.gui.Orientation;
import nl.naxanria.nlib.gui.PropertiesFactory;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.nlib.util.MathUtil;
import nl.naxanria.researchpower.Constants;
import nl.naxanria.researchpower.containers.ContainerElectricFurnace;
import nl.naxanria.researchpower.tile.machines.furnace.TileEntityElectricFurnace;

public class GuiElectricFurnace extends GuiContainerBase<ContainerElectricFurnace>
{
  public GuiElectricFurnace(ContainerElectricFurnace inventorySlots, EntityPlayer player)
  {
    super(inventorySlots, player);
    
    
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    int xPos = (width - xSize) >> 1;
    int yPos = ((height - ySize) >> 1);
    
    drawNineSlice(xPos, yPos, xSize, ySize, Constants.DEFAULT_BG);
    
    drawSlots(xPos, yPos);
  }
  
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    PropertiesFactory.BarProperties power = PropertiesFactory.BarProperties.create(10, 10, 10, 30).setColors(Color.RED, Color.BLACK).setOrientation(Orientation.BottomToTop);
    PropertiesFactory.BarProperties prog1 = PropertiesFactory.BarProperties.create(47, 30, 4, 19).setColors(Color.WHITE, Color.GRAY).setOrientation(Orientation.TopToBottom);
    PropertiesFactory.BarProperties prog2 = prog1.copy().setX(prog1.getX() + 40);
  
    TileEntityElectricFurnace tile = container.tile;
    float p = tile.storage.getStoredPercentage();
    float p1 = MathUtil.getPercent(tile.module1.progress, tile.module1.total);
    float p2 = MathUtil.getPercent(tile.module2.progress, tile.module2.total);
    
    drawProgressBar(power, p);
    drawProgressBar(prog1, p1);
    drawProgressBar(prog2, p2);
  }
}
