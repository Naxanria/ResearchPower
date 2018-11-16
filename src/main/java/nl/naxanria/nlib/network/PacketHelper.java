package nl.naxanria.nlib.network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import nl.naxanria.nlib.util.NBTHelper;

public class PacketHelper
{
  public static void sendButtonPacket(TileEntity tile, int buttonId)
  {
    NBTTagCompound compound = new NBTTagCompound();
    NBTHelper.writeBlockPos(tile.getPos(), compound);
    compound.setInteger("WorldID", tile.getWorld().provider.getDimension());
    compound.setInteger("PlayerID", Minecraft.getMinecraft().player.getEntityId());
    compound.setInteger("ButtonID", buttonId);
    PacketHandler.networkWrapper.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_BUTTON_TO_TILE_HANDLER));
  }
  
  public static void sendSyncPacket(ISync sync, NBTTagCompound data)
  {
    NBTTagCompound compound = new NBTTagCompound();
    compound.setInteger("SyncID", sync.syncID());
    compound.setTag("Data", data);
    PacketHandler.networkWrapper.sendToServer(new PacketClientToServer(compound, PacketHandler.SYNC_HANDLER));
  }
}
