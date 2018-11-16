package nl.naxanria.researchpower;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import nl.naxanria.nlib.network.DataHandler;
import nl.naxanria.nlib.network.ISync;
import nl.naxanria.nlib.network.PacketHandler;
import nl.naxanria.nlib.util.player.PlayerHelper;
import nl.naxanria.researchpower.research.Research;
import nl.naxanria.researchpower.research.ResearchProgress;

public class SyncInit
{
  public static void Init()
  {
  
  }
  
  public static final ISync RESEARCH_PROGRESS = PacketHandler.registerSync(compound ->
  {
    EntityPlayer player = PlayerHelper.getPlayerFromUUID(compound.getUniqueId("player"));
    if (player == null)
    {
      return;
    }
    
    ResearchProgress progress = ResearchProgress.getProgress(player);
    if (progress == null)
    {
      ResearchProgress.addProgress(player, new ResearchProgress(compound));
      return;
    }
    
    progress.readFromNbt(compound);
  });
}
