package nl.naxanria.nlib.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import nl.naxanria.nlib.tile.TileEntityBase;

public abstract class BlockTileBase<TTE extends TileEntityBase> extends BlockTileBaseInternal<IProperty, TTE>
{
  public BlockTileBase(Material blockMaterialIn, String name)
  {
    super(blockMaterialIn, name);
  }
}
