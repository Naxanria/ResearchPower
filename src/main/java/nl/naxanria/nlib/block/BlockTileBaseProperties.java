package nl.naxanria.nlib.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import nl.naxanria.nlib.tile.TileEntityBase;

public abstract class BlockTileBaseProperties<P extends IProperty, TTE extends TileEntityBase> extends BlockTileBaseInternal<P, TTE>
{
  public BlockTileBaseProperties(Material blockMaterialIn, String name, P property)
  {
    super(blockMaterialIn, name, property);
  }
}
