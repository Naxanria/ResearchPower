package nl.naxanria.nlib.command.arguments;

public class ArgumentBoolean extends Argument<Boolean>
{
  public ArgumentBoolean(boolean required)
  {
    super(required);
  }
  
  @Override
  protected boolean setValidValue()
  {
    String arg = args[0];
    
    if (arg.equalsIgnoreCase("true"))
    {
      value = true;
      return true;
    }
    else if (arg.equalsIgnoreCase("false"))
    {
      value = false;
      return true;
    }
    
    value = null;
    return false;
  }
  
  @Override
  public String getUsage()
  {
    return null;
  }
}
