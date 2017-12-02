package nl.naxanria.nlib.tile;

public enum TileFlags
{
  SaveOnWorldChange,
  HasOwner,
  DropInventory,
  KeepNBTData;
  
  public final long FLAG;
  
  TileFlags()
  {
    this.FLAG = 1 << ordinal();
  }
}
