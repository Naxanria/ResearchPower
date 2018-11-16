package nl.naxanria.nlib.util;

import scala.Int;

public class Numbers
{
  public static int BILLION = 1000000000;
  public static int MILLION = 1000000;
  public static int THOUSAND = 1000;
  
  public static int B(int n)
  {
    return billion(n);
  }
  
  public static int billion(int n)
  {
    long r = n * 1000000000;
    if (r > Integer.MAX_VALUE)
    {
      return Integer.MAX_VALUE;
    }
    return (int) r;
  }
  
  public static int M(int n)
  {
    return million(n);
  }
  
  public static int million(int n)
  {
    return n * 10000000;
  }
  
  public static int K(int n)
  {
    return thousand(n);
  }
  
  public static int thousand(int n)
  {
    return n * 1000;
  }
  
  public static String format(int n)
  {
    return format(n, 2);
  }
  
  public static String format(int n, int round)
  {
    if (n >= BILLION)
    {
      float b = round(n / (float) BILLION, round);
      return b + "B";
    }
    
    if (n >= MILLION)
    {
      float m = round(n / (float) MILLION, round);
      return m + "M";
    }
    
    if (n >= THOUSAND)
    {
      float t = round(n / (float) THOUSAND, round);
      return t + "K";
    }
    
    return Integer.toString(n);
  }
  
  public static float round(float n, int numbers)
  {
    float mul = (int) Math.pow(10, numbers);
    n = Math.round(n * mul) / mul;
    return n;
  }
}
