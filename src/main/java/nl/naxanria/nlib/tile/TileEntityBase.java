package nl.naxanria.nlib.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import nl.naxanria.nlib.network.PacketHandler;
import nl.naxanria.nlib.network.PacketServerToClient;
import nl.naxanria.nlib.tile.fluid.IFluidSharingProvider;
import nl.naxanria.nlib.tile.inventory.IInventoryHolder;
import nl.naxanria.nlib.tile.power.IEnergySharingProvider;
import nl.naxanria.nlib.util.EnumHelper;
import nl.naxanria.nlib.util.Flags;
import nl.naxanria.nlib.util.player.PlayerHelper;
import nl.naxanria.nlib.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.UUID;

@SuppressWarnings("NullableProblems")
public abstract class TileEntityBase extends TileEntity implements ITickable
{
  protected TileEntity[] tilesAround = new TileEntity[6];
  
  protected int ticksPassed = 0;
  
  private boolean isEnergySharingProvider;
  private IEnergySharingProvider energySharingProvider;
  
  private boolean isFluidSharingProvider;
  private IFluidSharingProvider fluidSharingProvider;
  
  private boolean isInventoryHolder;
  private IInventoryHolder inventoryHolder;

  public final Flags<TileFlags> flags = new Flags<>();
  private boolean hasSavedDataOnChangeOrWorldStart = false;
  private EntityPlayer owner;
  
  public TileEntityBase(boolean needsOwner)
  {
    this();
    
    if (needsOwner)
    {
      flags.enableFlag(TileFlags.HasOwner);
    }
  }
  
  public TileEntityBase()
  {
    isEnergySharingProvider = this instanceof IEnergySharingProvider;
    if (isEnergySharingProvider)
    {
      energySharingProvider = (IEnergySharingProvider) this;
      flags.enableFlag(TileFlags.SaveOnWorldChange);
    }
    
    isFluidSharingProvider = this instanceof IFluidSharingProvider;
    if (isFluidSharingProvider)
    {
      fluidSharingProvider = (IFluidSharingProvider) this;
      flags.enableFlag(TileFlags.SaveOnWorldChange);
    }

    isInventoryHolder = this instanceof IInventoryHolder;
    if (isInventoryHolder)
    {
      inventoryHolder = (IInventoryHolder) this;
    }
    
    this.flags.enableFlags(defaultFlags());
  }
  
  protected TileFlags[] defaultFlags()
  {
    return new TileFlags[0];
  }
  
  public String getInfo()
  {
    return "";
  }
  
  public void setOwner(EntityPlayer owner)
  {
    this.owner = owner;
    
    markDirty();
  }
  
  public EntityPlayer getOwner()
  {
    return owner;
  }
  
  public boolean isOwner(EntityPlayer player)
  {
    return player.equals(owner);
  }
  
  public boolean hasFlags(TileFlags... flags)
  {
//    long val = 0;
//    for (TileFlags f :
//      flags)
//    {
//      val |= f.FLAG;
//    }
//    return (this.flags & val) != 0;
    
    return this.flags.hasFlags(flags);
  }
  
  public boolean canUpdate()
  {
    return true;
  }
  
  @Override
  public void update()
  {
    ticksPassed++;
    
    if (canUpdate())
    {
      entityUpdate();
    }
  }
  
  protected void entityUpdate()
  {
    if (!world.isRemote)
    {
      if (isEnergySharingProvider)
      {
        if (energySharingProvider.doesShareEnergy())
        {
          int total = energySharingProvider.getEnergyToShare();
          if (total > 0)
          {
            EnumFacing[] shareSides = energySharingProvider.getEnergyProvidingSides();
            
            int amount = total / shareSides.length;
            if (amount <= 0)
            {
              amount = total;
            }
  
            for (EnumFacing side :
              shareSides)
            {
              TileEntity tile = tilesAround[side.ordinal()];
              if (tile == null)
              {
                continue;
              }
              if (energySharingProvider.canShareEnergyTo(tile))
              {
                WorldUtil.doEnergyInteraction(this, tile, side, amount);
              }
            }
          }
        }
      }
      
      if (isFluidSharingProvider)
      {
        if (fluidSharingProvider.doesShareFluid())
        {
          int total = fluidSharingProvider.getFluidToShare();
          EnumFacing[] sides = fluidSharingProvider.getFluidProvidingSides();
          if (total > 0)
          {
            int amount = total / sides.length;
            if (amount <= 0)
            {
              amount = total;
            }
  
            for (EnumFacing side :
              sides)
            {
              TileEntity tile = tilesAround[side.ordinal()];
              WorldUtil.doFluidInteraction(this, tile, side, amount);
            }
          }
        }
      }
  
      if(!hasSavedDataOnChangeOrWorldStart)
      {
        if(shouldSaveDataOnChangeOrWorldStart())
        {
          saveDataOnChangeOrWorldStart();
        }
    
        hasSavedDataOnChangeOrWorldStart = true;
      }
    }
  }
  
