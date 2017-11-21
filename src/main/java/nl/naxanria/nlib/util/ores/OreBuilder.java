package nl.naxanria.nlib.util.ores;

import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.nlib.item.ItemBase;

public class OreBuilder
{
  protected String name;
  protected BlockBase ore;
  protected BlockBase fullBlock;
  protected ItemBase ingot;
  protected ItemBase nugget;
  protected ItemBase dust;
  protected ItemBase plate;
  
  public OreBuilder(String name)
  {
    this.name = name;
  }
  
  public OreBuilder ore(BlockBase ore)
  {
    this.ore = ore;
    return this;
  }
  
  public OreBuilder fullBlock(BlockBase fullBlock)
  {
    this.fullBlock = fullBlock;
    return this;
  }
  
  public OreBuilder ingot(ItemBase ingot)
  {
    this.ingot = ingot;
    return this;
  }
  
  public OreBuilder nugget(ItemBase nugget)
  {
    this.nugget = nugget;
    return this;
  }
  
  public OreBuilder dust(ItemBase dust)
  {
    this.dust = dust;
    return this;
  }
  
  public OreBuilder plate(ItemBase plate)
  {
    this.plate = plate;
    return this;
  }
  
  
}
