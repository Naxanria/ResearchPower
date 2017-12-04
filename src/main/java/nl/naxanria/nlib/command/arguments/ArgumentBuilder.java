package nl.naxanria.nlib.command.arguments;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import nl.naxanria.nlib.util.CollectionUtil;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class ArgumentBuilder
{
  private List<List<Argument<?>>> arguments;
  
  public ArgumentBuilder(Argument<?>... arguments)
  {
    this.arguments = new ArrayList<>();
    next(arguments);
  }
  
  @SuppressWarnings("unchecked")
  public ArgumentBuilder next(Argument<?>... arguments)
  {
    this.arguments.add(new ArrayList<>(Arrays.asList(arguments)));
    
    return this;
  }
  
  public List<Argument<?>> getArguments(int index)
  {
    return arguments.get(index);
  }
  
  public boolean hasArguments(int layer)
  {
    return layer < arguments.size();
  }
  
  public List<Argument<?>> getValidArguments(MinecraftServer server, ICommandSender sender, String[] args)
  {
    List<Argument<?>> out = new ArrayList<>();
    
    outer:
    for (List<Argument<?>> l :
      arguments)
    {
      for (Argument<?> argument :
        l)
      {
        if (argument.populate(server, sender, args))
        {
          out.add(argument);
          args = CollectionUtil.shiftArgs(args, argument.argLength());
          continue outer;
        }
      }
    }
    
    return out;
  }
}
