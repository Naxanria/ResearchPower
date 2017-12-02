package nl.naxanria.nlib.gui;

import nl.naxanria.nlib.util.Color;

public class PropertiesFactory
{
  public static class BarProperties
  {
    protected Rect rect = new Rect(0, 0, 0, 0);
    protected boolean useBorder = false;
    protected int borderWidth = 0;
    protected Color foreground = Color.WHITE;
    protected Color background = Color.DARK_GRAY;
    protected Color border = Color.BLACK;
    protected Orientation orientation = Orientation.LeftToRight;
    
    
    private BarProperties()
    { }
  
    public BarProperties(Rect rect, boolean useBorder, int borderWidth, Color foreground, Color background, Color border, Orientation orientation)
    {
      this.rect = rect;
      this.useBorder = useBorder;
      this.borderWidth = borderWidth;
      this.foreground = foreground;
      this.background = background;
      this.border = border;
      this.orientation = orientation;
    }
    
    public BarProperties copy()
    {
      return new BarProperties(rect.copy(), useBorder, borderWidth, foreground, background, border, orientation);
    }
  
    public static BarProperties create()
    {
      return new BarProperties();
    }
  
    public static BarProperties create(Rect dim)
    {
      BarProperties barProperties = new BarProperties();
      barProperties.rect = dim;
      
      return barProperties;
    }
  
    public static BarProperties create(int x, int y, int width, int height)
    {
      BarProperties barProperties = new BarProperties();
      barProperties.rect = new Rect(x, y, width, height);
      
      return barProperties;
    }
    
    public BarProperties setPosition(int x, int y)
    {
      rect.setX(x);
      rect.setY(y);
      
      return this;
    }
    
    public BarProperties setDimensions(int width, int height)
    {
      rect.setWidth(width);
      rect.setHeight(height);
      
      return this;
    }
  
    public int getX()
    {
      return rect.x;
    }
  
    public BarProperties setX(int x)
    {
      rect.setX(x);
      return this;
    }
  
    public int getY()
    {
      return rect.y;
    }
  
    public BarProperties setY(int y)
    {
      rect.setY(y);
      return this;
    }
  
    public int getWidth()
    {
      return rect.width;
    }
  
    public BarProperties setWidth(int width)
    {
      rect.setWidth(width);
      return this;
    }
  
    public int getHeight()
    {
      return rect.height;
    }
  
    public BarProperties setHeight(int height)
    {
      rect.setHeight(height);
      return this;
    }
  
    public int getLeft()
    {
      return getX();
    }
  
    public BarProperties setLeft(int left)
    {
      return setX(left);
    }
  
    public int getRight()
    {
      return rect.getRight();
    }
  
    public BarProperties setRight(int right)
    {
      rect.setRight(right);
      return this;
    }
  
    public int getTop()
    {
      return rect.y;
    }
  
    public BarProperties setTop(int top)
    {
      rect.y = top;
      return this;
    }
  
    public int getBottom()
    {
      return rect.getBottom();
    }
  
    public BarProperties setBottom(int bottom)
    {
      rect.setBottom(bottom);
      return this;
    }
  
    public boolean isUseBorder()
    {
      return useBorder;
    }
  
    public BarProperties setUseBorder(boolean useBorder)
    {
      this.useBorder = useBorder;
      return this;
    }
  
    public int getBorderWidth()
    {
      return borderWidth;
    }
  
    public BarProperties setBorderWidth(int borderWidth)
    {
      this.borderWidth = borderWidth;
      return this;
    }
  
    public Color getForeground()
    {
      return foreground;
    }
  
    public BarProperties setForeground(Color foreground)
    {
      this.foreground = foreground;
      return this;
    }
  
    public BarProperties setForeground(int foreground)
    {
      this.foreground = Color.color(foreground);
      return this;
    }
  
    public Color getBackground()
    {
      return background;
    }
  
    public BarProperties setBackground(Color background)
    {
      this.background = background;
      return this;
    }
  
    public BarProperties setBackground(int background)
    {
      this.background = Color.color(background);
      return this;
    }
    
    public BarProperties setColors(int foreground, int background)
    {
      return setColors(Color.color(foreground), Color.color(background));
    }
    
    public BarProperties setColors(Color foreground, Color background)
    {
      this.foreground = foreground;
      this.background = background;
      
      return this;
    }
    
    public BarProperties swapColors()
    {
      Color t = background;
      background = foreground;
      foreground = background;
      
      return this;
    }
  
    public Color getBorder()
    {
      return border;
    }
  
    public BarProperties setBorder(Color border)
    {
      this.border = border;
      return this;
    }
  
    public BarProperties setBorder(int border)
    {
      this.border = Color.color(border);
      return this;
    }
  
    public Orientation getOrientation()
    {
      return orientation;
    }
  
    public BarProperties setOrientation(Orientation orientation)
    {
      this.orientation = orientation;
      return this;
    }
  
    @Override
    public String toString()
    {
      return "BarProperties{" +
        "rect=" + rect +
        ", useBorder=" + useBorder +
        ", borderWidth=" + borderWidth +
        ", foreground=" + foreground +
        ", background=" + background +
        ", border=" + border +
        ", orientation=" + orientation +
        '}';
    }
  }
}
