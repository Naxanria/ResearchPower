package nl.naxanria.nlib.command.arguments;

import net.minecraft.util.EnumFacing;
import nl.naxanria.nlib.util.CollectionUtil;
import nl.naxanria.nlib.util.EnumHelper;

public class ArgumentFacing extends Argument<EnumFacing>
{
  private String[] names;
  
  public ArgumentFacing(boolean required)
  {
    super(required);
    names = EnumHelper.getEnumNames(EnumFacing.class);
  }
  
  @Override
  protected boolean setValidValue()
  {
    String name = args[0].toUpperCase();
    if (CollectionUtil.contains(names, name))
    {
      value = EnumFacing.byName(name);
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
