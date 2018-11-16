package nl.naxanria.nlib.network;

import net.minecraft.nbt.NBTTagCompound;

public interface ISync
{
  void sync(NBTTagCompound compound);
  
  default int syncID()
  {
    return PacketHandler.SYNC_RECEIVERS.indexOf(this);
  }
}
