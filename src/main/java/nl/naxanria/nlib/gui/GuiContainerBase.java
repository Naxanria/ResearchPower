package nl.naxanria.nlib.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class GuiContainerBase extends GuiContainer
{
  public GuiContainerBase(Container inventorySlots)
  {
    super(inventorySlots);
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
}
