package nl.naxanria.nlib.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import nl.naxanria.nlib.command.arguments.Argument;
import nl.naxanria.nlib.command.arguments.ArgumentBuilder;
import nl.naxanria.nlib.command.arguments.ArgumentInteger;
import nl.naxanria.nlib.util.CollectionUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public abstract class Command extends CommandBase
{
  protected boolean dirty = false;
  
  protected final HashMap<String, ICommand> subCommands = new HashMap<>();
  
  protected List<ICommand> sortedSubCommands = new ArrayList<>();
  
  public final String NAME;
  public final List<String> ALIASES = new ArrayList<>();
  
  protected final ArgumentBuilder ARGUMENTS;
  
  public Command(@Nonnull String NAME, String... aliases)
  {
    this(NAME, null, aliases);
  }
  
  public Command(@Nonnull String NAME, ArgumentBuilder arguments, String... aliases)
  {
    this.NAME = NAME;
    this.ARGUMENTS = arguments;
    ALIASES.addAll(Arrays.asList(aliases));
  }
  
  @Override
  public String getName()
  {
    return NAME;
  }
  
  @Override
  public String getUsage(ICommandSender sender)
  {
    return null;
  }
  
  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
  {
    List<Argument<?>> validArguments = null;
    
    // check if sub command
    if (args.length > 0)
    {
      String c = args[0].toLowerCase();
      if (subCommands.containsKey(c))
      {
        ICommand command = subCommands.get(c);
        command.execute(server, sender, CollectionUtil.shiftArgs(args, 1));
        
        return;
      }
      
      validArguments = ARGUMENTS.getValidArguments(server, sender, args);
    }
    
    if (sender.getCommandSenderEntity() != null && sender.getCommandSenderEntity() instanceof  EntityPlayer )
    {
      playerCommand(server, (EntityPlayer) sender, args, validArguments);
    }
    else
    {
      consoleCommand(server, sender, args, validArguments);
    }
  }
  
  protected abstract void playerCommand(MinecraftServer server, EntityPlayer player, String[] args, List<Argument<?>> validArguments);
  
  protected abstract void consoleCommand(MinecraftServer server, ICommandSender console, String[] args, List<Argument<?>> validArguments);
  
  @Override
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
  {
    //TODO TabCompletions
    return super.getTabCompletions(server, sender, args, targetPos);
  }
  
  public Command markDirty()
  {
    dirty = true;
    return this;
  }
  
  public Command addSubCommand(ICommand command)
  {
    subCommands.put(command.getName().toLowerCase(), command);
    sortedSubCommands.add(command);
    List<String> aliases = command.getAliases();
    for (String a :
      aliases)
    {
      subCommands.put(a.toLowerCase(), command);
    }
    
    markDirty();
    
    return this;
  }
  
  public Collection<ICommand> getSubCommands()
  {
    return sortedSubCommands;
  }
  
  public List<ICommand> getSortedCommandList()
  {
    if (dirty)
    {
      Collections.sort(sortedSubCommands);
    }
    
    return sortedSubCommands;
  }
  
  @Override
  public List<String> getAliases()
  {
    return ALIASES;
  }
}
