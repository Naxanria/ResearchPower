package nl.naxanria.researchpower.tile.machines;


import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import nl.naxanria.nlib.tile.power.BaseEnergyAcceptor;
import nl.naxanria.nlib.util.Log;
import nl.naxanria.nlib.util.RandomHelper;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.recipe.MiniatureRecipeRegistry;
import nl.naxanria.researchpower.recipe.RecipeMiniature;

public class TileEntityMiniatureController extends BaseEnergyAcceptor
{
  public static final int CAPACITY = 20000;
  public static final int MAX_USE = 1500;
  
  protected boolean structureGood;
  
  protected int progress = 0;
  protected int totalTime = 500;
  protected boolean inProgress = false;
  protected int baseLightningChance = 2;
  protected int lightningChance = 0;
  protected RecipeMiniature currentRecipe;
  
  EnumFacing dir = null;
  
  public TileEntityMiniatureController()
  {
    super(CAPACITY, MAX_USE);
  }
  
  public boolean makeOwnStructure()
  {
    if (world.getBlockState(pos.north()) == BlocksInit.Machines.MACHINE_FRAME_BASE.getDefaultState())
    {
      dir = EnumFacing.NORTH;
    }
    else if (world.getBlockState(pos.east()) == BlocksInit.Machines.MACHINE_FRAME_BASE.getDefaultState())
    {
      dir = EnumFacing.EAST;
    }
    else if (world.getBlockState(pos.south()) == BlocksInit.Machines.MACHINE_FRAME_BASE.getDefaultState())
    {
      dir = EnumFacing.SOUTH;
    }
    else if (world.getBlockState(pos.west()) == BlocksInit.Machines.MACHINE_FRAME_BASE.getDefaultState())
    {
      dir = EnumFacing.WEST;
    }
    if (dir == null)
    {
      return false;
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

    structureGood = valid;
    
    return structureGood;
  }
  
  public boolean isStructureGood()
  {
    return structureGood;
  }
  
  public IBlockState[] getBlocksInside()
  {
    IBlockState[] blocks = new IBlockState[27];
    
    BlockPos searchPos = pos.offset(dir);
    Log.warn(searchPos.toString());
    
    return blocks;
  }
  
  public RecipeMiniature checkRecipe()
  {
    IBlockState[] blocks = getBlocksInside();
  
    return MiniatureRecipeRegistry.getRecipeFromInput(blocks);
  }
  
  public void startRecipe()
  {
    RecipeMiniature r = checkRecipe();
    if (r != null)
    {
      currentRecipe = r;
      progress = 0;
      inProgress = true;
    }
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
    
    if (!world.isRemote)
    {
      if (inProgress)
      {
        progress++;
        
        if (RandomHelper.chance(world.rand, (lightningChance += baseLightningChance)))
        {
          lightningChance = baseLightningChance;
  
          EntityLightningBolt bolt = new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), true);
          world.spawnEntity(bolt);
  
          Log.warn("Lightning!!");
        }
        
        if (progress >= totalTime)
        {
          // remove the blocks
          // spawn the item
          
          progress = 0;
          currentRecipe = null;
          inProgress = false;
        }
      }
    }
  }
  
  public boolean isInProgress()
  {
    return inProgress;
  }
  
  public int getProgress()
  {
    return progress;
  }
  
  public int getTotalTime()
  {
    return totalTime;
  }
}
