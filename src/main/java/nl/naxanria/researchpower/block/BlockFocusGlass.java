package nl.naxanria.researchpower.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.naxanria.nlib.block.BlockBase;

public class BlockFocusGlass extends BlockBase
{
  public BlockFocusGlass()
  {
    super(Material.GLASS, "glass_focus");
    
    
  }
  
  @Override
  public boolean isOpaqueCube(IBlockState state)
  {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
  {
    IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
    Block block = iblockstate.getBlock();
    
//    if (this == Blocks.GLASS || this == Blocks.STAINED_GLASS)
//    {
//      if (blockState != iblockstate)
//      {
//        return true;
//      }
//
//      if (block == this)
//      {
//        return false;
//      }
//    }
    
    return block != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
  }
  
  @Override
  public BlockRenderLayer getBlockLayer()
  {
    return BlockRenderLayer.CUTOUT;
  }
  
  @Override
  public int getLightOpacity(IBlockState state)
  {
    return 0;
  }
  
  @Override
  public boolean isFullCube(IBlockState state)
  {
    return false;
  }
}
