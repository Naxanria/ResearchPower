package nl.naxanria.researchpower.block.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.researchpower.gui.ModGuiHandler;
import nl.naxanria.researchpower.tile.machines.TileEntityMachineSanding;

import javax.annotation.Nullable;

public class BlockMachineSanding extends BlockTileBase<TileEntityMachineSanding>
{
  public BlockMachineSanding()
  {
    super(Material.IRON, "machine_sanding");
  }
  
  @Nullable
  @Override
  public TileEntityMachineSanding createTileEntity(World world, IBlockState state)
  {
    return new TileEntityMachineSanding();
  }
  
  @Override
  public Class<TileEntityMachineSanding> getTileEntityClass()
  {
    return TileEntityMachineSanding.class;
  }
  
  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!worldIn.isRemote)
    {
      if (!playerIn.isSneaking())
      {
        FluidTank tank = getTileEntity(worldIn, pos).tank;
        if (!useItemOnTank(playerIn, hand, tank))
        {
          playerIn.openGui(ResearchPower.getMod(), ModGuiHandler.SANDING, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
      }
    }
    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
  }
}
