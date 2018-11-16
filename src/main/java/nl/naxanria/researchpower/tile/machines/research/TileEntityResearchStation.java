package nl.naxanria.researchpower.tile.machines.research;

import net.minecraft.nbt.NBTTagCompound;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.TileFlags;
import nl.naxanria.nlib.tile.power.TileEntityEnergyAcceptor;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.nlib.util.logging.LogColor;
import nl.naxanria.researchpower.item.ItemsInit;
import nl.naxanria.researchpower.research.Research;
import nl.naxanria.researchpower.research.ResearchDatabase;
import nl.naxanria.researchpower.research.ResearchProgress;
import nl.naxanria.researchpower.util.IWorkFinishedHandler;
import nl.naxanria.researchpower.util.IWorkStartedHandler;
import nl.naxanria.researchpower.util.WorkScheme;

public class TileEntityResearchStation extends TileEntityEnergyAcceptor implements IWorkStartedHandler, IWorkFinishedHandler
{
  private WorkScheme workScheme = new WorkScheme(10, 20, 340);
  private ResearchProgress currentResearch;
  
  public TileEntityResearchStation()
  {
    super(1000, 70);
    workScheme
      .setWorkFinishedHandler(this)
      .setWorkStartedHandler(this);
  }
  
  @Override
  protected TileFlags[] defaultFlags()
  {
    return new TileFlags[] { TileFlags.KeepNBTData, TileFlags.HasOwner, TileFlags.SaveOnWorldChange };
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
    
    if (!world.isRemote)
    {
      boolean newStart = false;
      if (currentResearch == null)
      {
        // check if research is needed
        currentResearch = ResearchProgress.getProgress(getOwner());
        
      }
      else
      {
        newStart = true;
      }
      
      if (currentResearch != null)
      {
        if (!newStart)
        {
          Log.info(LogColor.CYAN, "Started research! " + currentResearch.parent.name);
        }
        
        workScheme.update(storage);
      }
    }
  }
  
  public float getWorkProgress()
  {
    return workScheme.getProgressPercentage();
  }
  
  @Override
  public void workFinished(WorkScheme scheme)
  {
    currentResearch.update(scheme.getWork());
    
    Log.info(LogColor.CYAN, "finished research work on " + currentResearch.parent.name + " " + currentResearch.getProgressPercentage());
  
    if (currentResearch.isFinished())
    {
      currentResearch = null;
    }
  }
  
  @Override
  public void workStarted(WorkScheme scheme)
  {
    Log.info(LogColor.CYAN, "Started research work!");
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    NBTTagCompound work = new NBTTagCompound();
    workScheme.writeToNBT(work);
    
    compound.setTag("work", work);
    
    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    NBTTagCompound work = compound.getCompoundTag("work");
    workScheme.readFromNBT(work);
    
    super.readSyncableNBT(compound, type);
  }
}
