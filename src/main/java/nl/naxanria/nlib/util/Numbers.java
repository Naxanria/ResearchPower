package nl.naxanria.nlib.util;

public class Numbers
{
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
}
