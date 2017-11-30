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
  public static ResourceLocation defaultSlotImage;
  
  public final TC container;
  public final EntityPlayer player;
  
  protected ITextureInfo backgroundImage;
  protected ITextureInfo slotImage;
  //protected ITextureInfo spriteImage;
  
  public GuiContainerBase(TC inventorySlots, EntityPlayer player)
  {
    super(inventorySlots);
    
    container = inventorySlots;
    this.player = player;
    
    if (defaultBackgroundImage != null)
    {
      backgroundImage = new TextureInfo(defaultBackgroundImage);
    }
    
    if (defaultSlotImage != null)
    {
      slotImage = new TextureInfo(defaultSlotImage);
    }
    
//    if (defaultSlotImage != null)
//    {
//      spriteImage = new TextureInfo(defaultSlotImage);
//      //slotImage = SpriteManager.registerSprite("slot", 0, 0, 18, 18, spriteImage);
//    }
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage)
  {
    drawProgressBar(left, top, right, bottom, background, foreground, percentage, Orientation.LeftToRight, false, 0, 0);
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage, Orientation direction)
  {
    drawProgressBar(left, top, right, bottom, background, foreground, percentage, direction, false, 0, 0);
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage, boolean border, int borderColor, int borderWidth)
  {
    drawProgressBar(left, top, right, bottom, background, foreground, percentage, Orientation.LeftToRight, border, borderColor, borderWidth);
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

    int col = properties.foreground.color;
    int x = properties.getX();
    int y = properties.getY();
    int w = properties.getWidth();
    int h = properties.getHeight();
    int r = properties.getRight();
    int b = properties.getBottom();
    
    int pw = (int) (percentage * w);
    int ph = (int) (percentage * h);
    
    switch (properties.getOrientation())
    {
      case LeftToRight:
        drawRectWidthHeight(x, y, pw, h, col);
        break;
      case TopToBottom:
        drawRectWidthHeight(x, y, w, ph, col);
        break;
      case RightToLeft:
        drawRectWidthHeight(x + w - pw, y, pw, h, col);
        break;
      case BottomToTop:
        drawRectWidthHeight(x, y + h - ph, w, ph, col);
        break;
    }
  }
  
  public void drawRectWidthHeight(int x, int y, int width, int height, int color)
  {
    drawRect(x, y, x + width, y + height, color);
  }
  
  public void drawTexture(int x, int y, ITextureInfo texture)
  {
    drawTexture(x, y, texture, Color.WHITE);
  }
  
  public void drawTexture(int x, int y, ITextureInfo texture, Color color)
  {
    drawTexture(x, y, -1, -1, texture, color);
  }
  
  public void drawTexture(int x, int y, int width, int height, ITextureInfo texture)
  {
    drawTexture(x, y, width, height, texture, Color.WHITE);
  }
  
  public void drawTexture(int x, int y, int width, int height, ITextureInfo texture, Color color)
  {
    if (texture == null)
    {
      return;
    }
    
    if (width == -1)
    {
      width = texture.getWidth();
    }
    
    if (height == -1)
    {
      height = texture.getHeight();
    }
    
    GlStateManager.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), color.getAlphaFloat());
    mc.getTextureManager().bindTexture(texture.getResource());
    
    drawScaledCustomSizeModalRect
    (
      x, y,
      texture.getX(), texture.getY(),
      texture.getWidth(), texture.getHeight(),
      width, height,
      256, 256
    );
  }
  
  public void drawDefault()
  {
    drawDefault(0, 0);
  }
  
  
  public void drawDefault(int bgOffsetX, int bgOffsetY)
  {
    // background
    int xpos = (width - xSize) >> 1;
    int ypos = (height - ySize) >> 1;
    
    drawBackground(bgOffsetX + xpos - (backgroundImage.getWidth() >> 3), bgOffsetY + ypos);
    
    // slots
    drawSlots(xpos, ypos);
  }
  
  public void drawBackground()
  {
    drawBackground(0, 0);
  }
  
  public void drawBackground(int xOffset, int yOffset)
  {
    drawTexture(xOffset, yOffset, backgroundImage);
  }
  
  public void drawSlots()
  {
    drawSlots(0, 0);
  }
  
  public void drawSpecificSlots(int start, int end)
  {
    drawSlots(0, 0, start, end);
  }
  
  public void drawSlots(int xOff, int yOff)
  {
    drawSlots(xOff, yOff, 0, container.inventorySlots.size() - 1);
  }
  
  public void drawSlots(int offX, int offY, int start, int end)
  {
    for (int i = start; i <= end; i++)
    {
      Slot slot = container.inventorySlots.get(i);
      
      int x = slot.xPos;
      int y = slot.yPos;
    
      drawTexture(offX + x - 1, offY + y - 1, slotImage);
    }
  }
  
  public void drawNineSlice(int x, int y, int width, int height, NineSlice nineSlice)
  {
    // corners, cant change their dimensions
    ITextureInfo topLeft = nineSlice.getSlice(EnumSlice.TopLeft);
    ITextureInfo topCenter = nineSlice.getSlice(EnumSlice.TopCenter);
    ITextureInfo topRight = nineSlice.getSlice(EnumSlice.TopRight);
    
    ITextureInfo middleLeft = nineSlice.getSlice(EnumSlice.MiddleLeft);
    ITextureInfo middleCenter = nineSlice.getSlice(EnumSlice.MiddleCenter);
    ITextureInfo middleRight = nineSlice.getSlice(EnumSlice.MiddleRight);
    
    ITextureInfo bottomLeft = nineSlice.getSlice(EnumSlice.BottomLeft);
    ITextureInfo bottomCenter = nineSlice.getSlice(EnumSlice.BottomCenter);
    ITextureInfo bottomRight = nineSlice.getSlice(EnumSlice.BottomRight);
    
    
    int centerWidth = width - topLeft.getWidth() - topRight.getWidth();
    if (centerWidth <= 0)
    {
      centerWidth = 0;
      width = topLeft.getWidth() + topRight.getWidth();
    }
    
    int centerHeight = height - topLeft.getHeight() - bottomLeft.getHeight();
    if (centerHeight <= 0)
    {
      centerHeight = 0;
      height = topLeft.getHeight() + bottomLeft.getHeight();
    }
    
    int cw = Color.WHITE.color;
    
    // draw top left
    drawTexture(x, y, topLeft);
    
    // draw top center
    if (centerWidth > 0)
    {
      drawTexture(x + topLeft.getWidth(), y, centerWidth, -1, topCenter);
    }

    // draw top right
    drawTexture(x + width - topRight.getWidth(), y, topRight);

    y += topLeft.getHeight();

    if (centerHeight > 0)
    {
      // draw middle left
      drawTexture(x, y, -1, centerHeight, middleLeft);

      if (centerWidth > 0)
      {
        // middle center
        drawTexture(x + middleLeft.getWidth(), y, centerWidth, centerHeight, middleCenter);
      }

      drawTexture(x + width - middleRight.getWidth(), y, -1, centerHeight, middleRight);
    }

    y += centerHeight;

    // draw bottom left
    drawTexture(x, y, bottomLeft);

    // draw bottom center
    if (centerWidth > 0)
    {
      drawTexture(x + bottomLeft.getWidth(), y, centerWidth, -1, bottomCenter);
    }

    // draw bottom right
    drawTexture(x + width - bottomRight.getWidth(), y, bottomRight);
  }
}
