package nl.naxanria.researchpower.network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.naxanria.researchpower.ResearchPower;
import nl.naxanria.researchpower.tile.TileEntityBase;

import java.util.ArrayList;
import java.util.List;

public class PacketHandler
{
  public static SimpleNetworkWrapper networkWrapper;
  
  public static final List<DataHandler> DATA_HANDLERS = new ArrayList<>();
  
  public static final DataHandler TILE_ENTITY_HANDLER = new DataHandler()
  {
    @Override
    @SideOnly(Side.CLIENT)
    public void handleData(NBTTagCompound compound, MessageContext context)
    {
      World world = Minecraft.getMinecraft().world;
      if(world != null)
      {
        TileEntity tile = world.getTileEntity(new BlockPos(compound.getInteger("X"), compound.getInteger("Y"), compound.getInteger("Z")));
        if(tile instanceof TileEntityBase)
        {
          ((TileEntityBase)tile).readSyncableNBT(compound.getCompoundTag("Data"), TileEntityBase.NBTType.SYNC);
        }
      }
    }
  };
  
  public static void init()
  {
    networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ResearchPower.MOD_ID);
    networkWrapper.registerMessage(PacketServerToClient.Handler.class, PacketServerToClient.class, 0, Side.CLIENT);
    networkWrapper.registerMessage(PacketClientToServer.Handler.class, PacketClientToServer.class, 1, Side.SERVER);
    
    addAll
    (
      TILE_ENTITY_HANDLER
    );
  }
  
  private static void addAll(DataHandler... handlers)
  {
    for (int i = 0; i < handlers.length; i++)
    {
      DataHandler handler = handlers[i];
      handler.id = i;
      DATA_HANDLERS.add(handler);
    }
  }
}
