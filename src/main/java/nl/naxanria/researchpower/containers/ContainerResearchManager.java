package nl.naxanria.researchpower.containers;

import net.minecraft.entity.player.EntityPlayer;
import nl.naxanria.nlib.container.ContainerBase;
import nl.naxanria.researchpower.tile.machines.research.TileEntityResearchManager;

public class ContainerResearchManager extends ContainerBase<TileEntityResearchManager>
{
  public ContainerResearchManager(TileEntityResearchManager tile, EntityPlayer player)
  {
    super(tile, player);
    
    syncHelper.create
    (
      "Energy",
      tile.storage::setEnergyStored,
      tile.storage::getEnergyStored
    );
  }
}
