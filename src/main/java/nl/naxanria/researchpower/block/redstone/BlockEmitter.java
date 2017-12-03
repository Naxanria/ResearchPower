package nl.naxanria.researchpower.block.redstone;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockBase;
import nl.naxanria.nlib.util.player.PlayerHelper;
import nl.naxanria.researchpower.block.fluid.BlockFluidDrum;

import java.util.Random;

public class BlockEmitter extends BlockBase<PropertyInteger>
{
  public BlockEmitter(PropertyInteger property)
  {
    super(Blocks.REDSTONE_BLOCK.getMaterial(Blocks.REDSTONE_BLOCK.getDefaultState()), "redstone_emitter", property);
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!world.isRemote)
    {
      int m = getMetaFromState(state);
    
      if (player.isSneaking())
      {
        m--;
      }
      else
      {
        m++;
      }
    
      if (m < 0)
      {
        m = 15;
      }
      if (m > 15)
      {
        m = 0;
      }
    
      IBlockState s = getStateFromMeta(m);
    
      world.setBlockState(pos, s);
    }
    
    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }
  
  
  
  @Override
  public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
  {
    super.onBlockClicked(world, pos, player);
  }
  
  @Override
  public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
  {
    
    return redstonePower(blockState);
  }
  
  @Override
  public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
  {
    return redstonePower(blockState);
  }
  
  @Override
  public boolean canProvidePower(IBlockState state)
  {
    return redstonePower(state) != 0;
  }
  
  public int redstonePower(IBlockState state)
  {
    return state.getValue(PROPERTY);
  }
  
  public static BlockEmitter createStateVersion(PropertyInteger property)
  {
    tempProperty = property;
    BlockEmitter block = new BlockEmitter(property);
    tempProperty = null;
    return block;
  }
}
