package nl.naxanria.nlib.util;

public class MathUtil
{
  public static float lerp(float a, float b, float t)
  {
    return (1 - t) * a + t * b;
  }
  
  public static float clamp(float val, float min, float max)
  {
    return  val < min ? min : val > max ? max : val;
  }
  
  public static float clamp01(float val)
  {
    return clamp(val, 0, 1);
  }
  
  public static int clamp(int val, int min, int max)
  {
    return  val < min ? min : val > max ? max : val;
  }
  
  public static int clamp015(int val)
  {
    return clamp(val, 0, 15);
  }
  
  public static float getPercent(float current, float max)
  {
    return clamp01(current / max);
  }
}
