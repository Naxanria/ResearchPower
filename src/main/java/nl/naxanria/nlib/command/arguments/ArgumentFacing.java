package nl.naxanria.nlib.command.arguments;

import net.minecraft.util.EnumFacing;
import nl.naxanria.nlib.util.EnumHelper;

public class ArgumentFacing extends ArgumentSpecificString
{
  public ArgumentFacing(boolean required)
  {
    super(required, EnumHelper.getEnumNames(EnumFacing.class));
  }
}
