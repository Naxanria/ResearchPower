package nl.naxanria.researchpower.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.text.TextFormatting;
import nl.naxanria.nlib.gui.GuiButtonBase;
import nl.naxanria.nlib.gui.GuiContainerBase;
import nl.naxanria.nlib.gui.Orientation;
import nl.naxanria.nlib.gui.PropertiesFactory;
import nl.naxanria.nlib.network.PacketHelper;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.researchpower.Constants;
import nl.naxanria.researchpower.containers.ContainerElectricFurnace;
import nl.naxanria.researchpower.tile.machines.furnace.TileEntityElectricFurnace;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiElectricFurnace extends GuiContainerBase<ContainerElectricFurnace>
{
  public PropertiesFactory.BarProperties power = PropertiesFactory.BarProperties.create(10, 10, 10, 30).setColors(Color.RED, Color.BLACK).setOrientation(Orientation.BottomToTop);
  public List<PropertiesFactory.BarProperties> progBars = new ArrayList<>();
  public GuiButtonBase sortButton;
  
  public GuiElectricFurnace(ContainerElectricFurnace inventorySlots, EntityPlayer player)
  {
    super(inventorySlots, player);
  
    int offsetX = 7;
    int offsetY = 2 + 18;
    for(int i = 0; i < container.INVENTORY_START; i += 2)
    {
      Slot slot = container.inventorySlots.get(i);
      progBars.add(PropertiesFactory.BarProperties.create(slot.xPos + offsetX, slot.yPos + offsetY, 4, 19).setColors(Color.WHITE, Color.GRAY).setOrientation(Orientation.TopToBottom));
    }
    
    power.setPosition(-20, 40);
  }
  
  @Override
  public void initGui()
  {
    super.initGui();
    
    if (container.tile.isButtonEnabled(0, player))
    {
      sortButton = new GuiButtonBase(0, guiLeft - 20, guiTop, 10, 10, "S");
      buttonList.add(sortButton);
    }
  }
  
  @Override
  protected void actionPerformed(GuiButton button) throws IOException
  {
    PacketHelper.sendButtonPacket(container.tile, button.id);
  }
  
  @Override
  public void updateScreen()
  {
    super.updateScreen();
    
    if (container.tile.isButtonEnabled(0, player))
    {
      sortButton.displayString = (container.tile.autoSort ? TextFormatting.DARK_GREEN : TextFormatting.DARK_RED) + "S";
    }
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
    TileEntityElectricFurnace tile = container.tile;
    float p = tile.storage.getStoredPercentage();
    
    drawProgressBar(power, p);
    
    for (int i = 0; i < progBars.size(); i++)
    {
      drawProgressBar(progBars.get(i), container.tile.modules.get(i).getProgressPercentage());
    }
    
  }
}
