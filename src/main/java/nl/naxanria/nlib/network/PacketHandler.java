package nl.naxanria.nlib.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.naxanria.nlib.tile.IButtonResponder;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.util.NBTHelper;
import nl.naxanria.nlib.util.logging.Log;
import nl.naxanria.nlib.util.logging.LogColor;
import nl.naxanria.researchpower.ResearchPower;

import java.util.ArrayList;
import java.util.List;

public class PacketHandler
{
  public static SimpleNetworkWrapper networkWrapper;
  
  public static final List<DataHandler> DATA_HANDLERS = new ArrayList<>();
  public static final List<ISync> SYNC_RECEIVERS = new ArrayList<>();
  
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
  
  public static final DataHandler GUI_BUTTON_TO_TILE_HANDLER = new DataHandler()
  {
    @Override
    public void handleData(NBTTagCompound compound, MessageContext context)
    {
      World world = DimensionManager.getWorld(compound.getInteger("WorldID"));
      TileEntity tile = world.getTileEntity(NBTHelper.readBlockPos(compound));
      
      if (tile instanceof IButtonResponder)
      {
        IButtonResponder responder = (IButtonResponder) tile;
        Entity entity = world.getEntityByID(compound.getInteger("PlayerID"));
        if (entity instanceof EntityPlayer)
        {
          int bID = compound.getInteger("ButtonID");
          responder.onButtonPressed(bID, (EntityPlayer) entity);
          Log.info(LogColor.YELLOW, "Button Remote:" + world.isRemote + " ID: " + bID);
        }
      }
    }
  };
  
  public static final DataHandler SYNC_HANDLER = new DataHandler()
  {
    @Override
    public void handleData(NBTTagCompound compound, MessageContext context)
    {
      int syncId = compound.getInteger("SyncID");
      NBTTagCompound data = compound.getCompoundTag("Data");
      ISync sync = SYNC_RECEIVERS.get(syncId);
      sync.sync(data);
  
      Log.info(LogColor.YELLOW, "Syncing Remote:" + Minecraft.getMinecraft().world.isRemote + " ID: " + syncId + " ");
    }
  };
  
  public static void init()
  {
    networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ResearchPower.MOD_ID);
    networkWrapper.registerMessage(PacketServerToClient.Handler.class, PacketServerToClient.class, 0, Side.CLIENT);
    networkWrapper.registerMessage(PacketClientToServer.Handler.class, PacketClientToServer.class, 1, Side.SERVER);
    
    addAll
    (
      TILE_ENTITY_HANDLER,
      GUI_BUTTON_TO_TILE_HANDLER,
      SYNC_HANDLER
    );
  }
  
  public static ISync registerSync(ISync sync)
  {
    SYNC_RECEIVERS.add(sync);
    
    return sync;
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
