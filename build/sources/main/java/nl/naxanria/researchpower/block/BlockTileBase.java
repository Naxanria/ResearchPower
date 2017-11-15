package nl.naxanria.researchpower.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nl.naxanria.researchpower.tile.TileEntityBase;

import javax.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public abstract class BlockTileBase<TE extends TileEntityBase> extends BlockBase
{
  public BlockTileBase(Material blockMaterialIn, String name)
  {
    super(blockMaterialIn, name);
  }
  
  @SuppressWarnings("unchecked")
  public TE getTileEntity(IBlockAccess world, BlockPos pos)
  {
    return (TE) world.getTileEntity(pos);
  }
  
  @Override
  public boolean hasTileEntity(IBlockState state)
  {
    return true;
  }
  
  @Nullable
  @Override
  public abstract TE createTileEntity(World world, IBlockState state);
  
  public abstract Class<TE> getTileEntityClass();
  
  @SuppressWarnings("ConstantConditions")
  public void registerTileEntity()
  {
    GameRegistry.registerTileEntity(getTileEntityClass(), getRegistryName().toString());
  }
  
  @Override
  public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
  {
    neighbourUpdate(world, pos);
  }
  
  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
  {
    neighbourUpdate(world, pos);
  }
  
  private void neighbourUpdate(IBlockAccess world, BlockPos pos)
  {
    TE tile = getTileEntity(world, pos);
  
    if (tile.shouldSaveDataOnChangeOrWorldStart())
    {
      tile.saveDataOnChangeOrWorldStart();
    }
  }
  
  @Override
  public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos)
  {
    return getTileEntity(world, pos).getComparatorStrength();
  }
}
