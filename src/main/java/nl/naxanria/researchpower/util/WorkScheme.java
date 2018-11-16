package nl.naxanria.researchpower.util;

import net.minecraft.nbt.NBTTagCompound;
import nl.naxanria.nlib.tile.power.EnergyStorageBase;

public class WorkScheme
{
  private int work;
  private int workTime;
  private int workProgress;
  private int workPower;
  
  private IWorkFinishedHandler workFinishedHandler;
  private IWorkStartedHandler workStartedHandler;
  
  public WorkScheme(int work, int workTime, int workPower)
  {
    this.work = work;
    this.workTime = workTime;
    this.workPower = workPower;
  }
  
  public WorkScheme setWorkFinishedHandler(IWorkFinishedHandler workFinishedHandler)
  {
    this.workFinishedHandler = workFinishedHandler;
    return this;
  }
  
  public WorkScheme setWorkStartedHandler(IWorkStartedHandler workStartedHandler)
  {
    this.workStartedHandler = workStartedHandler;
    return this;
  }
  
  public void update(EnergyStorageBase storage)
  {
    if (workProgress > 0)
    {
      // do the work progress stuff
      workProgress--;
      if (workProgress <= 0)
      {
        finished();
        workProgress = 0;
      }
    }
    else
    {
      // check if we can start the work
      if (storage.getEnergyStored() >= workPower)
      {
        storage.setEnergyStored(storage.getEnergyStored() - workPower);
        workProgress = workTime;
        started();
      }
    }
  }
  
  public int getWork()
  {
    return work;
  }
  
  public int getWorkTime()
  {
    return workTime;
  }
  
  public int getWorkProgress()
  {
    return workProgress;
  }
  
  public int getWorkPower()
  {
    return workPower;
  }
  
  public WorkScheme setWork(int work)
  {
    this.work = work;
    return this;
  }
  
  public WorkScheme setWorkTime(int workTime)
  {
    this.workTime = workTime;
    return this;
  }
  
  public WorkScheme setWorkPower(int workPower)
  {
    this.workPower = workPower;
    return this;
  }
  
  public float getProgressPercentage()
  {
    if (workProgress == 0)
    {
      return 0;
    }
    
    return (workTime - workProgress) / (float) workTime;
  }
  
  private void finished()
  {
    if (workFinishedHandler != null)
    {
      workFinishedHandler.workFinished(this);
    }
  }
  
  private void started()
  {
    if (workStartedHandler != null)
    {
      workStartedHandler.workStarted(this);
    }
  }
  
  public void writeToNBT(NBTTagCompound compound)
  {
    compound.setInteger("work", work);
    compound.setInteger("workTime", workTime);
    compound.setInteger("workProgress", workProgress);
    compound.setInteger("workPower", workPower);
  }
  
  public void readFromNBT(NBTTagCompound compound)
  {
    work = compound.getInteger("work");
    workTime = compound.getInteger("workTime");
    workProgress = compound.getInteger("workProgress");
    workPower = compound.getInteger("workPower");
    
    
  }
  
}
