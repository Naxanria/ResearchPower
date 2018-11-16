package nl.naxanria.researchpower.tile.machines.furnace;

public class FurnaceData
{
  public final int moduleCount;
  public final float speed;
  public final int power;
  
  public FurnaceData(int moduleCount, float speed, int power)
  {
    this.moduleCount = moduleCount;
    this.speed = speed;
    this.power = power;
  }
  
  public static final FurnaceData[] DATA =
    {
      new FurnaceData(0, 0, 0), // tier 0
      new FurnaceData(1, 1.25f, 20), // 1 --    20/t
      new FurnaceData(2, 1.35f, 35), // 2 --    70/t
      new FurnaceData(4, 1.50f, 65), // 3 --   260/t
      new FurnaceData(8, 60f, 100) // 4 --   800/t
    };
}
