package nl.naxanria.researchpower.block.machines.miniature;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import nl.naxanria.nlib.block.BlockTileBase;
import nl.naxanria.researchpower.tile.machines.TileEntityMiniatureController;

import javax.annotation.Nullable;

public class BlockMiniatureController extends BlockTileBase<TileEntityMiniatureController>
{
  public BlockMiniatureController()
  {
    super(Material.IRON, "miniature_controller");
  }

  @Nullable
  @Override
  public TileEntityMiniatureController createTileEntity(World world, IBlockState state)
  {
    return new TileEntityMiniatureController();
  }

  @Override
  public Class<TileEntityMiniatureController> getTileEntityClass()
  {
    return TileEntityMiniatureController.class;
  }


  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!player.isSneaking())
    {
      if (!world.isRemote)
      {
        TileEntityMiniatureController controller = getTileEntity(world, pos);
        
        if (!controller.isStructureGood())
        {
          boolean g = controller.makeOwnStructure();
          player.sendMessage(new TextComponentString("Controller is " + g));
        }
        else
        {
          if (!controller.isInProgress())
          {
            controller.startRecipe();
            player.sendStatusMessage(new TextComponentString("Trying to start the recipe"), true);
          }
          else
          {
            player.sendStatusMessage(new TextComponentString("Progress: " + controller.getProgress() + " " + controller.getTotalTime()), true);
          }
        }
      }
      return true;
    }

    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }
}
