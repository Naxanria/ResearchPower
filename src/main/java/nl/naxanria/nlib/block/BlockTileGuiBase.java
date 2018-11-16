package nl.naxanria.nlib.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.naxanria.nlib.NMod;
import nl.naxanria.nlib.tile.TileEntityBase;

public abstract class BlockTileGuiBase<TTE extends TileEntityBase> extends BlockTileBase<TTE>
{
  public final int GUI_ID;
  
  public BlockTileGuiBase(Material blockMaterialIn, String name, int guiID)
  {
    super(blockMaterialIn, name);
    
    GUI_ID = guiID;
  }
  
  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!worldIn.isRemote && !playerIn.isSneaking())
    {
      playerIn.openGui(NMod.getMod(), GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
    }
  
    return true;
  }
}
