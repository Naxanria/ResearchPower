package nl.naxanria.nlib.command.arguments;

public class ArgumentInteger extends Argument<Integer>
{
  protected int min;
  protected int max;
  
  public ArgumentInteger(boolean required)
  {
    this(required, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }
  
  public ArgumentInteger(boolean required, int min, int max)
  {
    super(required);
    this.min = min;
    this.max = max;
  }
  
  @Override
  protected boolean setValidValue()
  {
    try
    {
      int v = Integer.parseInt(args[0]);
      
      if (v < min || v > max)
      {
        return false;
      }
      
      value = v;
    }
    catch (Exception e)
    {
      return false;
    }
    
    return true;
  }
  
  @Override
  public Integer getValue(Integer def)
  {
    return (value != null)? value : (def != null) ? def : 0;
  }
  
  @Override
  public String getUsage()
  {
    return "An integer between " + min + " and " + max;
  }
  
 
  
}
