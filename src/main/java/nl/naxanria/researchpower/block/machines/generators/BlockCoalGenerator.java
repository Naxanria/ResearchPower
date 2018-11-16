package nl.naxanria.researchpower.block.machines.generators;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.nlib.block.BlockTileGuiBase;
import nl.naxanria.researchpower.gui.ModGuiHandler;
import nl.naxanria.researchpower.tile.machines.generators.TileEntityCoalGenerator;

import javax.annotation.Nullable;

public class BlockCoalGenerator extends BlockTileGuiBase<TileEntityCoalGenerator>
{
  public BlockCoalGenerator()
  {
    super(Material.IRON, "generator_coal", ModGuiHandler.GENERATOR_COAL);
  }
  
//  @Override
//  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
//  {
//    if (!worldIn.isRemote && !playerIn.isSneaking())
//    {
//      playerIn.openGui(NMod.getMod(), ModGuiHandler.GENERATOR_COAL, worldIn, pos.getX(), pos.getY(), pos.getZ());
//    }
//
//    return true;
//  }
  
  @Nullable
  @Override
  public TileEntityCoalGenerator createTileEntity(World world, IBlockState state)
  {
    return new TileEntityCoalGenerator();
  }
  
  @Override
  public Class<TileEntityCoalGenerator> getTileEntityClass()
  {
    return TileEntityCoalGenerator.class;
  }
}
