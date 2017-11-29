package nl.naxanria.nlib.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.nlib.util.Color;

import static nl.naxanria.researchpower.ResearchPower.MOD_ID;

public abstract class GuiContainerBase<TC extends ContainerBase> extends GuiContainer
{
  public static ResourceLocation defaultBackgroundImage = new ResourceLocation("minecraft:textures/gui/demo_background.png");
  public static ResourceLocation spriteSheet = new ResourceLocation(MOD_ID, "textures/gui/slot.png");
  
  public final TC container;
  public final EntityPlayer player;
  
  protected ITextureInfo backgroundImage;
  protected ITextureInfo slotImage;
  protected ITextureInfo spriteImage;
  
  public GuiContainerBase(TC inventorySlots, EntityPlayer player)
  {
    super(inventorySlots);
    
    container = inventorySlots;
    this.player = player;
    
    if (defaultBackgroundImage != null)
    {
      backgroundImage = new TextureInfo(defaultBackgroundImage);
    }
    
    if (spriteSheet != null)
    {
      spriteImage = new TextureInfo(spriteSheet);
      slotImage = SpriteManager.registerSprite("slot", 0, 0, 18, 18, spriteImage);
    }
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage)
  {
    drawProgressBar(left, top, right, bottom, background, foreground, percentage, Orientation.Horizontal, false, 0, 0);
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage, Orientation direction)
  {
    drawProgressBar(left, top, right, bottom, background, foreground, percentage, direction, false, 0, 0);
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage, boolean border, int borderColor, int borderWidth)
  {
    drawProgressBar(left, top, right, bottom, background, foreground, percentage, Orientation.Horizontal, border, borderColor, borderWidth);
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage, Orientation direction, boolean border, int borderColor, int borderWidth)
  {
    drawProgressBar(
      PropertiesFactory.BarProperties.create()
        .setLeft(left).setTop(top).setRight(right).setBottom(bottom)
        .setBackground(background).setForeground(foreground)
        .setOrientation(direction)
        .setUseBorder(border).setBorder(borderColor).setBorder(borderWidth),
      percentage
    );
  }
  
  public void drawProgressBar(PropertiesFactory.BarProperties properties, float percentage)
  {
    if (properties.useBorder)
    {
      drawRect
      (
      properties.getLeft() - properties.getBorderWidth(),
      properties.getTop() - properties.getBorderWidth(),
     properties.getRight() + properties.getBorderWidth(),
   properties.getBottom() + properties.getBorderWidth(),
           properties.border.color
      );
    }
    
    // background
    drawRect(properties.getLeft(), properties.getTop(), properties.getRight(), properties.getBottom(), properties.getBackground().color);

    drawRect
    (
      (properties.getOrientation() == Orientation.Horizontal) ? properties.getLeft() + ((int) ((properties.getWidth()) *  percentage)) : properties.getRight(),
      (properties.getOrientation() == Orientation.Vertical) ? properties.getBottom() - ((int) ((properties.getHeight()) * percentage)) : properties.getTop(),
      properties.getLeft(),
      properties.getBottom(),
      properties.getForeground().color
    );
  }
  
  public void drawTexture(int x, int y, ITextureInfo texture)
  {
    drawTexture(x, y, texture, Color.WHITE);
  }
  
  public void drawTexture(int x, int y, ITextureInfo texture, Color color)
  {
    if (texture == null)
    {
      return;
    }
    
    GlStateManager.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), color.getAlphaFloat());
    mc.getTextureManager().bindTexture(texture.getResource());
    drawTexturedModalRect(x, y, texture.getX(), texture.getY(), texture.getWidth(), texture.getHeight());
  }
  
  public void drawDefault()
  {
    // background
    int xpos = (width - xSize) >> 1;
    int ypos = (height - ySize) >> 1;
    
    drawTexture(xpos - (backgroundImage.getWidth() >> 3), ypos, backgroundImage);
    
    // slots
    for (Slot slot:
      container.inventorySlots)
    {
      int x = slot.xPos;
      int y = slot.yPos;
      
      drawTexture(xpos + x - 1, ypos + y - 1, slotImage);
    }
  }
}
