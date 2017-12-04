package nl.naxanria.researchpower.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import nl.naxanria.nlib.command.Command;
import nl.naxanria.nlib.command.arguments.Argument;
import nl.naxanria.nlib.command.arguments.ArgumentBuilder;
import nl.naxanria.nlib.command.arguments.ArgumentInteger;

import java.util.List;

public class CommandTest extends Command
{
  public CommandTest()
  {
    super("test", new ArgumentBuilder(new ArgumentInteger(true, -20, 20)).next(new ArgumentInteger(true, -20, 20)), "t");
    addSubCommand(new CommandBurp());
  }
  
  @Override
  protected void playerCommand(MinecraftServer server, EntityPlayer player, String[] args, List<Argument<?>> validArguments)
  {
    handle(player, validArguments);
  }
  
  @Override
  protected void consoleCommand(MinecraftServer server, ICommandSender console, String[] args, List<Argument<?>> validArguments)
  {
    handle(console, validArguments);
  }
  
  protected void handle(ICommandSender sender, List<Argument<?>> validArguments)
  {
    if (validArguments == null)
    {
      sender.sendMessage(new TextComponentString("Requires 2 numbers!"));
    }
    else if (validArguments.size() == 2)
    {
      int a = ArgumentInteger.staticValue(validArguments.get(0));
      int b = ArgumentInteger.staticValue(validArguments.get(1));
      
      sender.sendMessage(new TextComponentString( a + " + " + b + " = " + (a + b)));
    }
  }
}
