package nl.naxanria.researchpower.util;

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
}
