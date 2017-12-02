package nl.naxanria.researchpower.tile.machines;


import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.power.TileEntityEnergyAcceptor;
import nl.naxanria.nlib.util.MathUtil;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.nlib.util.RandomHelper;
import nl.naxanria.researchpower.block.BlocksInit;
import nl.naxanria.researchpower.recipe.registry.MiniatureRecipeRegistry;
import nl.naxanria.researchpower.recipe.RecipeMiniature;

public class TileEntityMiniatureController extends TileEntityEnergyAcceptor
{
  public static final int CAPACITY = 20000;
  public static final int MAX_USE = 1500;
  
  public boolean structureGood;
  
  protected int progress = 0;
  protected int totalTime = 100;
  protected boolean inProgress = false;
  protected int baseLightningChance = 2;
  protected int lightningChance = 0;
  protected RecipeMiniature currentRecipe;

  public IBlockState[] processingRecipe = new IBlockState[27];
  
  public EnumFacing dir = null;

  public TileEntityMiniatureController()
  {
    super(CAPACITY, MAX_USE);
  }
  
  public boolean makeOwnStructure() // TODO: eventually we store this and only invalidate, but for now this is good
  {
    if (world.getBlockState(pos.north()) == BlocksInit.Machines.MACHINE_FRAME.getDefaultState())
    {
      dir = EnumFacing.NORTH;
    }
    else if (world.getBlockState(pos.east()) == BlocksInit.Machines.MACHINE_FRAME.getDefaultState())
    {
      dir = EnumFacing.EAST;
    }
    else if (world.getBlockState(pos.south()) == BlocksInit.Machines.MACHINE_FRAME.getDefaultState())
    {
      dir = EnumFacing.SOUTH;
    }
    else if (world.getBlockState(pos.west()) == BlocksInit.Machines.MACHINE_FRAME.getDefaultState())
    {
      dir = EnumFacing.WEST;
    }
    if (dir == null)
    {
      return false;
    }

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
          IBlockState state = BlocksInit.Machines.Miniature.miniatureStructure[x][y][z];
          if (state != null && world.getBlockState(ourPos) != state)
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

    BlockPos[] positions = getBlockPositions();

    for (int i = 0; i <= 26; i++)
    {
        blocks[i] = world.getBlockState(positions[i]);
    }
    
    return blocks;
  }

  public BlockPos[] getBlockPositions()
  {
    BlockPos[] blocks = new BlockPos[27];

    EnumFacing offsetDir = dir.rotateAround(EnumFacing.Axis.Y).getOpposite();

    BlockPos basePos = pos.offset(offsetDir, 1).offset(dir, 1).up(1);

    EnumFacing opposite = offsetDir.getOpposite();

    int count = 0;

    for (int y = 0; y <=2; y++)
    {
      BlockPos yBase = basePos.offset(EnumFacing.UP, y);
      for (int i = 0; i <= 8; i++)
      {
        blocks[count++] = yBase.offset(opposite, i % 3).offset(dir, i / 3);
      }
    }

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
      BlockPos[] positions = getBlockPositions();
      for (int i = 0; i < 27; i++)
      {
        processingRecipe[i] = world.getBlockState(positions[i]);
        world.setBlockToAir(positions[i]);
      }
      sendUpdate();
    }
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();

    if (inProgress)
    {
      progress++;
      if (!world.isRemote)
      {
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

          IBlockState[] blocks = new IBlockState[27];

          BlockPos[] positions = getBlockPositions();

          // spawn the item

          BlockPos pos = positions[4];
          BlockPos controllerPos = getPos();

          if (currentRecipe != null) //TODO: Proper fix, including serializing the recipe.
          {
            EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, currentRecipe.getCraftingResult());
            item.setVelocity(0, 0, 0); // TODO: decide what to do, whether to have it fly randomly or keep it stationary like so.
            world.spawnEntity(item);
          }


          progress = 0;
          currentRecipe = null;
          inProgress = false;

          for(int i = 0; i < 27; i++)
          {
            world.setBlockState(positions[i], processingRecipe[i]); // for testing so I don't have to keep rebuilding the fucking thing
          }

          processingRecipe = new IBlockState[27];
          sendUpdate();
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
  
  public float getProgressPercent()
  {
    return MathUtil.getPercent(progress, totalTime);
  }

  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    super.writeSyncableNBT(compound, type);

    if (type == NBTType.SAVE_TILE  || type == NBTType.SYNC)
    {
      compound.setBoolean("structureGood", structureGood);
      if (structureGood)
      {
        compound.setString("dir", dir.getName());
        compound.setBoolean("inProgress", inProgress);
        if (inProgress)
        {
          compound.setInteger("progress", progress);
          compound.setInteger("totalTime", totalTime);
          for(int i = 0; i < 27; i++)
          {
            NBTTagCompound stateCompound = new NBTTagCompound();
            compound.setTag("state" + i, NBTUtil.writeBlockState(stateCompound, processingRecipe[i]));
          }
        }
      }
    }
  }

  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    super.readSyncableNBT(compound, type);

    if (type == NBTType.SAVE_TILE || type == NBTType.SYNC)
    {
      structureGood = compound.getBoolean("structureGood");
      if (structureGood)
      {
        dir = EnumFacing.byName(compound.getString("dir"));
        inProgress = compound.getBoolean("inProgress");
        if (inProgress)
        {
          progress = compound.getInteger("progress");
          totalTime = compound.getInteger("totalTime");
          for(int i = 0; i < 27; i++)
          {
            NBTTagCompound stateCompound = compound.getCompoundTag("state" + i);
            processingRecipe[i] = NBTUtil.readBlockState(stateCompound);
          }
        } else {
          progress = 0;
          processingRecipe = new IBlockState[27];
        }
      }
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public net.minecraft.util.math.AxisAlignedBB getRenderBoundingBox()
  {
    if (dir == null)
    {
      return super.getRenderBoundingBox();
    }
    
    return new AxisAlignedBB(getPos().offset(dir.rotateAround(EnumFacing.Axis.Y).getOpposite(), 1).down(), getPos().offset(dir, 3).offset(dir.rotateAround(EnumFacing.Axis.Y), 2).up(4));
  }

}
