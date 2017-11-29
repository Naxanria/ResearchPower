package nl.naxanria.researchpower.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.gui.GuiContainerBase;
import nl.naxanria.nlib.gui.Orientation;
import nl.naxanria.nlib.gui.PropertiesFactory;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.nlib.util.Color;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.containers.ContainerCoalGenerator;

public class GuiCoalGenerator extends GuiContainerBase<ContainerCoalGenerator>
{
  public final ResourceLocation BG_TEXTURE = new ResourceLocation(NMod.getModId(), "textures/gui/single_slot.png");
  
  private static final PropertiesFactory.BarProperties energyBarProperties = PropertiesFactory.BarProperties.create(11, 11, 18, 60)
    .setBackground(Color.BLACK).setForeground(Color.RED)
    .setOrientation(Orientation.Vertical);
  
  private static final PropertiesFactory.BarProperties progressBarProperties = PropertiesFactory.BarProperties.create(120, 41, 5, 15)
    .setBackground(Color.BLACK).setForeground(0xFFFF8800).setOrientation(Orientation.Vertical);
  
  public GuiCoalGenerator(ContainerCoalGenerator inventorySlots, EntityPlayer player)
  {
    super(inventorySlots, player);
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
    String name = Proxy.getLocal(BlocksInit.Machines.MACHINE_PRESS.getUnlocalizedName() + ".name");
    drawCenteredString(fontRenderer, name, xSize / 2, 6, Color.WHITE.color);
    drawString(fontRenderer, player.inventory.getDisplayName().getUnformattedText(), 8, ySize - 94, Color.WHITE.color);
  
    float energyPercentage = container.tile.storage.getStoredPercentage();
    float progress = (container.tile.getProgressPercentage());
    
    drawProgressBar(energyBarProperties, energyPercentage);
    drawProgressBar(progressBarProperties, progress);
    
    //super.drawGuiContainerForegroundLayer(mouseX, mouseY);
  }
}
