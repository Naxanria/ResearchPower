package nl.naxanria.nlib.command.arguments;

import nl.naxanria.nlib.util.CollectionUtil;

public class ArgumentSpecificString extends Argument<String>
{
  public String[] VALID_VALUES;
  
  public ArgumentSpecificString(boolean required, String[] valid_values)
  {
    super(required);
    VALID_VALUES = valid_values;
  }
  
  @Override
  protected boolean setValidValue()
  {
    if (CollectionUtil.contains(VALID_VALUES, args[0]))
    {
      value = args[0];
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
