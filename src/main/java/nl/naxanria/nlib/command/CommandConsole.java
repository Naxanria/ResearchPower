package nl.naxanria.nlib.command;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import nl.naxanria.nlib.command.arguments.Argument;
import nl.naxanria.nlib.command.arguments.ArgumentBuilder;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class CommandConsole extends Command
{
  public CommandConsole(@Nonnull String NAME, String... aliases)
  {
    super(NAME, aliases);
  }
  
  public CommandConsole(@Nonnull String NAME, ArgumentBuilder arguments, String... aliases)
  {
    super(NAME, arguments, aliases);
  }
  
  @Override
  protected void playerCommand(MinecraftServer server, EntityPlayer player, String[] args, List<Argument<?>> validArguments)
  {
    player.sendMessage(new TextComponentString("This can only be used on the console!"));
  }
}
