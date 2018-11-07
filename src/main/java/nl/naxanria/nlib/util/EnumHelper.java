package nl.naxanria.nlib.util;

import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Arrays;

public class EnumHelper
{
  public static class Facing
  {
    public final static EnumFacing[] ALL = EnumFacing.VALUES;
    public final static EnumFacing[] SIDES = EnumFacing.HORIZONTALS;
    public final static EnumFacing[] TOP_BOTTOM = { EnumFacing.UP, EnumFacing.DOWN };
    
    public static EnumFacing[] combine(EnumFacing[] array, EnumFacing... faces)
    {
      ArrayList<EnumFacing> arrayList = new ArrayList<>();
      arrayList.addAll(Arrays.asList(array));
  
      for (EnumFacing f :
        faces)
      {
        if (!arrayList.contains(f))
        {
          arrayList.add(f);
        }
      }
  
      EnumFacing[] out = new EnumFacing[arrayList.size()];
      return arrayList.toArray(out);
    }
  
    public static EnumFacing right(EnumFacing facing)
    {
      switch (facing)
      {
      
        case DOWN:
        case NORTH:
        case UP:
          return EnumFacing.EAST;
      
        case SOUTH:
          return EnumFacing.WEST;
      
        case WEST:
          return EnumFacing.NORTH;
      
        case EAST:
          return EnumFacing.SOUTH;
      }
    
      return facing;
    }
  
    public static EnumFacing left(EnumFacing facing)
    {
      switch (facing)
      {
      
        case DOWN:
        case NORTH:
        case UP:
          return EnumFacing.WEST;
      
        case SOUTH:
          return EnumFacing.EAST;
      
        case WEST:
          return EnumFacing.SOUTH;
      
        case EAST:
          return EnumFacing.NORTH;
      }
    
      return facing;
    }
  }
  
  
  
  public static long getAsFlag(Enum<?> e)
  {
    return 1 << e.ordinal();
  }
  
  public static long getAsFlags(Enum<?>... e)
  {
    long val = 0;
    for (Enum<?> f:
      e)
    {
      val |= 1 << f.ordinal();
    }
    
    return val;
  }
  
  public static boolean hasFlags(long input, long flags)
  {
    return (input & flags) != 1;
  }
  
  public static boolean hasFlags(long input, Enum<?>... flags)
  {
    return hasFlags(input, getAsFlags(flags));
  }
  
  public static Enum<?>[] getFromFlags(Class<Enum<?>> e, long flags)
  {
    ArrayList<Enum<?>> output = new ArrayList<>();
    for (Enum<?> c :
      e.getEnumConstants())
    {
      if ((flags & (1 << c.ordinal())) != 0)
      {
        output.add(c);
      }
    }
  
    Enum<?>[] array = new Enum[output.size()];
    
    output.toArray(array);
    
    return array;
  }
  
  public static <T extends Enum<?>> String[] getEnumNames(Class<T> enumClass)
  {
    return getEnumNames(enumClass, false);
  }
  public static <T extends Enum<?>> String[] getEnumNames(Class<T> enumClass, boolean lowerCase)
  {
    Enum[] constants =  enumClass.getEnumConstants();
    String[] output = new String[constants.length];
    for (int i = 0; i < constants.length; i++)
    {
      output[i] = (lowerCase) ? constants[i].name().toLowerCase() :  constants[i].name();
    }
    
    return output;
  }
  
  public static <T extends Enum<?>> Enum<?> getFromName(Class<T> enumClass, String name)
  {
    for (Enum e :
      enumClass.getEnumConstants())
    {
      if (e.name().equalsIgnoreCase(name))
      {
        return e;
      }
    }
    
    return null;
  }
}
