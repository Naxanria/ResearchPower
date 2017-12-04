package nl.naxanria.nlib.command.arguments;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public abstract class Argument<T>
{
  protected boolean required;
  
  protected String[] args;
  
  protected MinecraftServer server;
  protected ICommandSender sender;
  
  protected T value;
  protected boolean valid;
  
  public Argument(boolean required)
  {
    this.required = required;
  }
  
  public int argLength()
  {
    return 1;
  }
  
  public T getValue()
  {
    return getValue(null);
  }
  
  public T getValue(T def)
  {
    return isValid() ? value : def;
  }
  
  public boolean isValid()
  {
    return valid;
  }
  
  protected abstract boolean setValidValue();
  
  public abstract String getUsage();
  
  public boolean populate(MinecraftServer server, ICommandSender sender, String args[])
  {
    this.args = args;
    this.server = server;
    this.sender = sender;
    
    return valid = setValidValue();
  }
  
  public List<String> tabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
  {
    return null;
  }
  
  public boolean isRequired()
  {
    return required;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> T value(Argument<?> argument)
  {
    return (T) argument.getValue();
  }

}
