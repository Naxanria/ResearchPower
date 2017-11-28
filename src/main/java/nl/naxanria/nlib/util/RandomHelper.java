package nl.naxanria.nlib.util;

import java.util.List;
import java.util.Random;

public class RandomHelper
{
  public static boolean chance(Random random, float chance)
  {
    return random.nextFloat() <= chance;
  }
  
  public static boolean chance(Random random, int chance)
  {
    return random.nextInt(100) <= chance;
  }
  
  public static <T> T choose(Random random, List<T> list)
  {
    if (list.isEmpty())
    {
      return null;
    }
    
    return list.get(random.nextInt(list.size()));
  }
  
  public static <T> T choose(Random random, T[] array)
  {
    return array[random.nextInt(array.length)];
  }
}