  @Nullable
  @Override
  public ITextComponent getDisplayName()
  {
    return new TextComponentString(getClass().getName());
  }
  
  public void saveDataOnChangeOrWorldStart()
  {
    for (EnumFacing side : EnumHelper.Facing.ALL)
    {
      BlockPos pos = this.pos.offset(side);
      if (this.world.isBlockLoaded(pos))
      {
        this.tilesAround[side.ordinal()] = this.world.getTileEntity(pos);
      }
    }
  }
  
  public boolean shouldSaveDataOnChangeOrWorldStart()
  {
    return hasFlags(TileFlags.SaveOnWorldChange);
  }
  
  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
  {
    return getCapability(capability, facing) != null;
  }
  
  @SuppressWarnings({"unchecked", "NullableProblems"})
  @Nullable
  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
  {
    if (capability == CapabilityEnergy.ENERGY)
    {
      IEnergyStorage storage = getEnergyStorage(facing);
      if (storage != null)
      {
        return (T) storage;
      }
    }
    
    if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
    {
      IFluidHandler tank = getFluidHandler(facing);
      if (tank != null)
      {
        return (T) tank;
      }
    }
    
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    {
      IItemHandler inventory = getInventory(facing);
      if (inventory != null)
      {
        return (T) inventory;
      }
    }
    
    return super.getCapability(capability, facing);
  }
  
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    return null;
  }
  
  public IFluidHandler getFluidHandler(EnumFacing facing)
  {
    return null;
  }
  
  public IItemHandler getInventory(EnumFacing facing)
  {
    return null;
  }
  
  public IItemHandler[] getAllInventories()
  {
    return new IItemHandler[0];
  }
  
  public IItemHandler[] getAllInventoriesToDrop()
  {
    return getAllInventories();
  }
  
  public int getComparatorStrength()
  {
    return 0;
  }
  
  @Override
  public final NBTTagCompound writeToNBT(NBTTagCompound compound)
  {
    this.writeSyncableNBT(compound, NBTType.SAVE_TILE);
    return compound;
  }
  
  @Override
  public final void readFromNBT(NBTTagCompound compound)
  {
    this.readSyncableNBT(compound, NBTType.SAVE_TILE);
  }
  
  @Override
  public final SPacketUpdateTileEntity getUpdatePacket()
  {
    NBTTagCompound compound = new NBTTagCompound();
    this.writeSyncableNBT(compound, NBTType.SYNC);
    return new SPacketUpdateTileEntity(this.pos, -1, compound);
  }
  
  @Override
  public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
  {
    this.readSyncableNBT(pkt.getNbtCompound(), NBTType.SYNC);
  }
  
  @Override
  public final NBTTagCompound getUpdateTag()
  {
    NBTTagCompound compound = new NBTTagCompound();
    this.writeSyncableNBT(compound, NBTType.SYNC);
    return compound;
  }
  
  @Override
  public final void handleUpdateTag(NBTTagCompound compound)
  {
    this.readSyncableNBT(compound, NBTType.SYNC);
  }
  
  public final void sendUpdate()
  {
    if(this.world != null && !this.world.isRemote)
    {
      NBTTagCompound compound = new NBTTagCompound();
      writeSyncableNBT(compound, NBTType.SYNC);
      
      NBTTagCompound data = new NBTTagCompound();
      data.setTag("Data", compound);
      data.setInteger("X", pos.getX());
      data.setInteger("Y", pos.getY());
      data.setInteger("Z", pos.getZ());
      PacketHandler.networkWrapper.sendToAllAround
      (
        new PacketServerToClient(data, PacketHandler.TILE_ENTITY_HANDLER),
        new NetworkRegistry.TargetPoint
        (
          world.provider.getDimension(),
          getPos().getX(),
          getPos().getY(),
          getPos().getZ(),
          64
        )
      );
    }
  }
  
  protected boolean sendUpdateWithInterval()
  {
    // Todo: make based on config
    if(ticksPassed % 5 == 0)
    {
      this.sendUpdate();
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    if(type != NBTType.SAVE_BLOCK)
    {
      super.writeToNBT(compound);
    }
    
    if (type == NBTType.SAVE_TILE)
    {
      if (owner != null)
      {
        compound.setUniqueId("owner", owner.getUniqueID());
      }
    }
  }
  
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    if(type != NBTType.SAVE_BLOCK)
    {
      super.readFromNBT(compound);
    }
    
    if (type == NBTType.SAVE_TILE)
    {
      UUID ownerID = compound.getUniqueId("owner");
      if (ownerID != null)
      {
        
        owner = PlayerHelper.getPlayerFromUUID(ownerID);
      }
    }
  }
  
  @Override
  public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
  {
    return !oldState.getBlock().isAssociatedBlock(newState.getBlock());
  }
  
  public enum NBTType
  {
    SAVE_TILE,
    SYNC,
    SAVE_BLOCK
  }
}
