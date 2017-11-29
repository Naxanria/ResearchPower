package nl.naxanria.researchpower.block.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.researchpower.gui.ModGuiHandler;
import nl.naxanria.researchpower.tile.machines.TileEntityEmpowerer;

import javax.annotation.Nullable;

public class BlockMachineEmpowerer extends BlockTileBase<TileEntityEmpowerer>
{
  public BlockMachineEmpowerer()
  {
    super(Material.IRON, "machine_empowerer");
  }
  
  @Nullable
  @Override
  public TileEntityEmpowerer createTileEntity(World world, IBlockState state)
  {
    return new TileEntityEmpowerer();
  }
  
  @Override
  public Class<TileEntityEmpowerer> getTileEntityClass()
  {
    return TileEntityEmpowerer.class;
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!player.isSneaking())
    {
      if (!world.isRemote)
      {
        
        // open crafting gui
        
        player.openGui(ResearchPower.getMod(), ModGuiHandler.EMPOWERER, world, pos.getX(), pos.getY(), pos.getZ());
        
      }
      return true;
    }
    
    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }
}
