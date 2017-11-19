package nl.naxanria.nlib.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import nl.naxanria.nlib.network.PacketHandler;
import nl.naxanria.nlib.network.PacketServerToClient;
import nl.naxanria.nlib.util.WorldUtil;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends TileEntity implements ITickable
{
  protected TileEntity[] tilesAround = new TileEntity[6];
  
  private boolean isEnergySharingProvider;
  private IEnergySharingProvider energySharingProvider;
  
  public TileEntityBase()
  {
    isEnergySharingProvider = this instanceof IEnergySharingProvider;
    if (isEnergySharingProvider)
    {
      energySharingProvider = (IEnergySharingProvider) this;
    }
  }
  
  public boolean canUpdate()
  {
    return true;
  }
  
  @Override
  public void update()
  {
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
        if (energySharingProvider.doesShare())
        {
          int total = energySharingProvider.getEnergyToShare();
          if (total > 0)
          {
            EnumFacing[] shareSides = energySharingProvider.getProvidingSides();
            
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
              if (energySharingProvider.canShareTo(tile))
              {
                WorldUtil.doEnergyInteraction(this, tile, side, amount);
              }
            }
          }
        }
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
    for (EnumFacing side : EnumFacing.values())
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
    return isEnergySharingProvider;
  }
  
  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
  {
    return getCapability(capability, facing) != null;
  }
  
  @SuppressWarnings("unchecked")
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
    
    return super.getCapability(capability, facing);
  }
  
  public IEnergyStorage getEnergyStorage(EnumFacing facing)
  {
    return null;
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
      this.writeSyncableNBT(compound, NBTType.SYNC);
      
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
          this.world.provider.getDimension(),
          this.getPos().getX(),
          this.getPos().getY(),
          this.getPos().getZ(),
          64
        )
      );
    }
  }
  
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    if(type != NBTType.SAVE_BLOCK)
    {
      super.writeToNBT(compound);
    }
//
//    if(type == NBTType.SAVE_TILE)
//    {
//      compound.setBoolean("Redstone", this.isRedstonePowered);
//      compound.setInteger("TicksElapsed", this.ticksElapsed);
//      compound.setBoolean("StopDrop", this.stopFromDropping);
//    }
//    if(this.isRedstoneToggle() && (type != NBTType.SAVE_BLOCK || this.isPulseMode))
//    {
//      compound.setBoolean("IsPulseMode", this.isPulseMode);
//    }
  }
  
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
    if(type != NBTType.SAVE_BLOCK)
    {
      super.readFromNBT(compound);
    }
    
//    if(type == NBTType.SAVE_TILE){
//      this.isRedstonePowered = compound.getBoolean("Redstone");
//      this.ticksElapsed = compound.getInteger("TicksElapsed");
//      this.stopFromDropping = compound.getBoolean("StopDrop");
//    }
//    if(this.isRedstoneToggle()){
//      this.isPulseMode = compound.getBoolean("IsPulseMode");
//    }
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
