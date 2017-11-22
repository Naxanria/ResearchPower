package nl.naxanria.nlib.util;

public class MathUtil
{
  public static float lerp(float a, float b, float t)
  {
    return (1 - t) * a + t * b;
  }
}
