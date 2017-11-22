package nl.naxanria.researchpower.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.proxy.Proxy;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.containers.ContainerPress;

public class GuiPress extends GuiContainer
{
  private static final ResourceLocation BG_TEXTURE = new ResourceLocation(NMod.getModId(), "texture/gui/single_slot.png");
  private InventoryPlayer playerInv;
  
  public GuiPress(Container container, InventoryPlayer playerInv)
  {
    super(container);
    
    this.playerInv = playerInv;
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
    drawCenteredString(fontRenderer, name, xSize / 2, 6, 0x404040);
    drawString(fontRenderer, playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
 
    // power bar
    drawRect(100, 100, 120, 200, 0x000000);
    float powerPercentage = ((ContainerPress) inventorySlots).entityPress.storage.getStoredPercentage();
    drawRect(100, 100, 120, (int)(100 + 100 * powerPercentage), 0xCC0000);
  }
}

