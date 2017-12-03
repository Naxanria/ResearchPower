package nl.naxanria.nlib.util;

public class Flags <T extends Enum<?>> implements Cloneable
{
  protected long flags;
  
  public Flags()
  { }
  
  @SafeVarargs
  public Flags(T... f)
  {
    enableFlags(f);
  }
  
  public long getAsFlag(T e)
  {
    return 1L << e.ordinal();
  }
  
  public boolean hasFlag(T f)
  {
    long flag = getAsFlag(f);
    return ((flag & flags) != 0);
  }
  
  @SafeVarargs
  public final boolean hasFlags(T... f)
  {
    long val = 0;
    for (T t:
      f)
    {
      val |= getAsFlag(t);
    }
    
    return (val & flags) != 0;
  }
  
  public boolean hasFlags(Flags<T> flags)
  {
    return ((flags.flags & this.flags) != 0);
  }
  
  public Flags setFlag(T f, boolean state)
  {
    long flag = getAsFlag(f);
    if (state)
    {
      flags |= flag;
    }
    else
    {
      flags &= ~flag;
    }
    
    return this;
  }
  
  public final Flags enableFlag(T t)
  {
    setFlag(t, true);
    
    return this;
  }
  
  @SafeVarargs
  public final Flags enableFlags(T... f)
  {
    for (T t :
      f)
    {
      setFlag(t, true);
    }
    
    return this;
  }
  
  public final Flags disableFlag(T t)
  {
    setFlag(t, false);
    
    return this;
  }
  
  @SafeVarargs
  public final Flags disableFlags(T... f)
  {
    for (T t :
      f)
    {
      setFlag(t, false);
    }
  
    return this;
  }
  
  @Override
  public boolean equals(Object obj)
  {
    return super.equals(obj);
  }
  
  private Flags setFlags(long flags)
  {
    this.flags = flags;
    
    return this;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  protected Flags<T> clone() throws CloneNotSupportedException
  {
    return ((Flags<T>) super.clone()).setFlags(flags);
  }
}
