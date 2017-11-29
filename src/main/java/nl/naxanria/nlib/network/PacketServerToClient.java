package nl.naxanria.nlib.network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketServerToClient extends Packet
{
  public PacketServerToClient()
  {  }
  
  public PacketServerToClient(NBTTagCompound data, DataHandler tileEntityHandler)
  {
    super(data, tileEntityHandler);
  }
  
  public static class Handler implements IMessageHandler<PacketServerToClient, IMessage>
  {
    @Override
    public IMessage onMessage(PacketServerToClient message, MessageContext ctx)
    {
      Minecraft.getMinecraft().addScheduledTask
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
