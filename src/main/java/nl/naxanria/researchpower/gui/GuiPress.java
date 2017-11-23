package nl.naxanria.researchpower.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.gui.GuiContainerBase;
import nl.naxanria.nlib.gui.Orientation;
import nl.naxanria.nlib.gui.PropertiesFactory;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.containers.ContainerPress;
import nl.naxanria.researchpower.tile.machines.TileEntityPress;

public class GuiPress extends GuiContainerBase
{
  private static final ResourceLocation BG_TEXTURE = new ResourceLocation(NMod.getModId(), "textures/gui/double_slot.png");
  
  private static final PropertiesFactory.BarProperties energyBarProperties = PropertiesFactory.BarProperties.create(11, 11, 18, 60)
    .setBackground(Color.BLACK).setForeground(Color.RED)
    .setOrientation(Orientation.Vertical);
  
  private static final PropertiesFactory.BarProperties progressBarProperties = PropertiesFactory.BarProperties.create(75, 41, 26, 5)
    .setBackground(Color.LIGHT_GRAY).setForeground(0xFFEEEEEE);
  
  private InventoryPlayer playerInv;
  
  public GuiPress(Container container, InventoryPlayer playerInv)
  {
    super(container);
    
    this.playerInv = playerInv;
    
    //Log.info(progressBarProperties.toString());
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    GlStateManager.color(1, 1, 1, 1);
    mc.getTextureManager().bindTexture(BG_TEXTURE);
    int x = (width - xSize) / 2;
    int y = (height - ySize) / 2;
    drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
  }
  
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    String name = Proxy.getLocal(BlocksInit.Machines.machinePress.getUnlocalizedName() + ".name");
    drawCenteredString(fontRenderer, name, xSize / 2, 6, Color.WHITE.color);
    drawString(fontRenderer, playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, Color.WHITE.color);
  
    TileEntityPress press = ((ContainerPress) inventorySlots).entityPress;
    float powerPercentage = press.storage.getStoredPercentage();
    drawProgressBar(energyBarProperties, powerPercentage);

    float progress = press.getProgressPercentage();

    drawProgressBar(progressBarProperties, progress);
  }
}

