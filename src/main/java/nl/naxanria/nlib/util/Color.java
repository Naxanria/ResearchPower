package nl.naxanria.nlib.util;

public class Color
{
  public static final Color BLACK        = new Color(0xFF000000);
  public static final Color WHITE        = new Color(0xFFFFFFFF);
  public static final Color RED          = new Color(0xFFFF0000);
  public static final Color PINK         = new Color(0xFFFFCCCC);
  public static final Color DARK_RED     = new Color(0xFFCC0000);
  public static final Color YELLOW       = new Color(0xFFFF0000);
  public static final Color ORANGE       = new Color(0xFF880000);
  public static final Color LIME         = new Color(0xFF00FF00);
  public static final Color GREEN        = new Color(0xFF00CC00);
  public static final Color DARK_GREEN   = new Color(0xFF00AA00);
  public static final Color LIGHT_GREEN  = new Color(0xFFCCFFCC);
  public static final Color BLUE         = new Color(0xFF0000FF);
  public static final Color DARK_BLUE    = new Color(0xFF0000CC);
  public static final Color LIGHT_BLUE   = new Color(0xFFCCCCFF);
  public static final Color DARK_GRAY    = new Color(0xFF888888);
  public static final Color LIGHT_GRAY   = new Color(0xFFDDDDDD);
  public static final Color GRAY         = new Color(0xFFAAAAAA);
  public static final Color CYAN         = new Color(0xFF00FFFF);
  public static final Color PURPLE       = new Color(0xFFFF00FF);
  public static final Color MAGENTA      = new Color(0xFFCC00CC);
  
  public final int color;
  
  private Color(int color)
  {
    this.color = color;
  }
  
  public static Color color(int color)
  {
    return new Color(color);
  }
  
  public static Color color(int red, int green, int blue)
  {
    return color(red, green, blue, 0xff);
  }
  
  public static Color color(int red, int green, int blue, int alpha)
  {
    return new Color(alpha << 24 | red << 16 | green << 8 | blue);
  }
  
  public static Color color(float r, float g, float b)
  {
    return color(r, g, b, 1);
  }
  
  public static Color color(float r, float g, float b, float a)
  {
    return color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
  }
  
  public static Color hsv(float h, float s, float v, float a)
  {
    float r = 0;
    float g = 0;
    float b = 0;
  
    int hue = (int) (h * 6);
    float f = h * 6 - hue;
    float p = v * (1 - s);
    float q = v * (1 - f * s);
    float t = v * (1 - (1 - f) * s);
  
    if (hue == 0)
    {
      r = v;
      g = t;
      b = p;
    }
    else if (hue == 1)
    {
      r = q;
      g = v;
      b = p;
    }
    else if (hue == 2)
    {
      r = p;
      g = v;
      b = t;
    }
    else if (hue == 3)
    {
      r = p;
      g = q;
      b = v;
    }
    else if (hue == 4)
    {
      r = t;
      g = p;
      b = v;
    }
    else if (hue <= 6)
    {
      r = v;
      g = p;
      b = q;
    }
    
    return color(r, g, b);
  }
  
  public int getAlpha()
  {
    return (color >> 24) & 0xff;
  }
  
  public int getRed()
  {
    return (color >> 24) & 0xff;
  }
  
  public int getGreen()
  {
    return (color >> 24) & 0xff;
  }
  
  public int getBlue()
  {
    return (color >> 24) & 0xff;
  }
  
  public float getAlphaFloat()
  {
    return getAlpha() / 255.0f;
  }
  
  public float getRedFloat()
  {
    return getRed() / 255.0f;
  }
  
  public float getGreenFloat()
  {
    return getGreen() / 255.0f;
  }
  public float getBlueFloat()
  {
    return getBlue() / 255.0f;
  }
  
  
  
  
  public static int getAlpha(int color)
  {
    return (color >> 24) & 0xff;
  }
  
  public static int getRed(int color)
  {
    return (color >> 24) & 0xff;
  }
  
  public static int getGreen(int color)
  {
    return (color >> 24) & 0xff;
  }
  
  public static int getBlue(int color)
  {
    return (color >> 24) & 0xff;
  }
  
  public Color lerp(int color, float t)
  {
    return lerp(this, color(color), t);
  }
  
  public Color lerp(Color color, float t)
  {
    return lerp(this, color, t);
  }
  
  public static Color lerp(int a, int b, float t)
  {
    return lerp(color(a), color(b), t);
  }
  
  public static Color lerp(int a, Color b, float t)
  {
    return lerp(color(a), b, t);
  }
  
  public static Color lerp(Color a, int b, float t)
  {
    return lerp(a, color(b), t);
  }
  
  public static Color lerp(Color a, Color b, float t)
  {
    int alpha  = (int) MathUtil.lerp(a.getAlpha(), b.getAlpha(), t);
    int red    = (int) MathUtil.lerp(a.getRed(),   b.getRed(),   t);
    int green  = (int) MathUtil.lerp(a.getGreen(), b.getGreen(), t);
    int blue   = (int) MathUtil.lerp(a.getBlue(),  b.getBlue(),  t);
    
    return color(red, green, blue, alpha);
  }
}
