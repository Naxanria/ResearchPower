package nl.naxanria.nlib.util.logging;

import nl.naxanria.nlib.NMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log
{
  private static Logger logger;
  
  public Log()
  {
    logger = LogManager.getLogger(NMod.getModId());
  }
  
  public static void info(String message)
  {
    logger.info(message);
  }
  
  public static void info(LogColor color, String message)
  {
    info(color.getColored(message));
  }
  
  public static void error(String message)
  {
    logger.error(LogColor.RED.getColored(message));
  }
  
  public static void warn(String message)
  {
    logger.warn(LogColor.YELLOW.getColored(message));
  }
}
