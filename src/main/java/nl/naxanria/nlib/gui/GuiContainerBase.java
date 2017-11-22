package nl.naxanria.nlib.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import nl.naxanria.researchpower.containers.ContainerPress;

public abstract class GuiContainerBase extends GuiContainer
{
  public enum BarDirection
  {
    Horizontal,
    Vertical
  }
  
  public GuiContainerBase(Container inventorySlots)
  {
    super(inventorySlots);
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage)
  {
    drawProgressBar(left, top, right, bottom, background, foreground, percentage, BarDirection.Horizontal, false, 0, 0);
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage, BarDirection direction)
  {
    drawProgressBar(left, top, right, bottom, background, foreground, percentage, direction, false, 0, 0);
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage, boolean border, int borderColor, int borderWidth)
  {
    drawProgressBar(left, top, right, bottom, background, foreground, percentage, BarDirection.Horizontal, border, borderColor, borderWidth);
  }
  
  public void drawProgressBar(int left, int top, int right, int bottom, int background, int foreground, float percentage, BarDirection direction ,boolean border, int borderColor, int borderWidth)
  {
    if (border)
    {
      drawRect(left - borderWidth, top - borderWidth, right + borderWidth, bottom + borderWidth, borderColor);
    }
    
    // background
    drawRect(left, top, right, bottom, background);
    
    drawRect
    (
      left, top,
      (direction == BarDirection.Horizontal) ? left + ((int) ((right - left) * percentage)) : right,
      (direction == BarDirection.Vertical) ? top + ((int) ((bottom - top) * percentage)) : bottom,
      foreground
    );
  }
}
