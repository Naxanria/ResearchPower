package nl.naxanria.researchpower.block.machines.research;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockTileGuiBase;
import nl.naxanria.researchpower.gui.ModGuiHandler;
import nl.naxanria.researchpower.tile.machines.research.TileEntityResearchManager;

import javax.annotation.Nullable;

public class BlockResearchManager extends BlockTileGuiBase<TileEntityResearchManager>
{
  public BlockResearchManager()
  {
    super(Material.IRON, "research_manager", ModGuiHandler.RESEARCH_MANAGER);
  }
  
  @Nullable
  @Override
  public TileEntityResearchManager createTileEntity(World world, IBlockState state)
  {
    return new TileEntityResearchManager();
  }
  
  @Override
  public Class<TileEntityResearchManager> getTileEntityClass()
  {
    return TileEntityResearchManager.class;
  }
  
//  @Override
//  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
//  {
//    if (!worldIn.isRemote && !playerIn.isSneaking())
//    {
//      playerIn.openGui(NMod.getMod(), ModGuiHandler.RESEARCH_MANAGER, worldIn, pos.getX(), pos.getY(), pos.getZ());
//    }
//
//    return true;
//  }
}
