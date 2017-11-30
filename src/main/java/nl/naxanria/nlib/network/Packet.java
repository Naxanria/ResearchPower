package nl.naxanria.nlib.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import nl.naxanria.nlib.util.logging.Log;

import java.io.IOException;

public abstract class Packet implements IMessage
{
  protected NBTTagCompound data;
  protected DataHandler handler;
  
  public Packet()
  { }
  
  public Packet(NBTTagCompound data, DataHandler handler)
  {
    this.data = data;
    this.handler = handler;
  }
  
  @Override
  public void fromBytes(ByteBuf buf)
  {
    PacketBuffer buffer = new PacketBuffer(buf);
    
    try
    {
      data = buffer.readCompoundTag();
      
      int handlerId = buffer.readInt();
      
      if (handlerId >= 0 && handlerId < PacketHandler.DATA_HANDLERS.size())
      {
        handler = PacketHandler.DATA_HANDLERS.get(handlerId);
      }
    }
    catch (IOException e)
    {
      Log.error("Something went wrong with trying to receive a server packet.");
    }
  }
  
  @Override
  public void toBytes(ByteBuf buf)
  {
    PacketBuffer buffer = new PacketBuffer(buf);
    
    buffer.writeCompoundTag(data);
    buffer.writeInt(handler.id);
  }
}
