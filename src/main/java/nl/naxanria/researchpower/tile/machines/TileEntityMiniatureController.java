package nl.naxanria.researchpower.tile.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.naxanria.nlib.tile.power.BaseEnergyAcceptor;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.gui.ModGuiHandler;

public class TileEntityMiniatureController extends BaseEnergyAcceptor
{

  public static final int CAPACITY = 20000;
  public static final int MAX_USE = 1500;

  public TileEntityMiniatureController()
  {
    super(CAPACITY, MAX_USE);
  }

  public void makeOwnStructure()
  {
    EnumFacing dir = null;
    if (world.getBlockState(pos.north()) == BlocksInit.Machines.machineFrameBase.getDefaultState()) {
      dir = EnumFacing.NORTH;
    } else if (world.getBlockState(pos.east()) == BlocksInit.Machines.machineFrameBase.getDefaultState()) {
      dir = EnumFacing.EAST;
    } else if (world.getBlockState(pos.south()) == BlocksInit.Machines.machineFrameBase.getDefaultState()) {
      dir = EnumFacing.SOUTH;
    } else if (world.getBlockState(pos.west()) == BlocksInit.Machines.machineFrameBase.getDefaultState()) {
      dir = EnumFacing.WEST;
    }

    System.out.println(dir);

    EnumFacing offsetDir = dir.rotateAround(EnumFacing.Axis.Y).getOpposite();

    BlockPos basePos = pos.offset(offsetDir, 2);

    boolean valid = true;

    outerloop:
    for (int x = 0; x <= 4; x++)
    {
      BlockPos curX = basePos.offset(offsetDir.getOpposite(), x);
      for (int y = 0; y <= 4; y++)
      {
        BlockPos curY = curX.offset(EnumFacing.UP, y);
        for (int z = 0; z <= 4; z++)
        {
          BlockPos ourPos = curY.offset(dir, z);
          if (world.getBlockState(ourPos) != BlocksInit.Machines.Miniature.miniatureStructure[x][y][z])
          {
            valid = false;
            break outerloop;
          }

        }
      }
    }

    System.out.println(valid);
  }
}
