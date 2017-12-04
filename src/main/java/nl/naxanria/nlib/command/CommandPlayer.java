package nl.naxanria.nlib.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import nl.naxanria.nlib.command.arguments.Argument;
import nl.naxanria.nlib.command.arguments.ArgumentBuilder;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class CommandPlayer extends Command
{
  public CommandPlayer(@Nonnull String NAME, String... aliases)
  {
    super(NAME, aliases);
  }
  
  public CommandPlayer(@Nonnull String NAME, ArgumentBuilder arguments, String... aliases)
  {
    super(NAME, arguments, aliases);
  }
  
  @Override
  protected final void consoleCommand(MinecraftServer server, ICommandSender console, String[] args, List<Argument<?>> validArguments)
  {
    console.sendMessage(new TextComponentString("This can only be used by a player!"));
  }
}
