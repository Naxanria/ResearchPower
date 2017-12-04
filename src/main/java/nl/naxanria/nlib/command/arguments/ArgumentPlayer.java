package nl.naxanria.nlib.command.arguments;

import net.minecraft.entity.player.EntityPlayer;
import nl.naxanria.nlib.util.player.PlayerHelper;

public class ArgumentPlayer extends Argument<EntityPlayer>
{
  public ArgumentPlayer(boolean required)
  {
    super(required);
  }
  
  @Override
  protected boolean setValidValue()
  {
    String name = args[0];
    EntityPlayer p = PlayerHelper.getPlayerFromUsername(name);
    if (p != null)
    {
      value = p;
      return true;
    }
    
    return false;
  }
  
  @Override
  public String getUsage()
  {
    return null;
  }
}
