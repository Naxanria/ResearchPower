package nl.naxanria.nlib.util.collections;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ReadonlyList<T> implements Iterable<T>
{
  private final List<T> source;
  
  private class Itr implements Iterator<T>
  {
    int cursor;
    
    @Override
    public boolean hasNext()
    {
      return cursor != source.size();
    }
  
    @Override
    public T next()
    {
      int i = cursor;
      if (i > source.size())
      {
        throw new NoSuchElementException();
      }
      
      cursor = i + 1;
      
      return source.get(i);
    }
  }
  
  public ReadonlyList(@NotNull List<T> source)
  {
    this.source = source;
    ArrayList<T> t = new ArrayList<>();
  }
  
  public T get(int index)
  {
    return source.get(index);
  }
  
  public int size()
  {
    return source.size();
  }
  
  public Object[] toArray()
  {
    return source.toArray();
  }
  
  @Override
  public Iterator<T> iterator()
  {
    return new Itr();
  }
}
