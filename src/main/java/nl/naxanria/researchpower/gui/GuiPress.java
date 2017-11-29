package nl.naxanria.researchpower.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import nl.naxanria.nlib.gui.GuiContainerBase;
import nl.naxanria.nlib.gui.Orientation;
import nl.naxanria.nlib.gui.PropertiesFactory;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.containers.ContainerPress;
import nl.naxanria.researchpower.tile.machines.TileEntityPress;

public class GuiPress extends GuiContainerBase<ContainerPress>
{
  private static final PropertiesFactory.BarProperties energyBarProperties = PropertiesFactory.BarProperties.create(11, 11, 18, 60)
    .setBackground(Color.BLACK).setForeground(Color.RED)
    .setOrientation(Orientation.Vertical);
  
  private static final PropertiesFactory.BarProperties progressBarProperties = PropertiesFactory.BarProperties.create(75, 41, 26, 5)
    .setBackground(Color.LIGHT_GRAY).setForeground(0xFFEEEEEE);
  
  private InventoryPlayer playerInv;
  
  public GuiPress(ContainerPress container, EntityPlayer player)
  {
    super(container, player);
    
    this.playerInv = player.inventory;
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    drawDefault();
  }
  
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    String name = Proxy.getLocal(BlocksInit.Machines.MACHINE_PRESS.getUnlocalizedName() + ".name");
    drawCenteredString(fontRenderer, name, xSize / 2, 6, Color.WHITE.color);
    drawString(fontRenderer, playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, Color.WHITE.color);
  
    TileEntityPress press = container.tile;
    float powerPercentage = press.storage.getStoredPercentage();
    drawProgressBar(energyBarProperties, powerPercentage);

    float progress = press.getProgressPercentage();

    drawProgressBar(progressBarProperties, progress);
  }
}

