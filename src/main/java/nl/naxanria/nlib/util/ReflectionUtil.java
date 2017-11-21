package nl.naxanria.nlib.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil
{
  public static <T> T getFirstField(Object instance, Class<T> fieldClass)
    throws IllegalAccessException
  {
    Class<?> clazz = instance.getClass();
    Field[] fields = clazz.getFields();
    for (Field field :
      fields)
    {
      if (field.getClass() == fieldClass)
      {
        return (T) field.get(instance);
      }
    }
    
    return null;
  }
  
  public static <T> List<T> getAllFields(Object instance, Class<T> fieldClass)
    throws IllegalAccessException
  {
    ArrayList<T> values = new ArrayList<>();
    
    Class<?> clazz = instance.getClass();
    Field[] fields = clazz.getFields();
    for (Field field :
      fields)
    {
      if (field.getClass() == fieldClass)
      {
        values.add((T) field.get(instance));
      }
    }
    
    return values;
  }
}
