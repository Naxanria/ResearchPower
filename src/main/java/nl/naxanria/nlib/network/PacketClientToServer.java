package nl.naxanria.nlib.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientToServer extends Packet
{
  public PacketClientToServer()
  { }
  
  public PacketClientToServer(NBTTagCompound data, DataHandler handler)
  {
    super(data, handler);
  }
  
  public static class Handler implements IMessageHandler<PacketClientToServer, IMessage>
  {
    @Override
    public IMessage onMessage(PacketClientToServer message, MessageContext ctx)
    {
      FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask
      (
        () ->
        {
          if (message.data != null && message.handler != null)
          {
            message.handler.handleData(message.data, ctx);
          }
        }
      );
      
      return null;
    }
  }
}
