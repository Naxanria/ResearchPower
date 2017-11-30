package nl.naxanria.nlib.util.logging;

public class LogColor
{
  public static final String RESET = "\u001B[0m";
  public static final LogColor BLACK =  new LogColor("\u001B[30m");
  public static final LogColor RED =    new LogColor("\u001B[31m");
  public static final LogColor GREEN =  new LogColor("\u001B[32m");
  public static final LogColor YELLOW = new LogColor("\u001B[33m");
  public static final LogColor BLUE =   new LogColor("\u001B[34m");
  public static final LogColor PURPLE = new LogColor("\u001B[35m");
  public static final LogColor CYAN =   new LogColor("\u001B[36m");
  public static final LogColor WHITE =  new LogColor("\u001B[37m");
  
  public static class Background
  {
    public static final LogColor BLACK =  new LogColor("\u001B[40m");
    public static final LogColor RED =    new LogColor("\u001B[41m");
    public static final LogColor GREEN =  new LogColor("\u001B[42m");
    public static final LogColor YELLOW = new LogColor("\u001B[43m");
    public static final LogColor BLUE =   new LogColor("\u001B[44m");
    public static final LogColor PURPLE = new LogColor("\u001B[45m");
    public static final LogColor CYAN =   new LogColor("\u001B[46m");
    public static final LogColor WHITE =  new LogColor("\u001B[47m");
  }
  
  public final String CODE;
  
  public LogColor(String CODE)
  {
    this.CODE = CODE;
  }
  
  public String getColored(String msg)
  {
    return CODE + msg + RESET;
  }
  
  @Override
  public String toString()
  {
    return CODE;
  }
}
