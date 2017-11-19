package nl.naxanria.nlib.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class DataHandler
{
  public abstract void handleData(NBTTagCompound compound, MessageContext context);
  
  public int id = 0;
}
