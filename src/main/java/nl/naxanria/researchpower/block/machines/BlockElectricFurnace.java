package nl.naxanria.researchpower.block.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.researchpower.ModInit;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.researchpower.gui.ModGuiHandler;
import nl.naxanria.researchpower.tile.machines.furnace.TileEntityElectricFurnace;

import javax.annotation.Nullable;

public class BlockElectricFurnace extends BlockTileBase<TileEntityElectricFurnace>
{
  public BlockElectricFurnace()
  {
    super(Material.IRON, "machine_electric_furnace");
    setHardness(0.5f);
    setResistance(4f);
    
  }
  
  @Nullable
  @Override
  public TileEntityElectricFurnace createTileEntity(World world, IBlockState state)
  {
    return new TileEntityElectricFurnace();
  }
  
  @Override
  public Class<TileEntityElectricFurnace> getTileEntityClass()
  {
    return TileEntityElectricFurnace.class;
  }
  
  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!world.isRemote)
    {
      if (!player.isSneaking())
      {
        player.openGui(ResearchPower.getMod(), ModGuiHandler.FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
      }
    }
    
    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }
}
