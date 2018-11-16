package nl.naxanria.researchpower.research;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import nl.naxanria.nlib.network.PacketHelper;
import nl.naxanria.nlib.util.INBTReadWrite;
import nl.naxanria.nlib.util.player.PlayerHelper;
import nl.naxanria.researchpower.SyncInit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResearchProgress implements INBTReadWrite
{
  // todo(nax): base on ftb teams?
  private static Map<EntityPlayer, ResearchProgress> playerProgress = new HashMap<>();
  
  public static ResearchProgress getProgress(EntityPlayer player)
  {
    
    if (playerProgress.containsKey(player))
    {
      return playerProgress.get(player);
    }
    
    return null;
  }
  
  public static void addProgress(EntityPlayer player, ResearchProgress progress)
  {
    playerProgress.put(player, progress);
  }
  
  public static void loadProgress(NBTTagCompound compound)
  {
  
  }
  
  public static NBTTagCompound getProgressCompound()
  {
    return new NBTTagCompound();
  }

  public Research parent;
  public EntityPlayer player;
  public int progress = 0;
  public int requiredProgress;
  
  public ResearchProgress(Research parent, EntityPlayer player, int requiredProgress)
  {
    this.parent = parent;
    this.player = player;
    this.requiredProgress = requiredProgress;
  }
  
  public ResearchProgress(NBTTagCompound compound)
  {
    readFromNbt(compound);
  }
  
  public void update(int amount)
  {
    if (isFinished())
    {
      return;
    }
    
    progress += amount;
    if (progress >= requiredProgress)
    {
      parent.finished(player);
    }
    
    if (Minecraft.getMinecraft().world.isRemote)
    {
      sendUpdate();
    }
  }
  
  public float getProgressPercentage()
  {
    return progress / (float) requiredProgress;
  }
  
  public boolean isFinished()
  {
    return progress >= requiredProgress;
  }
  
  public void sendUpdate()
  {
    NBTTagCompound compound = new NBTTagCompound();
    writeToNBT(compound);
    PacketHelper.sendSyncPacket(SyncInit.RESEARCH_PROGRESS, compound);
  }
  
  @Override
  public void writeToNBT(NBTTagCompound compound)
  {
    compound.setString("research", parent.name);
    compound.setUniqueId("player", PlayerHelper.getUUIDFromPlayer(player));
    compound.setInteger("progress", progress);
    compound.setInteger("requiredProgress", requiredProgress);
  }
  
  @Override
  public void readFromNbt(NBTTagCompound compound)
  {
    String researchName = compound.getString("research");
    UUID uuid = compound.getUniqueId("player");
    
    Research research = Research.getResearch(researchName);
    EntityPlayer player = PlayerHelper.getPlayerFromUUID(uuid);
    
    this.parent = research;
    this.player = player;
    
    progress = compound.getInteger("progress");
    requiredProgress = compound.getInteger("requiredProgress");
  }
  
  public static void remove(EntityPlayer player)
  {
      playerProgress.remove(player);
  }
}
