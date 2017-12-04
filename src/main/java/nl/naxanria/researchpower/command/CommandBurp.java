package nl.naxanria.researchpower.command;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import nl.naxanria.nlib.command.CommandPlayer;
import nl.naxanria.nlib.command.arguments.Argument;

import java.util.List;

public class CommandBurp extends CommandPlayer
{
  public CommandBurp()
  {
    super("burp", "b");
  }
  
  @Override
  protected void playerCommand(MinecraftServer server, EntityPlayer player, String[] args, List<Argument<?>> validArguments)
  {
    player.sendMessage(new TextComponentString("BURP"));
  }
}
