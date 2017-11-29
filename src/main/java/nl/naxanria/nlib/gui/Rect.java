package nl.naxanria.nlib.gui;

public class Rect
{
  public int x, y, width, height;
  
  public Rect(int x, int y, int width, int height)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  
  public Rect setX(int x)
  {
    this.x = x;
    return this;
  }
  
  public Rect setY(int y)
  {
    this.y = y;
    return this;
  }
  
  public Rect setWidth(int width)
  {
    this.width = width;
    return this;
  }
  
  public Rect setHeight(int height)
  {
    this.height = height;
    return this;
  }
  
  public Rect setBottom(int bottom)
  {
    setHeight(bottom - y);
    return this;
  }
  
  public Rect setRight(int right)
  {
    setWidth(right - x);
    return this;
  }
  
  public int getX()
  {
    return x;
  }
  
  public int getY()
  {
    return y;
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  public int getRight()
  {
    return x + width;
  }
  
  public int getBottom()
  {
    return y + height;
  }
  
  @Override
  public String toString()
  {
    return "Rect{" +
      "x=" + x +
      ", y=" + y +
      ", width=" + width +
      ", height=" + height +
      ", right=" + getRight() +
      ", bottom=" + getBottom() +
      '}';
  }
  
  public Rect copy()
  {
    return new Rect(x, y, width, height);
  }
}
