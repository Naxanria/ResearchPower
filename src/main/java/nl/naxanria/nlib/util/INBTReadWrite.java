package nl.naxanria.nlib.util;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTReadWrite
{
  void writeToNBT(NBTTagCompound compound);
  
  void readFromNbt(NBTTagCompound compound);
}
