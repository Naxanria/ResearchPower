package nl.naxanria.nlib.util;

public class CollectionUtil
{
  public static <T> boolean contains(T[] array, T item)
  {
    for (T t :
      array)
    {
      if (t == item)
      {
        return true;
      }
    }
    
    return false;
  }
  
  public static String[] shiftArgs(String[] s)
  {
    return shiftArgs(s, 1);
  }
  
  public static String[] shiftArgs(String[] s, int length)
  {
    if(s == null || s.length == 0)
    {
      return new String[0];
    }
    
    String[] s1 = new String[s.length - length];
    System.arraycopy(s, length, s1, 0, s1.length);
    return s1;
  }
}
