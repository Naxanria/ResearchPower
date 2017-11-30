package nl.naxanria.nlib.container;

import net.minecraft.inventory.IContainerListener;

import java.util.HashMap;
import java.util.List;
import java.util.function.*;

public class ContainerSyncHelper
{
  public static class SyncData
  {
    //private static int nextID;
    public int value;
    public final String name;
    public final int id;

    public final Consumer<Integer> update;
    public final Supplier<Integer> read;
  
    public SyncData(String name, int value, Consumer<Integer> update, Supplier<Integer> read)
    {
      this.value = value;
      this.name = name;
      this.update = update;
      this.read = read;
      
      id = name.hashCode();
    }
  
    public boolean doCompare()
    {
      return value == doRead();
    }
    
    public void doUpdate(int nValue)
    {
      update.accept(nValue);
    }
    
    public int doRead()
    {
      return read.get();
    }
  }
  
  public final HashMap<String, SyncData> names = new HashMap<>();
  public final HashMap<Integer, SyncData> ids = new HashMap<>();
  public final ContainerBase container;
  public final List<IContainerListener> listeners;
  
  public ContainerSyncHelper(ContainerBase container, List<IContainerListener> listeners)
  {
    this.container = container;
    this.listeners = listeners;
  }
  
  public int create(String name, Consumer<Integer> update, Supplier<Integer> read)
  {
    SyncData pair = getByName(name);
    if (pair == null)
    {
      pair = new SyncData(name, read.get(), update, read);
      
      ids.put(pair.id, pair);
      names.put(name, pair);
    }
  
    //Log.info("Created " + LogColor.PURPLE.getColored(name) + " with id: " + LogColor.CYAN.getColored("" + pair.id));
    return pair.id;
  }
  
  public void compare(String name, int realValue)
  {
    SyncData pair = getByName(name);
    if (pair == null)
    {
      return;
    }
    
    if (pair.value != realValue)
    {
      pair.value = realValue;
      
    }
  }
  
  public void compareAll()
  {
    for (int id :
      ids.keySet())
    {
      SyncData data = ids.get(id);
      if (!data.doCompare())
      {
        sendUpdates(id, data.doRead());
      }
    }
  }
  
  protected void sendUpdates(int id, int data)
  {
    for (IContainerListener listener :
      listeners)
    {
      listener.sendWindowProperty(container, id, data);
    }
  }
  
  protected void getUpdate(int id, int data)
  {
    SyncData pair = getById(id);
    if (pair == null)
    {
      return;
    }
    
    pair.value = data;
    pair.update.accept(data);
  }
  
  public int getValue(String name)
  {
    return getValue(name, 0);
  }
  
  public int getValue(String name, int def)
  {
    return getValue(getByName(name), def);
  }
  
  public int getValue(int id)
  {
    return getValue(id, 0);
  }
  
  public int getValue(int id, int def)
  {
    return getValue(getById(id), def);
  }
  
  private int getValue(SyncData pair, int def)
  {
    return pair == null ? def : pair.value;
  }
  
  public int getId(String name)
  {
    SyncData pair = getByName(name);
    return pair == null ? -1 : pair.id;
  }
  
  public SyncData getByName(String name)
  {
    return names.getOrDefault(name, null);
  }
  
  public SyncData getById(int id)
  {
    return ids.getOrDefault(id, null);
  }
  
  
}
